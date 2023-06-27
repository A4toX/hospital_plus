package com.hospital.cycle.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hospital.cycle.domain.*;
import com.hospital.cycle.domain.bo.CycleStudentBo;
import com.hospital.cycle.mapper.*;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.service.StudentService;
import org.dromara.common.core.service.domain.Student;
import org.dromara.common.core.utils.SpringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hospital.cycle.constant.CycleConstant.*;

/**
 * 校验工具类
 */
public class CycleValidUtils {
    public static CycleRuleMapper ruleMapper = SpringUtils.getBean(CycleRuleMapper.class);
    public static CycleGroupMapper groupMapper = SpringUtils.getBean(CycleGroupMapper.class);
    public static CycleGroupDeptMapper groupDeptMapper = SpringUtils.getBean(CycleGroupDeptMapper.class);
    public static CycleStudentMapper cycleGroupMapper = SpringUtils.getBean(CycleStudentMapper.class);
    public static CycleRuleBaseMapper ruleBaseMapper = SpringUtils.getBean(CycleRuleBaseMapper.class);
    public static CycleUserDeptMapper userDeptMapper = SpringUtils.getBean(CycleUserDeptMapper.class);
    public static CycleStudentMapper studentMapper = SpringUtils.getBean(CycleStudentMapper.class);
    public static StudentService studentService = SpringUtils.getBean(StudentService.class);


    /**
     * 校验用户提交的选课信息是否符合规则
     * @param userDepts
     * @return
     */
    public  static void ValidstudentSelectDept(List<CycleUserDept> userDepts) {
        //判断groupUserDepts中的ruleId是否都相同，不相同返回异常
        if (userDepts.stream().anyMatch(userDept -> !userDept.getRuleId().equals(userDepts.get(0).getRuleId()))){
            throw new ServiceException("每次提交，只允许提交同规则同阶段的数据");
        }
        //获取规则
        CycleRule cycleRule = ruleMapper.selectById(userDepts.get(0).getRuleId());
        //校验是否存在
        if (cycleRule==null){
            throw new ServiceException("规则不存在");
        }
        //判断规则是否开启了选课
        if(NO.equals(cycleRule.getDeptSelectFlag())){
            throw new ServiceException("此规则未开启选课");
        }
        if (CYCLE_STATUS_COMPLETE.equals(cycleRule.getRuleStatus())){
            throw new ServiceException("此规则已结束");
        }
        //获取轮转规则组
        List<CycleGroup> groups = groupMapper.selectList(Wrappers.<CycleGroup>lambdaQuery()
            .eq(CycleGroup::getRuleId,cycleRule.getRuleId())
            .eq(CycleGroup::getGroupType, CYCLE_GROUP_ELECTIVE));
        if (groups==null||groups.isEmpty()){
            throw new ServiceException("此规则下没有需要选课的规则组");
        }
        //循环校验每个规则组中的数据
        groups.forEach(group -> {
            //从groupUserDepts中获取规则组中的选课数据
            List<CycleUserDept> groupUserDepts = userDepts.stream().filter(userDept -> userDept.getGroupId().equals(group.getGroupId())).toList();
            if (groupUserDepts==null||groupUserDepts.isEmpty()){
                throw new ServiceException("规则组"+group.getGroupName()+"没有选课数据");
            }
            //判断规则中是否有这些科室
            List<Long> deptIds = groupUserDepts.stream().map(CycleUserDept::getDeptId).collect(Collectors.toList());
            List<CycleGroupDept> groupDepts = groupDeptMapper.selectList(Wrappers.<CycleGroupDept>lambdaQuery()
                .eq(CycleGroupDept::getGroupId,group.getGroupId())
                .in(CycleGroupDept::getDeptId,deptIds));
            if (groupDepts==null||groupDepts.isEmpty()){
                throw new ServiceException("数据错误"+group.getGroupName()+"中没有指定科室");
            }
            //循环处理每个规则组中的数据
            switch (group.getGroupMethod()){
                case CYCLE_GROUP_METHOD_MUST:
                    throw new ServiceException("选修科室不允许使用必选规则");
                case CYCLE_GROUP_METHOD_ELECTIVE://任选其几
                    if (group.getMethodNumber()!=groupUserDepts.size()){
                        throw new ServiceException("规则组"+group.getGroupName()+"选课数量不符合规则");
                    }
                    //判断groupUserDepts每条数据的时间都需相同
                    Integer selectTime = groupUserDepts.get(0).getSelectTime();
                    if (groupUserDepts.stream().anyMatch(userDept -> !userDept.getSelectTime().equals(selectTime))){
                        throw new ServiceException("规则组"+group.getGroupName()+"选课时间不符合规则");
                    }
                    //判断时间相加是否超过规则组的时间
                    Integer sumTime = groupUserDepts.stream().mapToInt(CycleUserDept::getSelectTime).sum();
                    if (!sumTime.equals(group.getGroupUnitNum())){
                        throw new ServiceException("规则组"+group.getGroupName()+"选课时间与要求时间不同");
                    }
                    break;
                case CYCLE_GROUP_METHOD_TIME://满足时长，不限数
                    //判断时时长是否符合要求
                    Integer sumTime2 = groupUserDepts.stream().mapToInt(CycleUserDept::getSelectTime).sum();
                    if (!sumTime2.equals(group.getGroupUnitNum())){
                        throw new ServiceException("规则组"+group.getGroupName()+"选课时间不符合规则");
                    }
            }
        });

    }

