package org.dromara.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否枚举
 */
@Getter
@AllArgsConstructor
public enum YesNoEnum {

    YES("Y", "是"),
    NO("N", "否");

    private final String value;

    private final String name;

    public static String getValueByBool(boolean value) {
        return value ? YES.getValue() : NO.getValue();
    }
}
