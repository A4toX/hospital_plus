package com.hospital.flow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.hospital.flow.domain.FlowVariable;

import java.io.Serial;
import java.io.Serializable;

/**
 * 流程当前参数 视图对象
 *
 * @author liguoxian
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlowVariable.class)
public class FlowVariableVo implements Serializable {

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
     * 参数类型
     */
    @ExcelProperty(value = "参数类型")
    private String paramType;

    /**
     * 参数名称
     */
    @ExcelProperty(value = "参数名称")
    private String paramName;

    /**
     * 参数值
     */
    @ExcelProperty(value = "参数值")
    private String paramValue;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}

