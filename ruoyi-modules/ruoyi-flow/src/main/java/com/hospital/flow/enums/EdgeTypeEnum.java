package com.hospital.flow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lgx
 */
@Getter
@AllArgsConstructor
public enum EdgeTypeEnum {

    DEFAULT("1", "默认流转"),
    CONDITION("2", "条件流转");

    private final String type;

    private final String desc;
}
