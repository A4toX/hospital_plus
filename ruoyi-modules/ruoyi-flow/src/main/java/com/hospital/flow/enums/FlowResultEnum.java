package com.hospital.flow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 流程审核结果
 * @author lgx
 */
@Getter
@AllArgsConstructor
public enum FlowResultEnum {

    PROCESS("1", "待处理"),
    APPROVE("2", "通过"),
    REJECT("3", "不通过");

    private final String result;

    private final String desc;
}
