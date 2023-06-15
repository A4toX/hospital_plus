package com.hospital.attendance.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("考勤组周次关联信息返回")
public class AttendanceGroupClassesSimpleRespVO {
    @ApiModelProperty("班次id")
    private Integer classesId;

    @ApiModelProperty("班次名称")
    private String classesName;

    @ApiModelProperty("上班开始打卡时间")
    private String workTime;

    @ApiModelProperty("下班开始打卡时间")
    private String afterTime;

    @ApiModelProperty("考勤组班次关联id")
    private Integer groupClassesId;

    @ApiModelProperty("考勤组id")
    private Integer groupId;

    @ApiModelProperty("周次(1-7)")
    private Integer weekly;
}
