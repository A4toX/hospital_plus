package com.hospital.attendance.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.hospital.attendance.domain.AttendanceFillRecord;

import java.io.Serial;
import java.io.Serializable;

/**
 * 补卡记录 视图对象
 *
 * @author liguoxian
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AttendanceFillRecord.class)
public class AttendanceFillRecordVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 对应的考勤记录id
     */
    @ExcelProperty(value = "对应的考勤记录id")
    private Long flowId;

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 补卡日期
     */
    @ExcelProperty(value = "补卡日期")
    private String fillDate;

    /**
     * 补卡时间
     */
    @ExcelProperty(value = "补卡时间")
    private String fillTime;

    /**
     * 原打卡时间
     */
    @ExcelProperty(value = "原打卡时间")
    private String originalTime;

    /**
     * 补卡原因
     */
    @ExcelProperty(value = "补卡原因")
    private String fillReason;

    /**
     * 补卡图片
     */
    @ExcelProperty(value = "补卡图片")
    private String fillImg;

    /**
     * 审核结果
     */
    @ExcelProperty(value = "审核结果")
    private String result;

    /**
     * 流程实例编号
     */
    @ExcelProperty(value = "流程实例编号")
    private String processInstanceId;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}

