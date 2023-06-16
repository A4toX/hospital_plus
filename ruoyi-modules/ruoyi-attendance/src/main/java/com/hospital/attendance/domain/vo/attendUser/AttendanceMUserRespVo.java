package com.hospital.attendance.domain.vo.attendUser;

import lombok.Data;

@Data
public class AttendanceMUserRespVo {

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
