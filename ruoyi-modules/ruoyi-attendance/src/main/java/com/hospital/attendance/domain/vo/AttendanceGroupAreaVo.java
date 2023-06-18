package com.hospital.attendance.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.hospital.attendance.domain.AttendanceGroupArea;

import java.io.Serial;
import java.io.Serializable;

/**
 * 考勤地点表 视图对象
 *
 * @author liguoxian
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AttendanceGroupArea.class)
public class AttendanceGroupAreaVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 医院id
     */
    @ExcelProperty(value = "医院id")
    private Long hosId;

    /**
     * 考勤组id
     */
    @ExcelProperty(value = "考勤组id")
    private Long groupId;

    /**
     * 地点名称
     */
    @ExcelProperty(value = "地点名称")
    private String areaName;

    /**
     * 经度
     */
    @ExcelProperty(value = "经度")
    private Double longitude;

    /**
     * 纬度
     */
    @ExcelProperty(value = "纬度")
    private Double latitude;

    /**
     * 考勤范围
     */
    @ExcelProperty(value = "考勤范围")
    private Double attendRange;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}

