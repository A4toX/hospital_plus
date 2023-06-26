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

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 考勤组id
     */
    @ExcelProperty(value = "考勤组id")
    private Long attendGroupId;

    /**
     * 考勤组班次id
     */
    @ExcelProperty(value = "考勤组班次id")
    private Long attendClassesId;

    /**
     * 签到方式
     */
    @ExcelProperty(value = "签到方式")
    private Integer attendMethod;

    /**
     * 签到状态
     */
    @ExcelProperty(value = "签到状态")
    private Integer attendStatus;

    /**
     * 签到日期
     */
    @ExcelProperty(value = "签到日期")
    private String attendDate;

    /**
     * 签到时间
     */
    @ExcelProperty(value = "签到时间")
    private String attendTime;

    /**
     * 签到经度
     */
    @ExcelProperty(value = "签到经度")
    private Double attendLongitude;

    /**
     * 签到纬度
     */
    @ExcelProperty(value = "签到纬度")
    private Double attendLatitude;

    /**
     * 考勤地址名称
     */
    @ExcelProperty(value = "考勤地址名称")
    private String attendAreaName;

    /**
     * 考勤次数
     */
    @ExcelProperty(value = "考勤次数")
    private Integer attendNumber;

    /**
     * 签到种类 1上班 2下班
     */
    @ExcelProperty(value = "签到种类 1上班 2下班")
    private String attendKind;

    /**
     * 请假流水id
     */
    @ExcelProperty(value = "请假流水id")
    private Integer leaveId;

    /**
     * 是否外勤打卡
     */
    @ExcelProperty(value = "是否外勤打卡")
    private String areaOutside;

    /**
     * 是否系统自动处理
     */
    @ExcelProperty(value = "是否系统自动处理")
    private String automaticFlag;

    /**
     * 是否需要打卡
     */
    @ExcelProperty(value = "是否需要打卡")
    private String needAttendFlag;

    /**
     * 迟到早退分钟数
     */
    @ExcelProperty(value = "迟到早退分钟数")
    private Integer errMinutes;

    /**
     * 考勤组信息
     */
//    private AttendanceGroupVo attendanceGroup;
//    /**
//     * 考勤班次
//     */
//    private AttendanceClassesVo attendanceClasses;
}

