package com.hospital.flow.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dromara.common.tenant.core.TenantEntity;

/**
 * 审核任务
 *
 * @author liguoxian
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("flow_task")
public class FlowTask extends TenantEntity {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 申请ID
     */
    private Long applyId;

    /**
     * 审核节点ID
     */
    private Long nodeId;

    /**
     * 审核用户ID
     */
    private Long assigneeUserId;

    /**
     * 审核结果（1待处理，2同意，3驳回）
     */
    private String result;

    /**
     * 审核意见
     */
    private String comment;

    /**
     * 是否为当前任务
     */
    private String currentFlag;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 备注
     */
    private String remark;

}

