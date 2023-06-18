package com.hospital.attendance.domain.vo.attendUser;

import lombok.Data;

/**
 * 管理考勤组下的人员查询条件
 */
@Data
public class AttendanceUserReqVO {

    /**
     * 考勤组id
     */
    private Integer groupId;

    /**
     * 医院id
     */
    private Integer hosId;

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
