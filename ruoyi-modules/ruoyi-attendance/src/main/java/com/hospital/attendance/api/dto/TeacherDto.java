package com.hospital.attendance.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 工作人员
 * @author lgx
 */
@Data
@Accessors(chain = true)
public class TeacherDto {

    /**
     * 角色代码，多个逗号隔开
     */
    private String roleCodes;

    /**
     * 角色名称，多个逗号隔开
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