    /**
     * 校验groupDept的创建是否符合规则
     * @param groupDepts
     */
    public static void validGroupDept(List<CycleGroupDept> groupDepts) {
        //判断参数中是否有相同的科室
        List<Long> deptIds = groupDepts.stream().map(CycleGroupDept::getDeptId).toList();
        if (deptIds.size()!=new HashSet<>(deptIds).size()){
            throw new ServiceException("科室重复");
        }


        //校验规则是否能够修改
        CycleRule cycleRule = ruleMapper.selectById(groupDepts.get(0).getRuleId());
        if (cycleRule == null) {
            throw new ServiceException("规则不存在");
        }
        if (CYCLE_STATUS_COMPLETE.equals(cycleRule.getRuleStatus())) {
            throw new ServiceException("规则已经完成排班，不能修改");
        }
        //根据方法判断
        CycleGroup cyclegroup = groupMapper.selectById(groupDepts.get(0).getGroupId());
        if (cyclegroup == null) {
            throw new ServiceException("规则组不存在");
        }
        //判断规则内科室是否已经在其他规则组添加
        List<CycleGroupDept> cycleGroupDepts = groupDeptMapper.selectList(Wrappers.<CycleGroupDept>lambdaQuery()
            .eq(CycleGroupDept::getRuleId, cycleRule.getRuleId())
            .in(CycleGroupDept::getDeptId, groupDepts.stream().map(CycleGroupDept::getDeptId).collect(Collectors.toSet()))
            .ne(CycleGroupDept::getGroupId, cyclegroup.getGroupId())
        );
        if (!cycleGroupDepts.isEmpty()) {
            throw new ServiceException("科室在此规则其他组中已被添加");
        }

        //所选科室的总轮转时间
        List<Integer> deptUnitNums = groupDepts.stream().map(CycleGroupDept::getDeptUnitNum).toList();
        //相加
        Integer sum = deptUnitNums.stream().reduce(0, Integer::sum);

        switch (cyclegroup.getGroupMethod()) {
            case CYCLE_GROUP_METHOD_MUST://必选只需要校验时间是否满足
                if (!sum.equals(cyclegroup.getGroupUnitNum())) {
                    throw new ServiceException("必修规则中，所有科室轮转时间必须等于规则组的轮转时间");
                }
                break;
            case CYCLE_GROUP_METHOD_ELECTIVE://任选其几
                Integer electiveNum = cyclegroup.getMethodNumber();//任选数量
                if (groupDepts.size() < electiveNum) {
                    throw new ServiceException("科室数量不能小于任选数量");
                }
                //判断groupDepts每条数据的时间都需相同
                if (groupDepts.stream().map(CycleGroupDept::getDeptUnitNum).distinct().count()!=1){
                    throw new ServiceException("科室轮转时间必须相同");
                }
                Integer deptUnitNum = groupDepts.get(0).getDeptUnitNum();//科室轮转时间
                //确定任选数量相加是否等于规则组的轮转时间
                if (cyclegroup.getGroupUnitNum() != deptUnitNum * electiveNum) {
                    throw new ServiceException("科室轮转时间必须等于规则组的轮转时间");
                }
                break;
            case CYCLE_GROUP_METHOD_TIME://选修，时长满足
                //判断时间是否超过规则组的时间
                if (sum > cyclegroup.getGroupUnitNum()) {
                    throw new ServiceException("选修规则中，总轮转时间(最小值)已经超过规则组轮转时间");
                }
                break;
            default:
                throw new ServiceException("错误的规则组方法");

        }
    }

