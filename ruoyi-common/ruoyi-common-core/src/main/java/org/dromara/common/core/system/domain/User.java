package org.dromara.common.core.system.domain;

import lombok.Data;

/**
 * @author lgx
 */
@Data
public class User {

    private Long userId;

    private String userName;

    private String phone;

    private Long deptId;
}
