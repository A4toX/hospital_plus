package com.hospital.attendance.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.hospital.attendance.domain.AttendanceGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 考勤组信息表 视图对象
 *
 * @author liguoxian
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AttendanceGroup.class)
public class AttendanceGroupVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 考勤组名称
     */
    @ExcelProperty(value = "考勤组名称")
    private String groupName;

    /**
     * 考勤类型 1固定班次 2自由打卡
     */
    @ExcelProperty(value = "考勤类型 1固定班次 2自由打卡")
    private Integer groupType;

    /**
     * 考勤方式 1位置 2签到考勤
     */
    @ExcelProperty(value = "考勤方式 1位置 2签到考勤")
    private Integer groupMethod;

    /**
     * 是否允许外勤打卡
     */
    @ExcelProperty(value = "是否允许外勤打卡")
    private String areaOutside;

    /**
     * 是否使用动态二维码
     */
    @ExcelProperty(value = "是否使用动态二维码")
    private String groupCode;

    /**
     * 二维码刷新时间
     */
    @ExcelProperty(value = "二维码刷新时间")
    private Integer codeFreshTime;

    /**
     * 法定节日是否自动排休
     */
    @ExcelProperty(value = "法定节日是否自动排休")
    private String holidayLeave;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;
}

