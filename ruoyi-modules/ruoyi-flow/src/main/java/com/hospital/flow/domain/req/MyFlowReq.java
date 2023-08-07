package com.hospital.flow.domain.req;

import lombok.Data;

/**
 * @author lgx
 */
@Data
public class MyFlowReq {

    /**
     * 流程名称
     */
    private String flowName;

    /**
     * 申请开始时间
     */
    private String applyStartDate;

    /**
     * 申请结束时间
     */
    private String applyEndTime;

    /**
     * 审核状态
     */
    private String result;
}
