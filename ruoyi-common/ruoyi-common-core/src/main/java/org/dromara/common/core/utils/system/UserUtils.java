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

    /**
     * 获取用户
     * @param userId 用户id
     * @return 用户
     */
    public static User getUser(Long userId) {
        return userService.getUserById(userId);
    }

    /**
     * 获取用户名
     * @param userId 用户id
     * @return 用户名
     */
    public static String getUserName(Long userId) {
        return userService.selectUserNameById(userId);
    }

    /**
     * 获取真实姓名
     * @param userId 用户id
     * @return 真实姓名
     */
    public static String getRealName(Long userId) {
        return userService.selectRealNameById(userId);
    }
}
