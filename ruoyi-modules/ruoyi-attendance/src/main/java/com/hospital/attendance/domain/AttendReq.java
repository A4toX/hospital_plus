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
     * 是否外勤打卡 1是 2否
     */
    private Integer areaOutside;

    /**
     * 签到类型 1是定位 2是扫描
     */
    private Integer attendType;

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
