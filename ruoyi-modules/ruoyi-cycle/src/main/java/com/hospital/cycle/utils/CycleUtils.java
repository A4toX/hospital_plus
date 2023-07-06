package com.hospital.cycle.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hospital.cycle.domain.*;
import com.hospital.cycle.domain.vo.calc.CycleDeptCalcVo;
import com.hospital.cycle.mapper.*;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.service.StudentService;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.redis.utils.RedisUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.hospital.cycle.constant.CycleConstant.*;
import static com.hospital.cycle.constant.CycleRedisConstant.CYCLE_CALC_DEPT_PREFIX;
import static com.hospital.cycle.constant.CycleRedisConstant.CYCLE_CALC_STUDENT_PREFIX;

public class CycleUtils {
    public static CycleRuleMapper ruleMapper = SpringUtils.getBean(CycleRuleMapper.class);
    public static CycleGroupMapper groupMapper = SpringUtils.getBean(CycleGroupMapper.class);
    public static CycleGroupDeptMapper groupDeptMapper = SpringUtils.getBean(CycleGroupDeptMapper.class);
    public static CycleStudentMapper cycleGroupMapper = SpringUtils.getBean(CycleStudentMapper.class);
    public static CycleRuleBaseMapper ruleBaseMapper = SpringUtils.getBean(CycleRuleBaseMapper.class);
    public static CycleUserDeptMapper userDeptMapper = SpringUtils.getBean(CycleUserDeptMapper.class);
    public static CycleStudentMapper studentMapper = SpringUtils.getBean(CycleStudentMapper.class);
    public static StudentService studentService = SpringUtils.getBean(StudentService.class);
    public static CycleRecordMapper recordMapper = SpringUtils.getBean(CycleRecordMapper.class);
    public static CycleCalcRecordMapper calcRecordMapper = SpringUtils.getBean(CycleCalcRecordMapper.class);

