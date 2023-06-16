package com.hospital.attendance.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.hospital.attendance.domain.AttendanceGroupUser;

import java.io.Serial;
import java.io.Serializable;

/**
 * 考勤参与人员 视图对象
 *
 * @author liguoxian
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AttendanceGroupUser.class)
public class AttendanceGroupUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "主键")
    private Long id;

    @ExcelProperty(value = "考勤组id")
    private Long groupId;

    @ExcelProperty(value = "用户id")
    private Long userId;

    @ExcelProperty(value = "备注")
    private String remark;

}

