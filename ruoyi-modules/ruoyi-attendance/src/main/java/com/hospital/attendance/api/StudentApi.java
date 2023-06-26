package com.hospital.attendance.api;

import com.hospital.attendance.api.dto.StudentDto;

import java.util.List;
import java.util.Set;

/**
 * @author lgx
 */
public interface StudentApi {

    default List<StudentDto> selectStudent(StudentDto dto, List<Long> userIds) {
        return null;
    }
}
