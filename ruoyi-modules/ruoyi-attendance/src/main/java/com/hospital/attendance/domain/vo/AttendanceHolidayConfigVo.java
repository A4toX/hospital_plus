package com.hospital.attendance.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.hospital.attendance.domain.AttendanceHolidayConfig;

import java.io.Serial;
import java.io.Serializable;

/**
 * 节假日配置 视图对象
 *
 * @author liguoxian
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AttendanceHolidayConfig.class)
public class AttendanceHolidayConfigVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 所属年份
     */
    @ExcelProperty(value = "所属年份")
    private String belongYear;

    /**
     * 节假日的开始时间
     */
    @ExcelProperty(value = "节假日的开始时间")
    private String beginDate;

    /**
     * 节假日的结束时间
     */
    @ExcelProperty(value = "节假日的结束时间")
    private String endDate;

    /**
     * 节假日名称
     */
    @ExcelProperty(value = "节假日名称")
    private String holiday;

    /**
     * 节假日描述
     */
    @ExcelProperty(value = "节假日描述")
    private String holidayRemark;

    /**
     * 调修日列表
     */
    @ExcelProperty(value = "调修日列表")
    private String inverseDays;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}

