package com.hospital.cycle.utils;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hospital.cycle.domain.*;
import com.hospital.cycle.domain.vo.calc.CycleDeptCalcVo;
import com.hospital.cycle.domain.vo.calc.CycleDeptCapacityVo;
import com.hospital.cycle.domain.vo.calc.CycleStudentCalcVo;
import com.hospital.cycle.domain.vo.calc.CycleStudentDeptCalcVo;
import com.hospital.cycle.mapper.*;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.service.StudentService;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.mybatis.helper.DataBaseHelper;

import java.util.*;
import java.util.stream.Collectors;

import static com.hospital.cycle.constant.CycleConstant.CYCLE_GROUP_METHOD_MUST;

/**
 * 用于初始化和计算轮转的工具类
 */
public class CycleCalcUtils {

    public static CycleRuleMapper ruleMapper = SpringUtils.getBean(CycleRuleMapper.class);
    public static CycleGroupMapper groupMapper = SpringUtils.getBean(CycleGroupMapper.class);
    public static CycleGroupDeptMapper groupDeptMapper = SpringUtils.getBean(CycleGroupDeptMapper.class);
    public static CycleStudentMapper cycleGroupMapper = SpringUtils.getBean(CycleStudentMapper.class);
    public static CycleRuleBaseMapper ruleBaseMapper = SpringUtils.getBean(CycleRuleBaseMapper.class);
    public static CycleUserDeptMapper userDeptMapper = SpringUtils.getBean(CycleUserDeptMapper.class);
    public static CycleStudentMapper studentMapper = SpringUtils.getBean(CycleStudentMapper.class);
    public static StudentService studentService = SpringUtils.getBean(StudentService.class);
    public static CycleRecordMapper recordMapper = SpringUtils.getBean(CycleRecordMapper.class);


    /**
     * 初始化规则中的所有科室
     * @param ruleId
     * @param ruleTotalTimeUnit
     * @return
     */
    public static List<CycleDeptCalcVo> calcAllDept(Long ruleId, Integer ruleTotalTimeUnit) {
        //获取规则下所有科室
        List<CycleGroupDept> cycleGroupDeptList = groupDeptMapper.selectList(Wrappers.<CycleGroupDept>lambdaQuery().eq(CycleGroupDept::getRuleId, ruleId));
        if (cycleGroupDeptList.size() == 0) {
            return null;
        }
        List<CycleDeptCalcVo> deptVoList = new ArrayList<>();

        cycleGroupDeptList.forEach(dept->{
            CycleDeptCalcVo deptVo = new CycleDeptCalcVo();
            deptVo.setDeptId(dept.getDeptId());
            //获取本科是需要轮转的学生人数
            Long studentNum = recordMapper.selectCount(Wrappers.<CycleRecord>lambdaQuery()
                .eq(CycleRecord::getRuleId, ruleId)
                .eq(CycleRecord::getDeptId, dept.getDeptId())
            );
            List<CycleDeptCapacityVo> capacityVoList = new ArrayList<>();
            if (ruleTotalTimeUnit<0){
                throw new ServiceException("轮转总时间不能小于0");
            }
            for (int i = 1; i<=ruleTotalTimeUnit;i++){
                CycleDeptCapacityVo capacityVo = new CycleDeptCapacityVo();
                capacityVo.setDeptId(dept.getDeptId());
                capacityVo.setDateIndex(i);
                //科室容量 = 需要来本科室轮转的人数/时间人数,向上取整
                capacityVo.setDeptCapacity((int) Math.ceil((double) studentNum.intValue() / ruleTotalTimeUnit));
                //还没开始排，剩余容量和科室容量一致
                capacityVo.setDeptCapacity(capacityVo.getDeptCapacity());
                capacityVoList.add(capacityVo);
            }
            deptVo.setCapacityList(capacityVoList);
            deptVoList.add(deptVo);
        });
        return deptVoList;
    }

