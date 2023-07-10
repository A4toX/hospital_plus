package com.hospital.flow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lgx
 */
@Getter
@AllArgsConstructor
public enum AssigneeTypeEnum {

    CURRENT_USER("current_user", "当前登录人"),
    ROLE("role", "角色");

    private final String type;

    private final String desc;
}
