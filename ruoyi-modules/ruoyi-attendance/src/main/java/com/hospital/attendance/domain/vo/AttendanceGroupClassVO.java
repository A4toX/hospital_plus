package com.hospital.attendance.domain.vo;

import com.demo.hospital.attendance.model.AttendanceClasses;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("考勤组下的周次信息VO")
public class AttendanceGroupClassVO extends AttendanceClasses {


    @ApiModelProperty("考勤组班次关联id")
    private Integer groupClassesId;

    @ApiModelProperty("考勤组id")
    private Integer groupId;

    @ApiModelProperty("周次(1-7)")
    private Integer weekly;

    @ApiModelProperty("考勤班次ID")
    private Integer classesId;

    @ApiModelProperty("是否需要打卡")
    private Integer status;
}
