package com.hospital.attendance.domain;

import com.demo.hospital.common.page.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 考勤地点表(AttendanceGroupArea) model
 *
 * @author makejava
 * @since 2023-05-21 18:25:42
 */
@ApiModel("考勤地点表 - 分页请求")
public class AttendanceGroupAreaPageReq extends BasePageParam implements Serializable {

    @ApiModelProperty("考勤组id")
    private Integer groupId;

    @ApiModelProperty("地点名称")
    private String areaName;

    @ApiModelProperty("经度")
    private Double longitude;

    @ApiModelProperty("纬度")
    private Double latitude;

    @ApiModelProperty("考勤范围")
    private Double attendRange;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getAttendRange() {
        return attendRange;
    }

    public void setAttendRange(Double attendRange) {
        this.attendRange = attendRange;
    }
}

