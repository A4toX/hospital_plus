package com.hospital.attendance.domain;

import lombok.Data;

/**
 * @author liguoxian
 */
@Data
public class AttendanceQrResp {

    /**
     * 考勤码创建时间
     */
    private String createTime;

    /**
     * 考勤码有效期（秒）
     */
    private Integer freshSeconds;

    /**
     * 考勤码刷新时间
     */
    private String freshTime;

    /**
     * 考勤认证标识
     */
    private String sign;
}
