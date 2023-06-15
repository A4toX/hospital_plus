package com.hospital.attendance.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liguoxian
 */
@ApiModel("考勤 - 考勤信息 resp")
@Data
public class AttendanceInfoResp {

    @ApiModelProperty("考勤记录")
    private List<AttendanceFlow> flows;

    @ApiModelProperty("考勤范围")
    private List<AttendanceGroupArea> areas;

    @ApiModelProperty("考勤班次")
    private AttendanceClasses classes;

    @ApiModelProperty("是否需要打卡")
    private String needAttendFlag;
}
