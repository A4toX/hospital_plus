package com.hospital.attendance.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.hospital.attendance.domain.AttendanceManagementUser;

import java.io.Serial;
import java.io.Serializable;

/**
 * 考勤组负责人 视图对象
 *
 * @author liguoxian
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AttendanceManagementUser.class)
public class AttendanceManagementUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色代码
     */
    private String roleCodes;
    /**
     * 角色名称
     */
    private String roleNames;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 姓名
     */
    private String userName;
    /**
     * 手机
     */
    private String phone;
    /**
     * 性别
     */
    private String gender;

}

