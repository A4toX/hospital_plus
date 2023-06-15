package com.hospital.attendance.domain.vo.attendUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("管理考勤组下的管理人员查询条件")
public class AttendanceMUserReqVO {
    @ApiModelProperty("角色代码")
    private String roleCode;
    @ApiModelProperty("医院id")
    private Integer hosId;
    @ApiModelProperty("考勤组id")
    private Integer groupId;
    @ApiModelProperty("姓名")
    private String userName;
    @ApiModelProperty("手机")
    private String phone;
    @ApiModelProperty("性别")
    private String gender;
}
