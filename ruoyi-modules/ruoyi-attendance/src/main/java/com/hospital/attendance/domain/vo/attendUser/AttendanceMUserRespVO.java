package com.hospital.attendance.domain.vo.attendUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("管理考勤组下的管理人员返回")
public class AttendanceMUserRespVO {
    @ApiModelProperty("角色代码")
    private String roleCodes;
    @ApiModelProperty("角色名称")
    private String roleNames;
    @ApiModelProperty("用户id")
    private Integer userId;
    @ApiModelProperty("姓名")
    private String userName;
    @ApiModelProperty("手机")
    private String phone;
    @ApiModelProperty("性别")
    private String gender;
}
