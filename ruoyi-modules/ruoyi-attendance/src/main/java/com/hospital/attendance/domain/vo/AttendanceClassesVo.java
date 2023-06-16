package com.hospital.attendance.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.hospital.attendance.domain.AttendanceClasses;

import java.io.Serial;
import java.io.Serializable;

/**
 * 班次设置表 视图对象
 *
 * @author liguoxian
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AttendanceClasses.class)
public class AttendanceClassesVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "主键")
    private Long id;

    @ExcelProperty(value = "医院id")
    private Long hosId;

    @ExcelProperty(value = "班次名称")
    private String name;

    @ExcelProperty(value = "上班开始打卡时间")
    private String workTime;

    @ExcelProperty(value = "下班开始打卡时间")
    private String afterTime;

    @ExcelProperty(value = "晚多少分钟为迟到")
    private Integer workLateMin;

    @ExcelProperty(value = "是否开启严重迟到(1是2否)")
    private Integer isSeriousLate;

    @ExcelProperty(value = "晚多少分钟为严重迟到")
    private Integer workSeriousLateMin;

    @ExcelProperty(value = "晚多少分钟为上班缺卡")
    private Integer workAbsMin;

    @ExcelProperty(value = "是否开启下班自动打卡(1是2否)")
    private Integer isAutoAfter;

    @ExcelProperty(value = "早多少分钟为下班缺卡")
    private Integer afterAbsMin;

    @ExcelProperty(value = "早多少分钟为早退")
    private Integer afterLeaveEarly;

    @ExcelProperty(value = "删除标志（0代表存在 2代表删除）")
    private String delDelete;

    @ExcelProperty(value = "备注")
    private String remark;

}

