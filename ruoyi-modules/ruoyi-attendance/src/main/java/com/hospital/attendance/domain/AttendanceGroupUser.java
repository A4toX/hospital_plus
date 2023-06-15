package com.hospital.attendance.domain;

import com.demo.hospital.common.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 考勤参与人员 model
 *
 * @author yaoyingjie
 */
@ApiModel("考勤参与人员")
public class AttendanceGroupUser extends BaseEntity<Integer> implements Serializable {

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

