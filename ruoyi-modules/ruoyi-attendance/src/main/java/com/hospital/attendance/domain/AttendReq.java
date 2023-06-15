package com.hospital.attendance.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author liguoxian
 */
@ApiModel("考勤 - 考勤打卡 req")
@Data
public class AttendReq {

    /**
     * 考勤组id
     */
    @ApiModelProperty(value = "考勤组id", required = true)
    @NotNull
    private Integer attendGroupId;

    /**
     * 是否外勤打卡 1是 2否
     */
    @ApiModelProperty(value = "是否外勤打卡 1是 2否", required = true)
    @NotNull
    private Integer areaOutside;

    /**
     * 签到类型 1是定位 2是扫描
     */
    @ApiModelProperty(value = "签到类型 1是定位 2是扫描", required = true)
    @NotNull
    private Integer attendType;

    /**
     * 签到经度
     */
    @ApiModelProperty(value = "签到经度", required = true)
    @NotNull
    private Double attendLongitude;

    /**
     * 签到纬度
     */
    @ApiModelProperty(value = "签到纬度", required = true)
    @NotNull
    private Double attendLatitude;

    /**
     * 考勤地址名称
     */
    @ApiModelProperty(value = "考勤地址名称", required = true)
    @NotNull
    private String attendAreaName;

    /**
     * 签到种类 1上班 2下班
     */
    @ApiModelProperty(value = "签到种类 1上班 2下班", required = true)
    @NotNull
    private String attendKind;
}
