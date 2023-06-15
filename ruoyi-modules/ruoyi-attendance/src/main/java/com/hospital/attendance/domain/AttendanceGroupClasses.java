package com.hospital.attendance.domain;

import com.demo.hospital.common.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 排班班次表(AttendanceGroupClasses) model
 *
 * @author makejava
 * @since 2023-06-14 18:32:03
 */
@ApiModel("排班班次表")
public class AttendanceGroupClasses extends BaseEntity<Integer> implements Serializable {

    /**
     * 考勤组id
     */
    @ApiModelProperty("考勤组id")
    private Integer groupId;

    /**
     * 班次id
     */
    @ApiModelProperty("班次id")
    private Integer classesId;

    /**
     * 周次(1-7)
     */
    @ApiModelProperty("周次(1-7)")
    private Integer weekly;

    /**
     * 是否需要打卡1是2否
     */
    @ApiModelProperty("是否需要打卡1是2否")
    private Integer status;


    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getClassesId() {
        return classesId;
    }

    public void setClassesId(Integer classesId) {
        this.classesId = classesId;
    }

    public Integer getWeekly() {
        return weekly;
    }

    public void setWeekly(Integer weekly) {
        this.weekly = weekly;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

