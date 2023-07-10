package com.hospital.flow.utils;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Sets;
import com.hospital.flow.enums.AssigneeTypeEnum;
import org.dromara.common.core.service.RoleService;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.satoken.utils.LoginHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author lgx
 */
public class FlowAssigneeUtils {

    public final static Map<String, AssigneeFunction> assigneeMap = new HashMap<>();

    static {
        assigneeMap.put(AssigneeTypeEnum.CURRENT_USER.getType(), FlowAssigneeUtils::current);
        assigneeMap.put(AssigneeTypeEnum.ROLE.getType(), FlowAssigneeUtils::role);
    }

    private static Set<Long> current(String value) {
        return Sets.newHashSet(LoginHelper.getUserId());
    }

    private static Set<Long> role(String value) {
        Set<Long> userIds = Sets.newHashSet();
        long[] roleIds = StrUtil.splitToLong(value, ",");
        RoleService roleService = SpringUtils.getBean(RoleService.class);
        for (long roleId : roleIds) {
            userIds.addAll(roleService.selectUsersByRoleId(roleId));
        }
        return userIds;
    }
}
