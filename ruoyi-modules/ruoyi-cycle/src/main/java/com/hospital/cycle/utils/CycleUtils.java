package com.hospital.cycle.utils;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hospital.cycle.domain.*;
import com.hospital.cycle.mapper.*;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.service.StudentService;
import org.dromara.common.core.utils.SpringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hospital.cycle.constant.CycleConstant.*;

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

    /**
     * 根据id处理学生的轮转
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
                   if (CYCLE_GROUP_ELECTIVE.equals(group.getGroupType())){//选修的数据先不处理
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
                            //随机选择deptNum个科室
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
                            break;
                       default:
                           break;
                   }
                   //获取学生的选修科室
                   List<CycleUserDept> userDeptList = userDeptMapper.selectList(Wrappers.<CycleUserDept>lambdaQuery().eq(CycleUserDept::getUserId, userId));
                   if (userDeptList.size() == 0) {
                       return;
                   }
                   // TODO: 2023/6/28 明天在record表上加上科室类型，方便排轮转的时候用
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
            recordMapper.insertBatch(cycleRecordList);
           });

    }

    /**
     * 任选科室工具类
     * @param cycleGroupDeptList
     * @param deptNum
     * @return
     */
    private static List<CycleGroupDept> optionalDept(List<CycleGroupDept> cycleGroupDeptList, Integer deptNum) {
        List<CycleGroupDept> groupDeptList = new ArrayList<>();
        for (int i = 0; i < deptNum; i++) {
            //按照assignedNum由小到大排序
            cycleGroupDeptList.sort((o1, o2) -> o1.getAssignedNum().compareTo(o2.getAssignedNum()));
            groupDeptList.add(cycleGroupDeptList.get(0));
            //选中的科室assignedNum+1
            cycleGroupDeptList.get(0).setAssignedNum(cycleGroupDeptList.get(0).getAssignedNum()+1);
        }
        return groupDeptList;
    }


}
