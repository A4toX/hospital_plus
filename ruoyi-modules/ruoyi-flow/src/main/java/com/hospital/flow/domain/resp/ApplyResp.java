package com.hospital.flow.domain.resp;

/**
 * @author lgx
 */
public class ApplyResp {

    /**
     * 申请ID
     */
    private Long applyId;

    /**
     * 审核ID
     */
    private Long taskId;

    /**
     * 流程名称
     */
    private String flowName;

    /**
     * 审核节点名称
     */
    private String taskName;

    /**
     * 申请人ID
     */
    private Long applyUserId;

    /**
     * 申请人名称
     */
    private String applyUserName;

    /**
     * 申请时间
     */
    private String applyTime;

    /**
     * 申请结果
     */
    private String result;
}
