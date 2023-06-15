package com.hospital.attendance.domain;

import com.demo.hospital.common.page.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 考勤组负责人(AttendanceManagementUser) model
 *
 * @author makejava
 * @since 2023-05-21 19:29:23
 */
@ApiModel("考勤组负责人 - 分页请求")
public class AttendanceManagementUserPageReq extends BasePageParam implements Serializable {

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

