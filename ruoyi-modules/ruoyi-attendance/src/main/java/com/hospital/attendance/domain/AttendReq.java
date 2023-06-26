package com.hospital.attendance.domain;

import lombok.Data;

/**
 * 考勤打卡 req
 *
 * @author liguoxian
 */
@Data
public class AttendReq {

    /**
     * 考勤组id
     */
    private Long attendGroupId;

    /**
     * 是否外勤打卡
     */
    private String areaOutside;

    /**
     * 签到经度
     */
    private Double attendLongitude;

    /**
     * 签到纬度
     */
    private Double attendLatitude;

    /**
     * 考勤地址名称
     */
    private String attendAreaName;

    /**
     * 签到种类 1上班 2下班
     */
    private String attendKind;
}
