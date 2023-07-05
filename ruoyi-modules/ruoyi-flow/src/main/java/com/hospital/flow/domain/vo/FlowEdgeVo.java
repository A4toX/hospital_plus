package com.hospital.flow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.hospital.flow.domain.FlowEdge;

import java.io.Serial;
import java.io.Serializable;

/**
 * 流程边线 视图对象
 *
 * @author liguoxian
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlowEdge.class)
public class FlowEdgeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 配置ID
     */
    @ExcelProperty(value = "配置ID")
    private Long configId;

    /**
     * 节点类型（1默认流转，2条件流转）
     */
    @ExcelProperty(value = "节点类型（1默认流转，2条件流转）")
    private String type;

    /**
     * 源节点ID
     */
    @ExcelProperty(value = "源节点ID")
    private Long sourceNodeId;

    /**
     * 目标节点ID
     */
    @ExcelProperty(value = "目标节点ID")
    private Long targetNodeId;

    /**
     * 条件表达式
     */
    @ExcelProperty(value = "条件表达式")
    private String conditionExpre;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}

