package com.hospital.cycle.utils;

import com.hospital.cycle.domain.*;
import com.hospital.cycle.mapper.CycleGroupDeptMapper;
import com.hospital.cycle.mapper.CycleGroupMapper;
import com.hospital.cycle.mapper.CycleRuleMapper;
import com.hospital.cycle.mapper.CycleStudentMapper;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.redis.utils.RedisUtils;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.hospital.cycle.constant.CycleRedisConstant.*;

public class CycleCacheUtils {
    public static CycleRuleMapper ruleMapper = SpringUtils.getBean(CycleRuleMapper.class);
    public static CycleGroupMapper groupMapper = SpringUtils.getBean(CycleGroupMapper.class);
    public static CycleGroupDeptMapper groupDeptMapper = SpringUtils.getBean(CycleGroupDeptMapper.class);
    public static CycleStudentMapper studentMapper = SpringUtils.getBean(CycleStudentMapper.class);




    /**
     * 新增科室缓存
     * @param groupDepts 科室列表
     */
    public static void setGroupDept(List<CycleGroupDept> groupDepts) {
        String key = CYCLE_GROUP_DEPT_PREFIX+groupDepts.get(0).getGroupId();
        if (RedisUtils.isExistsObject(key)) {
            Collection<Long> deptIds = groupDepts.stream().map(CycleGroupDept::getDeptId).toList();
            delGroupDept(deptIds);
            }
        RedisUtils.setCacheList(key, groupDepts);
    }

    /**
     * 删除科室组
     * @param deptIds
     */
    public static void delGroupDept(Collection<Long> deptIds) {
        CycleGroupDept groupDept = groupDeptMapper.selectById(deptIds.iterator().next());
        String key = CYCLE_GROUP_DEPT_PREFIX+groupDept.getGroupId();
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
        String key = CYCLE_STUDENT_PREFIX + student.get(0).getRuleId();
        if (RedisUtils.isExistsObject(key)) {
            Collection<Long> studentIds = student.stream().map(CycleStudent::getUserId).toList();
            delStudent(studentIds);
            RedisUtils.setCacheList(key, student);
        }
        RedisUtils.setCacheList(key, student);
    }

    public static void delStudent(Collection<Long> cycleStudentIds) {
        CycleStudent cycleStudent = studentMapper.selectById(cycleStudentIds.iterator().next());
        String key = CYCLE_STUDENT_PREFIX + cycleStudent.getRuleId();
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