    /**
     * 根据id初始化学生的轮转数据
     * @param ruleId
     */
    public static void initStudentDept(Long ruleId) {
        CycleRule cycleRule = ruleMapper.selectById(ruleId);
        if (cycleRule == null) {
            throw new ServiceException("轮转规则不存在");
        }
        //获取规则下的所有学生
        List<Long> studentIds = studentMapper.selectList(Wrappers.<CycleStudent>lambdaQuery().eq(CycleStudent::getRuleId, ruleId)).stream().map(CycleStudent::getUserId).toList();
        if (studentIds.size() == 0) {//没有获取到就返回
            return;
        }
        studentIds.forEach(userId->{
            List<CycleRecord> cycleRecordList = new ArrayList<>();
            List<CycleGroup> cycleGroupList;
            //判断规则组是否需要区分专业
            if (YES.equals(cycleRule.getBaseFlag())) {
                //获取学生专业id
                Long baseId = studentService.selectStudentBaseIdByUserId(userId);
                cycleGroupList = groupMapper.selectList(Wrappers.<CycleGroup>lambdaQuery().eq(CycleGroup::getRuleId, ruleId).eq(CycleGroup::getBaseId, baseId));
            }else {
                cycleGroupList = groupMapper.selectList(Wrappers.<CycleGroup>lambdaQuery().eq(CycleGroup::getRuleId, ruleId));
            }
               if (cycleGroupList.size() == 0) {
                   return;
               }
               cycleGroupList.forEach(group->{
                   if (CYCLE_GROUP_ELECTIVE.equals(group.getGroupType())){//选修的数据不处理
                       return;
                   }
                   //获取组下所有科室
                   List<CycleGroupDept> cycleGroupDeptList = groupDeptMapper.selectList(Wrappers.<CycleGroupDept>lambdaQuery().eq(CycleGroupDept::getGroupId, group.getGroupId()));
                   if (cycleGroupDeptList.size() == 0) {
                       return;
                   }

                   switch (group.getGroupMethod()){
                       case CYCLE_GROUP_METHOD_MUST:
                            //新增
                           cycleGroupDeptList.forEach(groupDept->{
                                CycleRecord cycleRecord = new CycleRecord();
                                cycleRecord.setRuleId(ruleId);//规则id
                                cycleRecord.setGroupId(group.getGroupId());//规则组id
                                cycleRecord.setUserId(userId);//用户id
                                cycleRecord.setDeptId(groupDept.getDeptId());//科室id
                                cycleRecord.setDeptType(group.getGroupMethod());//科室所属规则组方法类型
                                cycleRecord.setDeptNum(groupDept.getDeptUnitNum());//科室轮转时长
                                if (group.getBaseId()!=null){
                                    cycleRecord.setBaseId(group.getBaseId());
                                }
                                cycleRecordList.add(cycleRecord);
                            });
                            break;
                       case CYCLE_GROUP_METHOD_ELECTIVE:
                            //任选其几
                            Integer deptNum = group.getMethodNumber();
                            //所有人都选择前deptNum个科室
                            List<CycleGroupDept> optionalDeptList = optionalDept(cycleGroupDeptList, deptNum);
                           optionalDeptList.forEach(groupDept->{
                               CycleRecord cycleRecord = new CycleRecord();
                               cycleRecord.setRuleId(ruleId);//规则id
                               cycleRecord.setGroupId(group.getGroupId());//规则组id
                               cycleRecord.setUserId(userId);//用户id
                               cycleRecord.setDeptId(groupDept.getDeptId());//科室id
                               cycleRecord.setDeptType(group.getGroupMethod());//科室所属规则组方法类型
                               cycleRecord.setDeptNum(groupDept.getDeptUnitNum());//科室轮转时长
                                if (group.getBaseId()!=null){
                                    cycleRecord.setBaseId(group.getBaseId());
                                }
                                cycleRecordList.add(cycleRecord);
                            });
                           //在任选科室的人数选则上加一
                           optionalDeptList.forEach(dept->{
                                dept.setAssignedNum(dept.getAssignedNum()+1);
                           });
                            groupDeptMapper.updateBatchById(optionalDeptList);//更新
                            break;
                       default:
                           break;
                   }
                   //获取学生的选修科室
                   List<CycleUserDept> userDeptList = userDeptMapper.selectList(Wrappers.<CycleUserDept>lambdaQuery().eq(CycleUserDept::getUserId, userId));
                   if (userDeptList.size() == 0) {
                       return;
                   }

                   userDeptList.forEach(userDept->{
                       CycleRecord cycleRecord = new CycleRecord();
                       cycleRecord.setRuleId(ruleId);//规则id
                       cycleRecord.setGroupId(group.getGroupId());//规则组id
                       cycleRecord.setUserId(userId);//用户id
                       cycleRecord.setDeptId(userDept.getDeptId());//科室id
                       cycleRecord.setDeptType(group.getGroupMethod());//科室所属规则组方法类型
                       cycleRecord.setDeptNum(userDept.getSelectTime());//科室轮转时长
                      if (group.getBaseId()!=null){
                        cycleRecord.setBaseId(group.getBaseId());
                      }
                      cycleRecordList.add(cycleRecord);
                 });
               });
            //批量新增
            RedisUtils.setCacheList(CYCLE_CALC_STUDENT_PREFIX+":"+ruleId+":"+userId,cycleRecordList);
//            recordMapper.insertBatch(cycleRecordList);
           });

    }


