package com.hospital.cycle.utils;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hospital.cycle.domain.CycleGroup;
import com.hospital.cycle.domain.CycleGroupDept;
import com.hospital.cycle.domain.CycleRule;
import com.hospital.cycle.domain.CycleUserDept;
import com.hospital.cycle.mapper.*;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.SpringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.hospital.cycle.constant.CycleConstant.*;

public class CycleValidUtils {
    public static CycleRuleMapper ruleMapper = SpringUtils.getBean(CycleRuleMapper.class);
    public static CycleGroupMapper groupMapper = SpringUtils.getBean(CycleGroupMapper.class);
    public static CycleGroupDeptMapper groupDeptMapper = SpringUtils.getBean(CycleGroupDeptMapper.class);
    public static CycleStudentMapper cycleGroupMapper = SpringUtils.getBean(CycleStudentMapper.class);
    public static CycleRuleBaseMapper ruleBaseMapper = SpringUtils.getBean(CycleRuleBaseMapper.class);
    public static CycleUserDeptMapper userDeptMapper = SpringUtils.getBean(CycleUserDeptMapper.class);


    /**
     * 校验用户提交的选课信息是否符合规则
     * @param userDepts
     * @return
     */
    public  static void ValidstudentSelectDept(List<CycleUserDept> userDepts) {
        //使用steam判断groupUserDepts中的ruleId是否都相同，不相同返回异常
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

    public static void validGroupDept(){

    }




}
