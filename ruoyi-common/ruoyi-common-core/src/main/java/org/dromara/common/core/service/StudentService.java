package org.dromara.common.core.service;

import org.dromara.common.core.service.domain.Student;

import java.util.List;
import java.util.Set;

public interface StudentService {
    Long selectStudentBaseIdByUserId(Long userId);

    List<Student> selectStudentByUserIds(Set<Long> userIds);

}
