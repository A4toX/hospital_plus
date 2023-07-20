package com.hospital.flow.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.hospital.flow.domain.FlowTask;

/**
 * 审核任务 参数对象
 *
 * @author liguoxian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FlowTask.class, reverseConvertGenerate = false)
public class FlowTaskBo extends BaseEntity {

    /**
     * 主键
     */
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
     * 审核时间
     */
    private String auditTime;

    /**
     * 备注
     */
    private String remark;

}