    /**
     * 获取轮转科室的整体轮转时间
     * @param ruleId
     * @return
     */
    public static Integer getTotalTimeUnit(Long ruleId) {
        //获取规则组下,非必选方法的轮转时间
        List<CycleGroup> cycleGroupList = groupMapper.selectList(Wrappers.<CycleGroup>lambdaQuery()
            .eq(CycleGroup::getRuleId, ruleId)
            .ne(CycleGroup::getGroupMethod, CYCLE_GROUP_METHOD_MUST)
        );
        int totalTimeUnit;
        totalTimeUnit = cycleGroupList.stream().mapToInt(CycleGroup::getGroupUnitNum).sum();
        //必选不设置规则组时间，直接获取科室的轮转时间
        List<CycleGroupDept> cycleGroupDeptList = groupDeptMapper.selectList(Wrappers.<CycleGroupDept>lambdaQuery()
            .eq(CycleGroupDept::getRuleId, ruleId)
            .and(w->{
                List<CycleGroup> mustGroupList = groupMapper.selectList(Wrappers.<CycleGroup>lambdaQuery()
                    .eq(CycleGroup::getRuleId, ruleId)
                    .eq(CycleGroup::getGroupMethod, CYCLE_GROUP_METHOD_MUST));
                if (!mustGroupList.isEmpty()){
                    w.in(CycleGroupDept::getGroupId, mustGroupList.stream().map(CycleGroup::getGroupId).collect(Collectors.toList()));
                }
            })
        );
        if (!cycleGroupDeptList.isEmpty()){
            totalTimeUnit += cycleGroupDeptList.stream().mapToInt(CycleGroupDept::getDeptUnitNum).sum();
        }
        return totalTimeUnit;
    }

    /**
     * 获取规则中所有学生和要排课的科室
     * @param ruleId
     * @return
     */
    public static List<CycleStudentCalcVo> calcAllStudent(Long ruleId) {
        //获取规则
        CycleRule cycleRule = ruleMapper.selectById(ruleId);
        if(cycleRule.getParentId()!=0L){
            //获取父规则
            CycleRule parentRule = ruleMapper.selectOne(Wrappers.<CycleRule>lambdaQuery()
                .apply(DataBaseHelper.findInSet("rule_id", cycleRule.getAncestors()))
                .eq(CycleRule::getParentId, 0L));
            ruleId = parentRule.getRuleId();
        }
        //获取规则下的学生
        List<CycleStudent> studentList = studentMapper.selectList(Wrappers.<CycleStudent>lambdaQuery().eq(CycleStudent::getRuleId, ruleId));
        if(studentList.isEmpty()) {
            throw new ServiceException("该规则下没有学生");
        }
        Set<Long> userIds = studentList.stream().map(CycleStudent::getUserId).collect(Collectors.toSet());
        //获取所有学生的轮转记录
        List<CycleRecord> recordList = recordMapper.selectList(Wrappers.<CycleRecord>lambdaQuery()
            .in(CycleRecord::getUserId, userIds)
            .in(CycleRecord::getRuleId, ruleId)
        );
        List<CycleStudentCalcVo> voList = new ArrayList<>();
        //提取每个学生的科室
        studentList.forEach(student->{
            List<CycleRecord> studentRecordList = recordList.stream().filter(record -> record.getUserId().equals(student.getUserId())).toList();
            if (studentRecordList.isEmpty()){
                return;
            }
            List<CycleStudentDeptCalcVo> studentDeptCalcVoList = new ArrayList<>();
            studentRecordList.forEach(studentRecord->{
                CycleStudentDeptCalcVo studentDeptCalcVo = new CycleStudentDeptCalcVo();
                studentDeptCalcVo.setDeptId(studentRecord.getDeptId());
                studentDeptCalcVo.setStudentDeptNum(studentRecord.getDeptNum());//每个科室的轮转时长
                studentDeptCalcVoList.add(studentDeptCalcVo);
            });
            CycleStudentCalcVo studentCalcVo = new CycleStudentCalcVo();
            studentCalcVo.setUserId(student.getUserId());
            studentCalcVo.setDeptList(studentDeptCalcVoList);
            voList.add(studentCalcVo);
        });
        return voList;
    }


