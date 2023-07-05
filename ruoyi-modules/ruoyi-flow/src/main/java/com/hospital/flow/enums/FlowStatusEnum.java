package com.hospital.flow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 流程申请状态
 * @author lgx
 */
@Getter
@AllArgsConstructor
public enum FlowStatusEnum {

    RUNNING("1", "进行中"),
    FINISH("2", "完成"),
    END("3", "终止"),
    CANCEL("4", "撤销");

    private final String status;
    private final String desc;
}
