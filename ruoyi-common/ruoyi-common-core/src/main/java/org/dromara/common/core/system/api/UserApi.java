package org.dromara.common.core.system.api;

import org.dromara.common.core.system.domain.User;

/**
 * @author lgx
 */
public interface UserApi {

    User getUser(Long userId);
}
