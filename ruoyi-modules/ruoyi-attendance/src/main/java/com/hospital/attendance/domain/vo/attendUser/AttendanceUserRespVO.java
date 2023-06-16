package com.hospital.attendance.domain.vo.attendUser;

import lombok.Data;

/**
 * 考勤组人员信息返回
 */
@Data
public class AttendanceUserRespVO {

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

    /**
     * 参培年份
     */
    private String getYear;

    /**
     * 人员类型
     */
    private Integer personType;

    /**
     * 学员类型
     */
    private Integer studentType;
}
