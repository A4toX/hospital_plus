package com.hospital.cycle.utils;

import com.hospital.cycle.domain.CycleGroup;
import com.hospital.cycle.domain.CycleGroupDept;
import com.hospital.cycle.domain.CycleRule;
import com.hospital.cycle.domain.CycleStudent;
import com.hospital.cycle.mapper.CycleGroupDeptMapper;
import com.hospital.cycle.mapper.CycleGroupMapper;
import com.hospital.cycle.mapper.CycleRuleMapper;
import com.hospital.cycle.mapper.CycleStudentMapper;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.redis.utils.RedisUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.hospital.cycle.constant.CycleRedisConstant.CYCLE_GROUP_PREFIX;
import static com.hospital.cycle.constant.CycleRedisConstant.CYCLE_RULE_PREFIX;

public class CycleCacheUtils {
    public static CycleRuleMapper ruleMapper = SpringUtils.getBean(CycleRuleMapper.class);
    public static CycleGroupMapper groupMapper = SpringUtils.getBean(CycleGroupMapper.class);
    public static CycleGroupDeptMapper groupDeptMapper = SpringUtils.getBean(CycleGroupDeptMapper.class);
    public static CycleStudentMapper studentMapper = SpringUtils.getBean(CycleStudentMapper.class);

    /**
     * 插入或更新规则缓存
     *
     * @param ruleId 轮转规则ID
     */
    public static void setRule(Long ruleId) {
        CycleRule rule = ruleMapper.selectById(ruleId);
        String key = CYCLE_RULE_PREFIX + ruleId;
        RedisUtils.setCacheObject(key, rule);
    }

    /**
     * 插入或更新规则缓存
     *
     * @param rule 轮转规则
     */
    public static void setRule(CycleRule rule) {
        String key = CYCLE_RULE_PREFIX + rule.getRuleId();
        RedisUtils.setCacheObject(key, rule);
    }

    /**
     * 删除规则
     *
     * @param ruleId 轮转规则id
     */
    public static void delRule(Long ruleId) {
        String key = CYCLE_RULE_PREFIX + ruleId;
        RedisUtils.deleteObject(key);
    }
    public static void delRule(Collection<Long> ruleIds) {
        ruleIds.forEach(CycleCacheUtils::delRule);
    }



    /**
     * 插入或更新规则缓存
     *
     * @param group 轮转规则组
     */
    public static void setGroup(CycleGroup group) {
        String key = CYCLE_GROUP_PREFIX + group.getRuleId();
        if (!RedisUtils.isExistsObject(key)) {
            List<CycleGroup> groupList = new ArrayList<>();
            RedisUtils.setCacheList(key, groupList);
        } else {
            List<CycleGroup> groups = RedisUtils.getCacheList(key);
            //groupList中是否存在该group
            groups = groups.stream()
                .filter(cacheGroup -> !Objects.equals(cacheGroup.getGroupId(), group.getRuleId()))
                .collect(Collectors.toList());
            groups.add(group);
            //删除并重新缓存
            RedisUtils.deleteObject(key);
            RedisUtils.setCacheList(key, groups);
        }
    }

    /**
     * 删除规则组
     * @param groupIds
     */
    public static void delGroup(Collection<Long> groupIds) {
        CycleGroup group = groupMapper.selectById(groupIds.iterator().next());
        String key = CYCLE_GROUP_PREFIX + group.getRuleId();
        if (RedisUtils.isExistsObject(key)) {
            List<CycleGroup> groups = RedisUtils.getCacheList(key);
            //groupList中是否存在该group
            groups = groups.stream()
                .filter(cacheGroup -> !groupIds.contains(cacheGroup.getGroupId()))
                .collect(Collectors.toList());
            //删除并重新缓存
            RedisUtils.deleteObject(key);
            if (!groups.isEmpty()){
                RedisUtils.setCacheList(key, groups);
            }
        }
    }

    /**
     * 新增科室缓存
     * @param groupDepts 科室列表
     */
    public static void setGroupDept(List<CycleGroupDept> groupDepts) {
        String key = CYCLE_GROUP_PREFIX + groupDepts.get(0).getRuleId()+":"+groupDepts.get(0).getGroupId();
        if (RedisUtils.isExistsObject(key)) {
            Collection<Long> deptIds = groupDepts.stream().map(CycleGroupDept::getDeptId).toList();
            delGroupDept(deptIds);
            RedisUtils.setCacheList(key, groupDepts);
            }
        }

    /**
     * 删除科室组
     * @param deptIds
     */
    public static void delGroupDept(Collection<Long> deptIds) {
        CycleGroupDept groupDept = groupDeptMapper.selectById(deptIds.iterator().next());
        String key = CYCLE_GROUP_PREFIX + groupDept.getRuleId()+":"+groupDept.getGroupId();
        if (RedisUtils.isExistsObject(key)) {
            List<CycleGroupDept> groupDepts = RedisUtils.getCacheList(key);

            groupDepts = groupDepts.stream()
                .filter(cacheGroupDept -> !deptIds.contains(cacheGroupDept.getDeptId()))
                .collect(Collectors.toList());
            //删除并重新缓存
            RedisUtils.deleteObject(key);
            if (!groupDepts.isEmpty()){
                RedisUtils.setCacheList(key, groupDepts);
            }
        }
    }

    /**
     * 新增或修改人员缓存
     * @param student 人员列表
     */
    public static void setStudent(List<CycleStudent> student){
        String key = CYCLE_GROUP_PREFIX + student.get(0).getRuleId();
        if (RedisUtils.isExistsObject(key)) {
            Collection<Long> studentIds = student.stream().map(CycleStudent::getUserId).toList();
            delStudent(studentIds);
            RedisUtils.setCacheList(key, student);
        }
        RedisUtils.setCacheList(key, student);
    }

    private static void delStudent(Collection<Long> cycleStudentIds) {
        CycleStudent cycleStudent = studentMapper.selectById(cycleStudentIds.iterator().next());
        String key = CYCLE_GROUP_PREFIX + cycleStudent.getRuleId();
        if (RedisUtils.isExistsObject(key)) {
            List<CycleStudent> students = RedisUtils.getCacheList(key);
            students = students.stream()
                .filter(cacheStudent -> !cycleStudentIds.contains(cacheStudent.getUserId()))
                .collect(Collectors.toList());
            //删除并重新缓存
            RedisUtils.deleteObject(key);
            if (!students.isEmpty()){
                RedisUtils.setCacheList(key, students);
            }
        }
    }


}





