package com.hospital.attendance.domain;

import com.demo.hospital.common.page.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 考勤参与人员 model
 *
 * @author yaoyingjie
 */
@ApiModel("考勤参与人员 - 分页请求")
public class AttendanceGroupUserPageReq extends BasePageParam implements Serializable {

    @ApiModelProperty("考勤组id")
    private Integer groupId;

    @ApiModelProperty("用户id")
    private Integer userId;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

