package org.dromara.common.core.service.domain;

import lombok.Data;

/**
 * @author lgx
 */
@Data
public class User {

    private Long userId;

    private String realName;

    private String phone;

    private Long deptId;
}
