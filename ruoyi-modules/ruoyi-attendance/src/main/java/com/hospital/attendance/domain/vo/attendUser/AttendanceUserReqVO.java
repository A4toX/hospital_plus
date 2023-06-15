package com.hospital.attendance.domain.vo.attendUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("管理考勤组下的人员查询条件")
public class AttendanceUserReqVO {
    @ApiModelProperty("考勤组id")
    private Integer groupId;
    @ApiModelProperty("医院id")
    private Integer hosId;
    @ApiModelProperty("姓名")
    private String userName;
    @ApiModelProperty("手机")
    private String phone;
    @ApiModelProperty("性别")
    private String gender;
    @ApiModelProperty("参培年份")
    private String getYear;
    @ApiModelProperty("人员类型")
    private Integer personType;
    @ApiModelProperty("学员类型")
    private Integer studentType;
}
