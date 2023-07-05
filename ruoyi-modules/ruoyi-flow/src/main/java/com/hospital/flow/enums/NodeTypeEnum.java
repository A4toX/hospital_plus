package com.hospital.flow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lgx
 */
@Getter
@AllArgsConstructor
public enum NodeTypeEnum {

    START("start", "开始节点"),
    END("end", "结束节点"),
    GATEWAY("gateway", "网关"),
    TASK("task", "任务节点");

    private final String type;

    private final String desc;
}