    /**
     * 初始化规则中的所有科室
     *
     * @param ruleId
     * @param ruleTotalTimeUnit
     * @return
     */
    public static void initAllDept(Long ruleId, Integer ruleTotalTimeUnit) {
        //获取规则下所有科室
        List<CycleGroupDept> cycleGroupDeptList = groupDeptMapper.selectList(Wrappers.<CycleGroupDept>lambdaQuery().eq(CycleGroupDept::getRuleId, ruleId));
        if (cycleGroupDeptList.size() == 0) {
            throw new ServiceException("该规则下没有科室");
        }

        cycleGroupDeptList.forEach(dept -> {
            List<CycleCalcRecord> dataList = new ArrayList<>();
            //获取本科室需要轮转的学生人数
            List<CycleRecord> recordList = recordMapper.selectList(Wrappers.<CycleRecord>lambdaQuery()
                .eq(CycleRecord::getRuleId, ruleId)
                .eq(CycleRecord::getDeptId, dept.getDeptId())
            );
            //获取所有数据中deptNum的总和
            int studentUnitNum = recordList.stream().mapToInt(CycleRecord::getDeptNum).sum();

            if (ruleTotalTimeUnit < 0) {
                throw new ServiceException("轮转总时间不能小于0");
            }

            for (int i = 1; i <= ruleTotalTimeUnit; i++) {
                CycleCalcRecord cycleCalcRecord = new CycleCalcRecord();
                cycleCalcRecord.setDeptId(dept.getDeptId());
                cycleCalcRecord.setGroupId(dept.getGroupId());
                cycleCalcRecord.setDeptIndex(i);
                //科室容量 = 总轮转时长/总时间单位
                cycleCalcRecord.setDeptCapacity((int) Math.ceil((double) studentUnitNum / ruleTotalTimeUnit));
                //还没开始排，剩余容量和科室容量一致
                cycleCalcRecord.setUnDeptCapacity(cycleCalcRecord.getDeptCapacity());
                double margin = (double) cycleCalcRecord.getUnDeptCapacity() / cycleCalcRecord.getDeptCapacity() * 100;
                double roundedMargin = Math.round(margin * 100.0) / 100.0;
                cycleCalcRecord.setMargin(roundedMargin);//初始化时空闲率百分百

                cycleCalcRecord.setRuleId(ruleId);
                dataList.add(cycleCalcRecord);

            }
            RedisUtils.setCacheList(CYCLE_CALC_DEPT_PREFIX+ruleId+":"+dept.getDeptId(),dataList);
        });
//        calcRecordMapper.insertBatch(dataList);
    }

    /**
     * 任选科室工具类
     * @param cycleGroupDeptList
     * @param deptNum
     * @return
     */
    public static List<CycleGroupDept> optionalDept(List<CycleGroupDept> cycleGroupDeptList, Integer deptNum) {
        List<CycleGroupDept> groupDeptList = new ArrayList<>();
        for (int i = 0; i < deptNum; i++) {
            //按照assignedNum由小到大排序
            cycleGroupDeptList.sort((o1, o2) -> o1.getAssignedNum().compareTo(o2.getAssignedNum()));
            groupDeptList.add(cycleGroupDeptList.get(0));
            //选中的科室从列表中删除
            cycleGroupDeptList.remove(0);
        }
        return groupDeptList;
    }

    /**
     * 为学生的轮转科室添加时间
     * @param studentSortList
     * @param ruleId
     * @param userId
     */
    public static void studentCycleAddDate(List<Long> studentSortList,Long ruleId,Long userId){
        //获取rule的开始时间
        CycleRule cycleRule = ruleMapper.selectById(ruleId);
        if (StringUtils.isEmpty(cycleRule.getStartDate())){
            throw new ServiceException("没有设置规则开始时间");
        }
        String startDate= cycleRule.getStartDate();
        for (int i=0;i<studentSortList.size();i++){
            //获取学生的轮转记录
            CycleRecord record = recordMapper.selectOne(Wrappers.<CycleRecord>lambdaQuery()
                .eq(CycleRecord::getUserId,userId)
                .eq(CycleRecord::getRuleId, ruleId)
                .eq(CycleRecord::getDeptId, studentSortList.get(i))
            );
            //根据startDate对科室进行排序
            record.setStartTime(startDate);
            String endDate;
            //根据时间单位判断偏移
            if (CYCLE_UNIT_MONTH.equals(cycleRule.getRuleUnit())){
                endDate = DateUtil.formatDate(DateUtil.offsetDay(DateUtil.offsetMonth(DateUtil.parse(startDate), record.getDeptNum()),-1));
            }else {
                endDate = DateUtil.formatDate(DateUtil.offsetDay(DateUtil.offsetWeek(DateUtil.parse(startDate), record.getDeptNum()),-1));
            }
            String checkMonth = endDate.substring(5,7);//月
            String checkDay = endDate.substring(8,10);//周
            if ("01".equals(checkDay)){
                //如果是每月第一天，往前推一天
                endDate = DateUtil.formatDate(DateUtil.offsetDay(DateUtil.parse(endDate), -1));
            }
            Set<String> monthsWith31Days = new HashSet<>(Arrays.asList("1", "3", "5", "7", "8", "10", "12"));


            if (monthsWith31Days.contains(checkMonth) && "30".equals(checkDay)) {
                endDate = DateUtil.formatDate(DateUtil.offsetDay(DateUtil.parse(endDate), 1));
            }

            record.setEndTime(endDate);
            //新的开始时间等于上一个科室的结束时间往后推一天
            startDate = DateUtil.formatDate(DateUtil.offsetDay(DateUtil.parse(endDate), 1));
            record.setCycleRecordIndex(i+1);
            recordMapper.updateById(record);
        }
    }

