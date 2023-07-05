package com.hospital.attendance.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.hospital.attendance.domain.AttendanceLeaveConfig;

import java.io.Serial;
import java.io.Serializable;

/**
 * 请假配置 视图对象
 *
 * @author liguoxian
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AttendanceLeaveConfig.class)
public class AttendanceLeaveConfigVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 请假名称
     */
    @ExcelProperty(value = "请假名称")
    private String leaveName;

    /**
     * 时长单位 1天 2半天
     */
    @ExcelProperty(value = "时长单位 1天 2半天")
    private Integer leaveUnit;

    /**
     * 假期计算方式 1工作日 2自然日
     */
    @ExcelProperty(value = "假期计算方式 1工作日 2自然日")
    private Integer leaveTimeType;

    /**
     * 状态（0正常 1停用）
     */
    @ExcelProperty(value = "状态（0正常 1停用）")
    private String status;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}

