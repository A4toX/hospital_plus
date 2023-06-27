package org.dromara.common.core.utils.system;

import org.dromara.common.core.service.UserService;
import org.dromara.common.core.service.domain.User;
import org.dromara.common.core.utils.SpringUtils;

/**
 * 系统用户工具类
 * @author lgx
 */
public class UserUtils {

    private static UserService userService = SpringUtils.getBean(UserService.class);

    public static User getUser(Long userId) {
        return userService.getUserById(userId);
    }

    public static String getUserName(Long userId) {
        return userService.selectUserNameById(userId);
    }

    public static String getRealName(Long userId) {
        return userService.selectRealNameById(userId);
    }
}