    public static void calc(List<CycleStudentCalcVo> allStudentList, List<CycleDeptCalcVo> allDeptList, Long ruleId, Integer ruleTotalTimeUnit) {
        //对所有科室进行随机重排
        Collections.shuffle(allDeptList);
        allStudentList.forEach(student->{
            //获取学生没有排班的科室
            List<CycleStudentDeptCalcVo> unDeptList = student.getDeptList().stream().filter(dept -> !dept.getIsComplete()).toList();
            if (unDeptList.isEmpty()){//全部排完
                return;
            }
            //开始排序
            for (int i = 0;i<=unDeptList.size();){
                int dateIndex = i+1;//目前排序的编号
                //计算出学生要排的科室
                Integer thisNum = calcDeptMinByDateIndex(student,allDeptList,dateIndex);
                i+=thisNum;
            }
        });
    }


    /**
     * 获取排序+信息更新
     * @param student
     * @param allDeptList
     * @param dateIndex
     * @return
     */
    private static Integer calcDeptMinByDateIndex(CycleStudentCalcVo student, List<CycleDeptCalcVo> allDeptList, int dateIndex) {
        //获取学生没有排班的科室
        List<CycleStudentDeptCalcVo> unDeptList = student.getDeptList().stream().filter(dept -> !dept.getIsComplete()).toList();
        //获取所有科室的capacityList
        List<CycleDeptCapacityVo> deptCalcList = allDeptList.stream().map(CycleDeptCalcVo::getCapacityList).flatMap(Collection::stream).toList();
        //调用科室选优算法，获取最优的科室
        Long deptId = deptOptimality(unDeptList,deptCalcList ,dateIndex);

        //获取到最优的科室后，更新学生的选课信息
        student.getStudentSortList().add(deptId);//插入要轮转的科室
        Integer studentDeptNum = student.getDeptList().stream().filter(dept -> dept.getDeptId().equals(deptId)).findFirst().get().getStudentDeptNum();//获取科室轮转时长

        student.getDeptList().forEach(dept->{
            if (dept.getDeptId().equals(deptId)){
                dept.setIsComplete(true);
            }
        });
        //更新科室信息
        allDeptList.forEach(dept->{
            if (!dept.getDeptId().equals(deptId)){
                return;
            }
            dept.getCapacityList().forEach(capacity->{
                if (capacity.getDateIndex()>=dateIndex&&capacity.getDateIndex()<=(studentDeptNum+dateIndex-1)){
                    capacity.getUserIds().add(student.getUserId());//把学生加入科室
                    capacity.setUnDeptCapacity(capacity.getUnDeptCapacity()-1);//对应科室容量减1
                }
            });
        });


        return studentDeptNum;
    }

