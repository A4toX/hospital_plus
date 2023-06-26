package com.hospital.attendance.api;

import com.hospital.attendance.api.dto.TeacherDto;

import java.util.List;

/**
 * @author lgx
 */
public interface TeacherApi {
    default List<TeacherDto> selectTeacher(TeacherDto dto, List<Long> userIds) {
        return null;
    }
}
