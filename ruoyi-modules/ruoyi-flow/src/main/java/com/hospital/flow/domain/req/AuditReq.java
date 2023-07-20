package com.hospital.flow.domain.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author lgx
 */
@Data
public class AuditReq {

    /**
     * 审核任务ID集合
     */
    @NotNull(message = "审核任务ID不能为空")
    private String taskIds;

    /**
     * 审核意见
     */
    private String comment;
}
