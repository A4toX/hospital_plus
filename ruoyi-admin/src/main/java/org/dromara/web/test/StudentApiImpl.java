package org.dromara.web.test;

import com.hospital.attendance.api.StudentApi;
import com.hospital.attendance.api.dto.StudentDto;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;
import org.dromara.system.domain.SysUser;
import org.dromara.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 学员
 * @author lgx
 */
@Service
public class StudentApiImpl implements StudentApi {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public List<StudentDto> selectStudent(StudentDto dto, List<Long> userIds) {
        List<SysUser> sysUsers = userMapper.selectList(new LambdaQueryWrapperX<SysUser>()
            .inIfPresent(SysUser::getUserId, userIds)
            .likeIfPresent(SysUser::getRealName, dto.getUserName())
            .likeIfPresent(SysUser::getPhonenumber, dto.getPhone())
            .eqIfPresent(SysUser::getSex, dto.getGender()));
        return sysUsers.stream()
            .map(user -> new StudentDto()
                .setUserId(user.getUserId())
                .setUserName(user.getRealName())
                .setPhone(user.getPhonenumber())
                .setGender(user.getSex()))
            .collect(Collectors.toList());

    }
}
