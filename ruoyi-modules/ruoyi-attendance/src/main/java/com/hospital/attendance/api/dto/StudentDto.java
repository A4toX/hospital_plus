package com.hospital.attendance.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lgx
 */
@Data
@Accessors(chain = true)
public class StudentDto {

    /**
     * 用户id
     */
    private Long userId;

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
