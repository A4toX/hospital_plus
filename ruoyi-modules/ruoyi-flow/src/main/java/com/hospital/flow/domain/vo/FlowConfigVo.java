package com.hospital.flow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.hospital.flow.domain.FlowConfig;

import java.io.Serial;
import java.io.Serializable;

/**
 * 流程配置 视图对象
 *
 * @author liguoxian
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlowConfig.class)
public class FlowConfigVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 流程名称
     */
    @ExcelProperty(value = "流程名称")
    private String flowName;

    /**
     * 关键字
     */
    @ExcelProperty(value = "关键字")
    private String flowKey;

    /**
     * 流程配置内容
     */
    @ExcelProperty(value = "流程配置内容")
    private String bpmnConfig;

    /**
     * 版本
     */
    @ExcelProperty(value = "版本")
    private Integer flowVersion;

    /**
     * 是否最新版本
     */
    @ExcelProperty(value = "是否最新版本")
    private String versionFlag;

    /**
     * 最新发布时间
     */
    @ExcelProperty(value = "最新发布时间")
    private String publishTime;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}

