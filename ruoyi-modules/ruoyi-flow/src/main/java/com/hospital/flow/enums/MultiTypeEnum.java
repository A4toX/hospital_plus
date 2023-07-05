package com.hospital.flow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lgx
 */
@Getter
@AllArgsConstructor
public enum MultiTypeEnum {

    NO("0", "无"),
    AND_SIGN("1", "会签"),
    OR_SIGN("2", "或签");

    private final String type;

    private final String desc;
}
