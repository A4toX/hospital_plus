package com.hospital.attendance.domain;

import com.demo.hospital.common.page.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 考勤记录表 model
 *
 * @author liguoxian
 */
@ApiModel("考勤记录表 - 分页请求")
public class AttendanceFlowPageReq extends BasePageParam implements Serializable {

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("考勤组id")
    private Integer attendGroupId;

    @ApiModelProperty("考勤组班次id")
    private Integer attendClassesId;

    @ApiModelProperty("签到类型")
    private Integer attendType;

    @ApiModelProperty("签到状态")
    private Integer attendStatus;

    @ApiModelProperty("签到时间")
    private String attendTime;

    @ApiModelProperty("签到经度")
    private Double attendLongitude;

    @ApiModelProperty("签到纬度")
    private Double attendLatitude;

    @ApiModelProperty("考勤地址名称")
    private String attendAreaName;

    @ApiModelProperty("签到种类 1上班 2下班")
    private String attendKind;

    @ApiModelProperty("请假流水id")
    private Integer leaveId;

    @ApiModelProperty("是否外勤打卡 1是 2否")
    private Integer areaOutside;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAttendGroupId() {
        return attendGroupId;
    }

    public void setAttendGroupId(Integer attendGroupId) {
        this.attendGroupId = attendGroupId;
    }

    public Integer getAttendClassesId() {
        return attendClassesId;
    }

    public void setAttendClassesId(Integer attendClassesId) {
        this.attendClassesId = attendClassesId;
    }

    public Integer getAttendType() {
        return attendType;
    }

    public void setAttendType(Integer attendType) {
        this.attendType = attendType;
    }

    public Integer getAttendStatus() {
        return attendStatus;
    }

    public void setAttendStatus(Integer attendStatus) {
        this.attendStatus = attendStatus;
    }

    public String getAttendTime() {
        return attendTime;
    }

    public void setAttendTime(String attendTime) {
        this.attendTime = attendTime;
    }

    public Double getAttendLongitude() {
        return attendLongitude;
    }

    public void setAttendLongitude(Double attendLongitude) {
        this.attendLongitude = attendLongitude;
    }

    public Double getAttendLatitude() {
        return attendLatitude;
    }

    public void setAttendLatitude(Double attendLatitude) {
        this.attendLatitude = attendLatitude;
    }

    public String getAttendAreaName() {
        return attendAreaName;
    }

    public void setAttendAreaName(String attendAreaName) {
        this.attendAreaName = attendAreaName;
    }

    public String getAttendKind() {
        return attendKind;
    }

    public void setAttendKind(String attendKind) {
        this.attendKind = attendKind;
    }

    public Integer getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Integer leaveId) {
        this.leaveId = leaveId;
    }

    public Integer getAreaOutside() {
        return areaOutside;
    }

    public void setAreaOutside(Integer areaOutside) {
        this.areaOutside = areaOutside;
    }
}

