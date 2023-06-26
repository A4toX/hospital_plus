package org.dromara.system.service.impl;

import lombok.RequiredArgsConstructor;
import org.dromara.common.core.system.api.UserApi;
import org.dromara.common.core.system.domain.User;
import org.dromara.system.domain.SysUser;
import org.dromara.system.domain.vo.SysUserVo;
import org.dromara.system.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

/**
 * @author lgx
 */
@RequiredArgsConstructor
@Service
public class UserApiImpl implements UserApi {

    private final SysUserMapper userMapper;

    @Override
    public User getUser(Long userId) {
        SysUser sysUser = userMapper.selectById(userId);
        User user = new User();
        user.setUserId(sysUser.getUserId());
        user.setUserName(sysUser.getRealName());
        user.setPhone(sysUser.getPhonenumber());
        user.setDeptId(sysUser.getDeptId());

        return user;
    }
}
