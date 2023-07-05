package com.hospital.flow.domain.req;

import lombok.Data;

/**
 * @author lgx
 */
@Data
public class AuditReq {

    /**
     * 审核任务ID集合
     */
    private String taskIds;

    /**
     * 审核意见
     */
    private String comment;
}
