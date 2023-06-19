package org.dromara.web.test;

import com.hospital.attendance.api.StudentApi;
import com.hospital.attendance.api.TeacherApi;
import com.hospital.attendance.api.dto.StudentDto;
import com.hospital.attendance.api.dto.TeacherDto;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;
import org.dromara.system.domain.SysUser;
import org.dromara.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lgx
 */
@Service
public class TeacherApiImpl implements TeacherApi {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public List<TeacherDto> selectTeacher(TeacherDto dto, List<Long> userIds) {
        return TeacherApi.super.selectTeacher(dto, userIds);
    }
}
