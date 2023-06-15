package com.hospital.attendance.domain.vo.attendUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("管理考勤组下的人员")
public class AddAttendanceUserVO {
    @ApiModelProperty("考勤组id")
    private Integer groupId;
    @ApiModelProperty("用户idList")
    private List<Integer> addUserIds;
}
