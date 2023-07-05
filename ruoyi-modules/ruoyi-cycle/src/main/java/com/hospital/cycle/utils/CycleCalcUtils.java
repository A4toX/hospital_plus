package com.hospital.cycle.utils;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hospital.cycle.domain.*;
import com.hospital.cycle.domain.vo.CycleCalcRecordVo;
import com.hospital.cycle.domain.vo.calc.*;
import com.hospital.cycle.mapper.*;
import org.apache.poi.ss.formula.functions.Index;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.service.DeptService;
import org.dromara.common.core.service.StudentService;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.helper.DataBaseHelper;
import org.dromara.common.redis.utils.RedisUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.hospital.cycle.constant.CycleConstant.CYCLE_GROUP_METHOD_MUST;
import static com.hospital.cycle.utils.CycleUtils.initStudentDept;

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
    public static DeptService deptService = SpringUtils.getBean(DeptService.class);
    public static CycleCalcRecordMapper calcRecordMapper = SpringUtils.getBean(CycleCalcRecordMapper.class);




    /**
     * 获取规则下轮转科室的整体轮转时间
     *
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
            .and(w -> {
                List<CycleGroup> mustGroupList = groupMapper.selectList(Wrappers.<CycleGroup>lambdaQuery()
                    .eq(CycleGroup::getRuleId, ruleId)
                    .eq(CycleGroup::getGroupMethod, CYCLE_GROUP_METHOD_MUST));
                if (!mustGroupList.isEmpty()) {
                    w.in(CycleGroupDept::getGroupId, mustGroupList.stream().map(CycleGroup::getGroupId).collect(Collectors.toList()));
                }
            })
        );
        if (!cycleGroupDeptList.isEmpty()) {
            totalTimeUnit += cycleGroupDeptList.stream().mapToInt(CycleGroupDept::getDeptUnitNum).sum();
        }
        return totalTimeUnit;
    }

    /**
     * 获取规则中所有学生和要排课的科室
     *
     * @param ruleId
     * @return
     */
    public static List<CycleStudentCalcVo> calcAllStudent(Long ruleId) {
        //获取规则
        CycleRule cycleRule = ruleMapper.selectById(ruleId);
        if (cycleRule.getParentId() != 0L) {
            //获取父规则
            CycleRule parentRule = ruleMapper.selectOne(Wrappers.<CycleRule>lambdaQuery()
                .apply(DataBaseHelper.findInSet("rule_id", cycleRule.getAncestors()))
                .eq(CycleRule::getParentId, 0L));
            ruleId = parentRule.getRuleId();
        }
        //获取规则下的学生
        List<CycleStudent> studentList = studentMapper.selectList(Wrappers.<CycleStudent>lambdaQuery()
            .eq(CycleStudent::getRuleId, ruleId));
        if (studentList.isEmpty()) {
            throw new ServiceException("该规则下没有学生");
        }
        Set<Long> userIds = studentList.stream().map(CycleStudent::getUserId).collect(Collectors.toSet());
        //获取所有学生的轮转记录
       /* List<CycleRecord> recordList = recordMapper.selectList(Wrappers.<CycleRecord>lambdaQuery()
            .in(CycleRecord::getRuleId, ruleId)
        );*/
        List<CycleStudentCalcVo> voList = new ArrayList<>();
        //提取每个学生的科室
        studentList.forEach(student -> {
//            List<CycleRecord> studentRecordList = recordList.stream().filter(record -> record.getUserId().equals(student.getUserId())).toList();
            List<CycleRecord> studentRecordList = RedisUtils.getCacheList("cycle:student:"+cycleRule.getRuleId()+":"+student.getUserId());
            if (studentRecordList.isEmpty()) {
                return;
            }
            List<CycleStudentDeptCalcVo> studentDeptCalcVoList = new ArrayList<>();
            studentRecordList.forEach(studentRecord -> {
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


    /**
     * 轮转主主控方法
     * @param allStudentList
     * @param allDeptList
     * @param ruleId
     * @param ruleTotalTimeUnit
     */
    public static void calc(List<CycleStudentCalcVo> allStudentList, List<CycleDeptCalcVo> allDeptList, Long ruleId, Integer ruleTotalTimeUnit) {
        //allDeptList按照必修排序
        allDeptList.sort(Comparator.comparing(CycleDeptCalcVo::getDeptMethod).reversed());
        //过滤出必修的科室
        allStudentList.forEach(student -> {
            List<CycleDeptCalcVo> allDeptListCopy = new ArrayList<>(allDeptList);
            //从allDeptList中去除unDeptList中不一样的科室
            allDeptListCopy.removeIf(dept -> student.getDeptList().stream().noneMatch(unDept -> unDept.getDeptId().equals(dept.getDeptId())));
            //获取学生的完整排班记录
            List<CycleDeptCalcVo> realSortList = initStudentRealSort(allDeptListCopy,student.getDeptList());
            List<Long> deptIdList = allDeptListCopy.stream().map(CycleDeptCalcVo::getDeptId).toList();
            student.getStudentSortList().addAll(deptIdList);//增加排班
            //对轮转进行授时
            CycleUtils.studentCycleAddDate(student.getStudentSortList(), ruleId, student.getUserId());
            //信息更新
            updateDeptAndStudent(student,realSortList);
            //把allDeptList中的第一位换到最后一位
            CycleDeptCalcVo firstDept = allDeptList.remove(0);
            allDeptList.add(firstDept);
        });

    }

    /**
     * 校验是否有超限，如果有寻找可以调换的单位,并返回新的排序
     * @param realSortList
     * @param allDeptListCopy
     * @param deptList
     * @return
     */
    private static List<Long> validateStudentSort(List<CycleDeptCalcVo> realSortList,List<CycleStudentDeptCalcVo> deptList,Long ruleId,Integer ruleTotalTimeUnit) {
        List<CycleCalcRecord> records = calcRecordMapper.selectList(Wrappers.<CycleCalcRecord>lambdaQuery()
            .eq(CycleCalcRecord::getRuleId, ruleId)
            .gt(CycleCalcRecord::getUnDeptCapacity, 0)
            .orderByAsc(CycleCalcRecord::getMargin)
        );
        // 创建 deptId 到 id 列表的映射
        Map<Long, List<Integer>> deptIdToIdsMap = new HashMap<>();
        // 遍历 records 列表，填充映射
        for (CycleCalcRecord record : records) {
            if (!deptIdToIdsMap.containsKey(record.getDeptId())) {
                deptIdToIdsMap.put(record.getDeptId(), new ArrayList<>());
            }
            deptIdToIdsMap.get(record.getDeptId()).add(record.getDeptIndex());
        }

        // 用于存储空闲时间及其出现次数的Map
        Map<Integer, Integer> freeTimeCountMap = new HashMap<>();

        for (Map.Entry<Long, List<Integer>> entry : deptIdToIdsMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        // 遍历映射
        for (Map.Entry<Long, List<Integer>> entry : deptIdToIdsMap.entrySet()) {
            Long deptId = entry.getKey();
            List<Integer> indexList = entry.getValue();
            indexList.forEach(index -> {
                if (!freeTimeCountMap.containsKey(index)) {
                    freeTimeCountMap.put(index, 1);
                } else {
                   freeTimeCountMap.put(index, freeTimeCountMap.get(index) + 1);
                }
            });
        }
        System.out.println(freeTimeCountMap);

        // 遍历映射，找到具有至少两个 id 的 deptId 以及对应的 id 列表
        throw new ServiceException("未完成");
//        return null;
//        return jList;
    }


    /**
     * 返回学生的完整排序
     * @param allDeptListCopy
     * @param deptList
     * @return
     */
    private static List<CycleDeptCalcVo> initStudentRealSort(List<CycleDeptCalcVo> allDeptListCopy, List<CycleStudentDeptCalcVo> deptList) {
        List<CycleDeptCalcVo> realSort = new ArrayList<>();
        for (int i=0;i<allDeptListCopy.size();i++){
            CycleDeptCalcVo dept = allDeptListCopy.get(i);
            CycleStudentDeptCalcVo studentDeptCalcVo = deptList.stream().filter(deptVo -> deptVo.getDeptId().equals(dept.getDeptId())).findFirst().orElse(null);
            if (studentDeptCalcVo==null){
                throw new ServiceException("学生的科室信息不完整");
            }
            //根据学生在本科室的轮转时间，完成初始化
            if(studentDeptCalcVo.getStudentDeptNum()==1){//加一个
                realSort.add(dept);
            }else {//有多个
                for (int j=0;j<studentDeptCalcVo.getStudentDeptNum();j++){
                    realSort.add(dept);
                }
            }
        }
        return realSort;
    }


    /**
     * 更新学生和科室信息
     * @param student
     * @param deptList
     */
    private static void updateDeptAndStudent(CycleStudentCalcVo student, List<CycleDeptCalcVo> deptList) {
        for (int i = 0;i<deptList.size();i++){
            Integer index = i+1;
            Long deptId = deptList.get(i).getDeptId();
            CycleCalcRecord calcRecord = calcRecordMapper.selectOne(Wrappers.<CycleCalcRecord>lambdaQuery()
                .eq(CycleCalcRecord::getDeptIndex, index)
                .eq(CycleCalcRecord::getDeptId, deptId)
            );
            calcRecord.setUnDeptCapacity(calcRecord.getUnDeptCapacity()-1);//科室剩余容量减1
            double margin = (double) calcRecord.getUnDeptCapacity() / calcRecord.getDeptCapacity() * 100;
            double roundedMargin = Math.round(margin * 100.0) / 100.0;
            calcRecord.setMargin(roundedMargin);//重新计算空闲率
            if (calcRecord.getUserIds()==null){
                calcRecord.setUserIds(student.getUserId().toString());
            }else {
                calcRecord.setUserIds(calcRecord.getUserIds()+","+student.getUserId());//增加学生id
            }
            calcRecordMapper.updateById(calcRecord);
        }
    }

    /**
     * 获取所有需要排班的科室
     * @param ruleId
     * @return
     */
    public static List<CycleDeptCalcVo> calcAllDept(Long ruleId) {
        //获取规则下所有科室
        List<CycleGroupDept> cycleGroupDeptList = groupDeptMapper.selectList(Wrappers.<CycleGroupDept>lambdaQuery()
            .eq(CycleGroupDept::getRuleId, ruleId));
        if (cycleGroupDeptList.size() == 0) {
            return null;
        }
        List<CycleDeptCalcVo> voList = new ArrayList<>();
        cycleGroupDeptList.forEach(dept -> {
            CycleDeptCalcVo  cycleDeptCalcVo = new CycleDeptCalcVo();
            cycleDeptCalcVo.setDeptUnit(dept.getDeptUnitNum());
            cycleDeptCalcVo.setDeptId(dept.getDeptId());
            cycleDeptCalcVo.setDeptMethod(dept.getGroupMethod());
            voList.add(cycleDeptCalcVo);
        });
        return voList;
    }

}
