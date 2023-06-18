package com.hospital.attendance.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hospital.attendance.domain.AttendanceGroupClasses;
import com.hospital.attendance.domain.vo.AttendanceGroupClassVo;
import com.hospital.attendance.domain.vo.AttendanceGroupClassesSimpleRespVo;
import com.hospital.attendance.domain.vo.AttendanceGroupClassesVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 排班班次表(AttendanceGroupClasses) DAO
 *
 * @author makejava
 * @since 2023-05-21 17:56:38
 */
public interface AttendanceGroupClassesMapper extends BaseMapperPlus<AttendanceGroupClasses, AttendanceGroupClassesVo> {

    List<AttendanceGroupClassVo> selectByGroupId(Long id);

    default List<AttendanceGroupClasses> exsitGroupByWeek(Long groupId, Integer weekly){
        return selectList(new LambdaQueryWrapper<AttendanceGroupClasses>()
            .eq(AttendanceGroupClasses::getGroupId, groupId)
            .eq(AttendanceGroupClasses::getWeekly, weekly));
    }

    default List<AttendanceGroupClasses> getGroupIds(Long classesId, Integer weekly) {
        return selectList(new LambdaQueryWrapper<AttendanceGroupClasses>()
            .eq(AttendanceGroupClasses::getClassesId, classesId)
            .eq(AttendanceGroupClasses::getWeekly, weekly));
    }

    default List<AttendanceGroupClasses> selectByClassesId(Long classesId) {
        return selectList(new LambdaQueryWrapper<AttendanceGroupClasses>()
           .eq(AttendanceGroupClasses::getClassesId, classesId));
    }

    List<AttendanceGroupClassesSimpleRespVo> selectByGroupIdSimple(Long id);
}