    /**
     * 校验规则组是否能够创建和修改
     * @param cycleGroup
     */
    public static void validGroup(CycleGroup cycleGroup){
        //校验规则
        CycleRule cycleRule = ruleMapper.selectById(cycleGroup.getRuleId());
        if (cycleRule == null) {
            throw new ServiceException("规则不存在");
        }
        if (CYCLE_STATUS_COMPLETE.equals(cycleRule.getRuleStatus())) {
            throw new ServiceException("规则已经排班，不能修改");
        }
        //如果规则开启了专业,则组必须选择专业
        if(YES.equalsIgnoreCase(cycleRule.getBaseFlag())){
            if(cycleGroup.getBaseId() == null){
                throw new ServiceException("请选择专业");
            }
            //校验专业是否已经和规则组关联
            CycleRuleBase cycleRuleBase = ruleBaseMapper.selectOne(Wrappers.<CycleRuleBase>lambdaQuery()
                .eq(CycleRuleBase::getRuleId, cycleRule.getRuleId())
                .eq(CycleRuleBase::getBaseId, cycleGroup.getBaseId()));
            if (cycleRuleBase == null) {
                throw new ServiceException("专业与规则不匹配");
            }
        }else {
            if (cycleGroup.getBaseId()!=null){
                throw new ServiceException("规则不允许选择专业");
            }
        }


        //校验规则组类型
        switch (cycleGroup.getGroupType()) {
            case CYCLE_GROUP_MUST -> {
                //必修不能使用时间满足方法
                if (CYCLE_GROUP_METHOD_TIME.equals(cycleGroup.getGroupMethod())) {
                    throw new ServiceException("必修规则组不能使用时间满足方法");
                }
            }
            case CYCLE_GROUP_ELECTIVE -> {
                //选修不能使用必修方法
                if (CYCLE_GROUP_METHOD_MUST.equals(cycleGroup.getGroupMethod())) {
                    throw new ServiceException("选修规则组不能使用必修方法");
                }
                //如果选修规则已经开启，则不能新增选修方法
                if (YES.equals(cycleRule.getDeptSelectFlag())){
                    throw new ServiceException("选修已经开启，不能新增选修规则组");
                }
            }
            default -> throw new ServiceException("错误的规则组类型");
        }

        //校验规则组方法
        switch (cycleGroup.getGroupMethod()) {
            case CYCLE_GROUP_METHOD_MUST -> {
                //类型为必修时不能填写任选数
                if(cycleGroup.getMethodNumber()!=null){
                    throw new ServiceException("必修规则组不能填写任选数");
                }
            }
            case CYCLE_GROUP_METHOD_ELECTIVE -> {//任选其几
                //任选其几时任选数不能为空
                if(cycleGroup.getMethodNumber()==null){
                    throw new ServiceException("任选其几规则任选数不能为空");
                }
                //如果任选数不能被科室数整除
                if(cycleGroup.getGroupUnitNum()%cycleGroup.getMethodNumber()!=0){
                    throw new ServiceException("请修改科室数量或任选数，因为此规则无法操作");
                }
            }
            case CYCLE_GROUP_METHOD_TIME -> {
                //类型为必修时不能填写任选数
                if(cycleGroup.getMethodNumber()!=null){
                    throw new ServiceException("时长满足规则不能填写任选数");
                }
            }
            default -> throw new ServiceException("错误的规则组方法");
        }
        //校验同名
        Long count = groupMapper.selectCount(new QueryWrapper<CycleGroup>()
            .eq("rule_id", cycleGroup.getRuleId())
            .eq("group_name", cycleGroup.getGroupName())
            .ne(cycleGroup.getGroupId()!=null,"group_id",cycleGroup.getGroupId()));
        if (count>0){
            throw new ServiceException("同规则下的规则组名称请勿重复");
        }

        if (cycleGroup.getGroupId()!=null){//修改校验
            //校验是否有关联数据
            List<CycleGroupDept> groupDepts = groupDeptMapper.selectList(new QueryWrapper<CycleGroupDept>().eq("group_id", cycleGroup.getGroupId()));
            if (CollectionUtil.isNotEmpty(groupDepts)){
                throw new ServiceException("请删除其下科室在进行修改");
            }
        }

    }


