package com.hospital.attendance.domain.vo.attendUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("考勤组人员信息返回")
public class AttendanceUserRespVO {
    @ApiModelProperty("用户id")
    private Integer userId;
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
