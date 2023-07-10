package com.hospital.flow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lgx
 */
@Getter
@AllArgsConstructor
public enum FlowKeyEnum {

    LEAVE("leave", "请假流程"),
    FILL("fill", "补卡流程");

    private final String key;

    private final String desc;
}
