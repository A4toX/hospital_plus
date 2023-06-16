package org.dromara.common.core.utils.system;

import org.dromara.common.core.system.api.UserApi;
import org.dromara.common.core.system.domain.User;
import org.dromara.common.core.utils.SpringUtils;

/**
 * 系统用户工具类
 * @author lgx
 */
public class UserUtils {

    private static UserApi userApi = SpringUtils.getBean(UserApi.class);

    public static User getUser(Long userId) {
        return userApi.getUser(userId);
    }

    public static String getUserName(Long userId) {
        User user = getUser(userId);
        return user != null ? user.getUserName() : "";
    }
}
