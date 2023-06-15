package com.hospital.attendance.domain;

import com.demo.hospital.common.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 考勤组负责人(AttendanceManagementUser) model
 *
 * @author makejava
 * @since 2023-05-21 19:29:23
 */
@ApiModel("考勤组负责人")
public class AttendanceManagementUser extends BaseEntity<Integer> implements Serializable {

    /**
     * 考勤组id
     */
    @ApiModelProperty("考勤组id")
    private Integer groupId;

    /**
     * 用户id
     */
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

