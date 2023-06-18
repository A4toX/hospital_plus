package com.hospital.attendance.domain;

import lombok.Data;


/**
 * @author liguoxian
 */
@Data
public class ScanAttendReq {

    /**
     * 认证信息
     */
    private String sign;

    /**
     * 签到种类 1上班 2下班
     */
    private String attendKind;
}
