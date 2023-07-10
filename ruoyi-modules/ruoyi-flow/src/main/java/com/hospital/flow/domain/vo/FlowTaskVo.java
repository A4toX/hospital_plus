package com.hospital.flow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.hospital.flow.domain.FlowTask;

import java.io.Serial;
import java.io.Serializable;

/**
 * 审核任务 视图对象
 *
 * @author liguoxian
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlowTask.class)
public class FlowTaskVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 申请ID
     */
    @ExcelProperty(value = "申请ID")
    private Long applyId;

    /**
     * 审核节点ID
     */
    @ExcelProperty(value = "审核节点ID")
    private Long nodeId;

    /**
     * 审核用户ID
     */
    @ExcelProperty(value = "审核用户ID")
    private Long assigneeUserId;

    /**
     * 审核结果（1待处理，2同意，3驳回）
     */
    @ExcelProperty(value = "审核结果（1待处理，2同意，3驳回）")
    private String result;

    /**
     * 审核意见
     */
    @ExcelProperty(value = "审核意见")
    private String comment;

    /**
     * 是否为当前任务
     */
    @ExcelProperty(value = "是否为当前任务")
    private String currentFlag;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}