    /**
     * 获取轮转导出表头(学员维护-时间表头)
     */
    public static List<List<String>> getCycleExportHeadByUserWtihTime(Long ruleId){
        List<List<String>> headList = new ArrayList<>();
        // TODO: 2023/7/1 后期要添加是否跳过节假日的规则
        CycleRule cycleRule = ruleMapper.selectById(ruleId);
        //新增首列表头
        List<String> firstHeadList = new ArrayList<>();
        firstHeadList.add("科室\\时间");
        headList.add(firstHeadList);
        //获取规则的轮转时间
        Integer ruleTotalTimeUnit = CycleCalcUtils.getTotalTimeUnit(ruleId);
        String startDate= cycleRule.getStartDate();
        //根据类型不同，往后推一月或者一周
        for (int i = 0;i<ruleTotalTimeUnit;i++){
            List<String> timeHead = new ArrayList<>();
            String endDate;
            if (CYCLE_UNIT_MONTH.equals(cycleRule.getRuleUnit())){
                endDate = DateUtil.formatDate(DateUtil.offsetDay(DateUtil.offsetMonth(DateUtil.parse(startDate), 1),-1));
            }else {
                endDate = DateUtil.formatDate(DateUtil.offsetDay(DateUtil.offsetWeek(DateUtil.parse(startDate), 1),-1));
            }
            String checkMonth = endDate.substring(5,7);//月
            String checkDay = endDate.substring(8,10);//周
            if ("01".equals(checkDay)){
                //如果是每月第一天，往前推一天
                endDate = DateUtil.formatDate(DateUtil.offsetDay(DateUtil.parse(endDate), -1));
            }
            Set<String> monthsWith31Days = new HashSet<>(Arrays.asList("1", "3", "5", "7", "8", "10", "12"));


            if (monthsWith31Days.contains(checkMonth) && "30".equals(checkDay)) {
                endDate = DateUtil.formatDate(DateUtil.offsetDay(DateUtil.parse(endDate), 1));
            }
            timeHead.add(startDate+"-"+endDate);
            headList.add(timeHead);
            //新的开始时间等于上一个科室的结束时间往后推一天
            startDate = DateUtil.formatDate(DateUtil.offsetDay(DateUtil.parse(endDate), 1));
        }

    return headList;
    }

    public static List<List<String>> getCycleExportHeadByUserWtihUnit(Long ruleId){
        CycleRule cycleRule = ruleMapper.selectById(ruleId);
        if (cycleRule == null){
            throw new ServiceException("没有找到对应的规则");
        }
        String unitName;
        if (CYCLE_UNIT_MONTH.equals(cycleRule.getRuleUnit())) {
            unitName = "月";
        }else {
            unitName = "周";
        }
        List<List<String>> headList = new ArrayList<>();
        Integer ruleTotalTimeUnit = CycleCalcUtils.getTotalTimeUnit(ruleId);
        List<String> firstHeadList = new ArrayList<>();//首列表头
        firstHeadList.add("科室\\时间");
        headList.add(firstHeadList);
        for (int i=0;i<ruleTotalTimeUnit;i++){
            List<String> unitHead = new ArrayList<>();
            unitHead.add("第"+(i+1)+unitName);
            headList.add(unitHead);
        }
        return headList;
    }

}