    /**
     * 科室选优算法
     * @param unDeptList
     * @param deptCalcList
     * @param dateIndex
     * @return
     */
    private static Long deptOptimality(List<CycleStudentDeptCalcVo> unDeptList, List<CycleDeptCapacityVo> deptCalcList, int dateIndex) {
        List<CycleDeptCapacityVo> googList = new ArrayList<>();
        List<CycleDeptCapacityVo> normalList = new ArrayList<>();
        List<CycleDeptCapacityVo> badList = new ArrayList<>();//满员和部分满员放在这里

        unDeptList.forEach(dept -> {
            //获取科室的轮转时长
            Integer studentDeptNum = dept.getStudentDeptNum();
            //获取科室对应时间的信息
            List<CycleDeptCapacityVo> deptCalcListByDateIndex = deptCalcList.stream().filter(deptCalc -> deptCalc.getDeptId().equals(dept.getDeptId()) && deptCalc.getDateIndex() == dateIndex && deptCalc.getDateIndex() <= (studentDeptNum + dateIndex - 1)).toList();
            //先校验是否已经满员
            List<CycleDeptCapacityVo> fullDeptCalcList = deptCalcListByDateIndex.stream().filter(deptCalc -> deptCalc.getUnDeptCapacity() == 0).toList();
            if (!fullDeptCalcList.isEmpty()) {//有满员的，放入差的
                badList.addAll(fullDeptCalcList);
                return;
            }
            //查找出还没人进过的科室，放入优秀
            List<CycleDeptCapacityVo> noDeptCalcList = deptCalcListByDateIndex.stream().filter(deptCalc -> deptCalc.getUserIds().isEmpty()).toList();
            if (!noDeptCalcList.isEmpty() && noDeptCalcList.size() == deptCalcListByDateIndex.size()) {//全部为空，放入优秀
                googList.addAll(noDeptCalcList);
                return;
            }
            //其余的放入普通
            normalList.addAll(deptCalcListByDateIndex);
        });
        //所有科室分完以后，再进行一次校验
        if (!googList.isEmpty()) {//如果优秀不为空，选出其中轮转时间最长的，选出其中空余位置最多的
            if (googList.size() == 1) {//如果只有一条的话，不用校验，直接返回
                return googList.get(0).getDeptId();
            }
            //取出空位数最大的几条
            List<CycleDeptCapacityVo> maxGoodList = calcMaxUnDeptCapacity(googList);
            if (maxGoodList.size() == 1) {//如果只有一条的话，不用校验，直接返回
                return maxGoodList.get(0).getDeptId();
            } else {//有多条相同的数据,则根据学生在每个科室的轮转时间，比对出时间最长的
                Set<Long> deptIds = maxGoodList.stream().map(CycleDeptCapacityVo::getDeptId).collect(Collectors.toSet());
                //unDeptList按照deptId获取并按照轮转时长排序
                List<CycleStudentDeptCalcVo> unDeptListByDeptId = calcMaxStudentDeptNum(unDeptList, deptIds);
                return unDeptListByDeptId.get(0).getDeptId();
            }
        } else if (!normalList.isEmpty()){//优秀规则为空,从一般规则里取
            List<CycleDeptCapacityVo> maxNormalList = calcMaxUnDeptCapacity(normalList);
            if (maxNormalList.size() == 1) {//如果只有一条的话，不用校验，直接返回
                return maxNormalList.get(0).getDeptId();
            }else {//有多条，还是取出时间最长的
                Set<Long> deptIds = maxNormalList.stream().map(CycleDeptCapacityVo::getDeptId).collect(Collectors.toSet());
                List<CycleStudentDeptCalcVo> unDeptListByDeptId = calcMaxStudentDeptNum(unDeptList, deptIds);
                return unDeptListByDeptId.get(0).getDeptId();
            }
        }else {//优秀和一般都为空，从差的里取
            //人数都是满的，选择userIds最少的
            List<CycleDeptCapacityVo> minBadList = badList.stream()
                .collect(Collectors.groupingBy(v -> v.getUserIds().size()))
                .values().stream()
                .map(list -> list.stream().min(Comparator.comparing(CycleDeptCapacityVo::getDeptCapacity)).orElse(null))
                .filter(Objects::nonNull)
                .toList();
            if (minBadList.size() == 1) {//如果只有一条的话，不用校验，直接返回
                return minBadList.get(0).getDeptId();
            }else {//有多条,取出时间最长的，优先进
                // TODO: 2023/6/30 这里需要实验下，如果人满了 优先进长的还是进短的，有待考证
                Set<Long> deptIds = minBadList.stream().map(CycleDeptCapacityVo::getDeptId).collect(Collectors.toSet());
                List<CycleStudentDeptCalcVo> unDeptListByDeptId = calcMaxStudentDeptNum(unDeptList, deptIds);
                return unDeptListByDeptId.get(0).getDeptId();
            }
        }
    }

    /**
     * 取出空位最多的数据
     * @param deptList
     * @return
     */
    public static List<CycleDeptCapacityVo> calcMaxUnDeptCapacity(List<CycleDeptCapacityVo> deptList){
        return deptList.stream()
            .collect(Collectors.groupingBy(CycleDeptCapacityVo::getUnDeptCapacity))
            .values().stream()
            .map(list -> list.stream().max(Comparator.comparing(CycleDeptCapacityVo::getDeptCapacity)).orElse(null))
            .filter(Objects::nonNull)
            .toList();
    }

    /**
     * 取出时间最长的数据
     * @param allUnDeptList
     * @param deptIds
     * @return
     */
    public static List<CycleStudentDeptCalcVo> calcMaxStudentDeptNum(List<CycleStudentDeptCalcVo> allUnDeptList,Set<Long> deptIds) {
        return allUnDeptList.stream()
            .filter(dept -> deptIds.contains(dept.getDeptId()))
            .sorted(Comparator.comparing(CycleStudentDeptCalcVo::getStudentDeptNum).reversed()).toList();
    }

}
