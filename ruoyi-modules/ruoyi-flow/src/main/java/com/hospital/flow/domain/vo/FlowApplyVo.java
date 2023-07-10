package com.hospital.flow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.hospital.flow.domain.FlowApply;

import java.io.Serial;
import java.io.Serializable;

/**
 * 流程申请 视图对象
 *
 * @author liguoxian
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlowApply.class)
public class FlowApplyVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 流程ID
     */
    @ExcelProperty(value = "流程ID")
    private Long configId;

    /**
     * 流程关键字
     */
    @ExcelProperty(value = "流程关键字")
    private String flowKey;

    /**
     * 审核状态(1进行中，2完成，3终止，4撤销)
     */
    @ExcelProperty(value = "审核状态(1进行中，2完成，3终止，4撤销)")
    private String status;

    /**
     * 关联业务ID
     */
    @ExcelProperty(value = "关联业务ID")
    private Long businessId;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}

