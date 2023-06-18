package com.hospital.attendance.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.hospital.attendance.domain.AttendanceFlow;

import java.io.Serial;
import java.io.Serializable;

/**
 * 考勤记录表 视图对象
 *
 * @author liguoxian
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AttendanceFlow.class)
public class AttendanceFlowVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "主键")
    private Long id;

    @ExcelProperty(value = "医院id")
    private Long hosId;

    @ExcelProperty(value = "用户id")
    private Long userId;

    @ExcelProperty(value = "考勤组id")
    private Long attendGroupId;

    @ExcelProperty(value = "考勤组班次id")
    private Long attendClassesId;

    @ExcelProperty(value = "签到类型")
    private Integer attendType;

    @ExcelProperty(value = "签到状态")
    private Integer attendStatus;

    @ExcelProperty(value = "签到日期")
    private String attendDate;

    @ExcelProperty(value = "签到时间")
    private String attendTime;

    @ExcelProperty(value = "签到经度")
    private Double attendLongitude;

    @ExcelProperty(value = "签到纬度")
    private Double attendLatitude;

    @ExcelProperty(value = "考勤地址名称")
    private String attendAreaName;

    @ExcelProperty(value = "考勤次数")
    private Integer attendNumber;

    @ExcelProperty(value = "签到种类 1上班 2下班")
    private String attendKind;

    @ExcelProperty(value = "请假流水id")
    private Integer leaveId;

    @ExcelProperty(value = "是否外勤打卡")
    private Integer areaOutside;

    @ExcelProperty(value = "是否系统自动处理")
    private String automaticFlag;

    @ExcelProperty(value = "是否需要打卡")
    private String needAttendFlag;

    @ExcelProperty(value = "迟到早退分钟数")
    private Integer errMinutes;

    @ExcelProperty(value = "删除标志（0代表存在 2代表删除）")
    private String delDelete;

    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 考勤组信息
     */
    private AttendanceGroupVo attendanceGroup;
    /**
     * 考勤班次
     */
    private AttendanceClassesVo attendanceClasses;
}

