package com.hospital.attendance.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.hospital.attendance.domain.AttendanceGroupClasses;

import java.io.Serial;
import java.io.Serializable;

/**
 * 排班班次表 视图对象
 *
 * @author liguoxian
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AttendanceGroupClasses.class)
public class AttendanceGroupClassesVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 考勤组id
     */
    @ExcelProperty(value = "考勤组id")
    private Long groupId;

    /**
     * 班次id
     */
    @ExcelProperty(value = "班次id")
    private Long classesId;

    /**
     * 周次(1-7)
     */
    @ExcelProperty(value = "周次(1-7)")
    private Integer weekly;

    /**
     * 是否需要打卡
     */
    @ExcelProperty(value = "是否需要打卡")
    private String status;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}