    public static void validCycleStudent(List<CycleStudent> list){
        //校验轮转状态
        if (list.isEmpty()){
            throw new ServiceException("请选择学员");
        }
        Long ruleId = list.get(0).getRuleId();
        CycleRule cycleRule = ruleMapper.selectById(ruleId);
        if (cycleRule == null){
            throw new ServiceException("轮转规则不存在");
        }
        if (CYCLE_STATUS_COMPLETE.equals(cycleRule.getRuleStatus())){
            throw new ServiceException("轮转规则已排，不能再添加学员");
        }
        //判断规则是否为主规则
        if (cycleRule.getParentId()!=0){
            throw new ServiceException("子规则不能添加学员");
        }

        //获取list中所有的userId
        Set<Long> userIds = list.stream().map(CycleStudent::getUserId).collect(java.util.stream.Collectors.toSet());
        List<Student> students = studentService.selectStudentByUserIds(userIds);
        if (students==null||students.isEmpty()||students.size()!=userIds.size()){
            throw new ServiceException("选择人员有误，请重新选择");
        }
        //如果规则开启了专业，判断学员是否有专业，并且专业是否符合
        if(YES.equals(cycleRule.getBaseFlag())){
            List<Long> userBaseId = students.stream().map(Student::getBaseId).distinct().toList();
            //获取规则的专业
            List<CycleRuleBase> ruleBases = ruleBaseMapper.selectList(Wrappers.<CycleRuleBase>lambdaQuery().eq(CycleRuleBase::getRuleId, ruleId));
            if (ruleBases.isEmpty()){
                throw new ServiceException("轮转规则开启了专业，请先配置专业后再关联学员");
            }
            List<Long> ruleBaseIds = ruleBases.stream().map(CycleRuleBase::getBaseId).toList();
            //判断学员的所有专业是否都包含在规则的专业中
            List<Long> collect = userBaseId.stream().filter(ruleBaseIds::contains).toList();
            if (collect.isEmpty()||collect.size()!=userBaseId.size()){
                throw new ServiceException("学员专业与规则专业不符，请重新选择");
            }
        }
        //校验学员是否已经存在
        List<CycleStudent> cycleStudents = studentMapper.selectList(Wrappers.<CycleStudent>lambdaQuery().ne(CycleStudent::getRuleId, ruleId).in(CycleStudent::getUserId, userIds));
        if (!cycleStudents.isEmpty()){
            throw new ServiceException("学员已存在于其他轮转规则中，请重新选择");
        }

    }

}
