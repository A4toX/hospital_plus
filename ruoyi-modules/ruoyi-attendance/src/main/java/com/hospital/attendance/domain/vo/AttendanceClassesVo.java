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

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 班次名称
     */
    @ExcelProperty(value = "班次名称")
    private String name;

    /**
     * 上班开始打卡时间
     */
    @ExcelProperty(value = "上班开始打卡时间")
    private String workTime;

    /**
     * 下班开始打卡时间
     */
    @ExcelProperty(value = "下班开始打卡时间")
    private String afterTime;

    /**
     * 晚多少分钟为迟到
     */
    @ExcelProperty(value = "晚多少分钟为迟到")
    private Integer workLateMin;

    /**
     * 是否开启严重迟到
     */
    @ExcelProperty(value = "是否开启严重迟到")
    private String isSeriousLate;

    /**
     * 晚多少分钟为严重迟到
     */
    @ExcelProperty(value = "晚多少分钟为严重迟到")
    private Integer workSeriousLateMin;

    /**
     * 晚多少分钟为上班缺卡
     */
    @ExcelProperty(value = "晚多少分钟为上班缺卡")
    private Integer workAbsMin;

    /**
     * 是否开启下班自动打卡
     */
    @ExcelProperty(value = "是否开启下班自动打卡")
    private String isAutoAfter;

    /**
     * 早多少分钟为下班缺卡
     */
    @ExcelProperty(value = "早多少分钟为下班缺卡")
    private Integer afterAbsMin;

    /**
     * 早多少分钟为早退
     */
    @ExcelProperty(value = "早多少分钟为早退")
    private Integer afterLeaveEarly;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}

