package com.hospital.attendance.domain.vo.attendUser;

import lombok.Data;

/**
 * 管理考勤组下的管理人员查询条件
 */
@Data
public class AttendanceMUserReqVO {

    /**
     * 角色代码
     */
    private String roleCode;

    /**
     * 医院id
     */
    private Integer hosId;

    /**
     * 考勤组id
     */
    private Integer groupId;

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
