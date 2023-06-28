package com.hospital.attendance.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.hospital.attendance.domain.AttendanceLeaveRecord;

import java.io.Serial;
import java.io.Serializable;

/**
 * 请假记录 视图对象
 *
 * @author liguoxian
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AttendanceLeaveRecord.class)
public class AttendanceLeaveRecordVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 请假配置id
     */
    @ExcelProperty(value = "请假配置id")
    private Long leaveConfigId;

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 请假开始日期
     */
    @ExcelProperty(value = "请假开始日期")
    private String leaveStartDate;

    /**
     * 请假结束日期
     */
    @ExcelProperty(value = "请假结束日期")
    private String leaveEndDate;

    /**
     * 请假开始时间
     */
    @ExcelProperty(value = "请假开始时间")
    private String leaveStartTime;

    /**
     * 请假结束时间
     */
    @ExcelProperty(value = "请假结束时间")
    private String leaveEndTime;

    /**
     * 请假时长(日)
     */
    @ExcelProperty(value = "请假时长(日)")
    private Double leaveLength;

    /**
     * 请假事由
     */
    @ExcelProperty(value = "请假事由")
    private String leaveThings;

    /**
     * 附件集合,多个逗号隔开
     */
    @ExcelProperty(value = "附件集合,多个逗号隔开")
    private String files;

    /**
     * 流程实例编号
     */
    @ExcelProperty(value = "流程实例编号")
    private String processInstanceId;

    /**
     * 审核结果
     */
    @ExcelProperty(value = "审核结果")
    private String result;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}

