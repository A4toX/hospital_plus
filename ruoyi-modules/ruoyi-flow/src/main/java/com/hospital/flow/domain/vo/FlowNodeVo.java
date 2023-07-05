package com.hospital.flow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.hospital.flow.domain.FlowNode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 流程节点 视图对象
 *
 * @author liguoxian
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlowNode.class)
public class FlowNodeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 类型
     */
    @ExcelProperty(value = "类型")
    private String type;

    /**
     * 配置ID
     */
    @ExcelProperty(value = "配置ID")
    private Long configId;

    /**
     * 多实例类型（0无，1 会签，2或签）
     */
    @ExcelProperty(value = "多实例类型（0无，1 会签，2或签）")
    private String multiType;

    /**
     * 审核人类型
     */
    @ExcelProperty(value = "审核人类型")
    private String assigneeType;

    /**
     * 审核人选项
     */
    @ExcelProperty(value = "审核人选项")
    private String assigneeValue;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}

