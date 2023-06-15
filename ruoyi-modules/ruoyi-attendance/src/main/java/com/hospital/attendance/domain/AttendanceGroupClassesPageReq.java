package com.hospital.attendance.domain;

import com.demo.hospital.common.page.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 排班班次表(AttendanceGroupClasses) model
 *
 * @author makejava
 * @since 2023-06-14 18:32:03
 */
@ApiModel("排班班次表 - 分页请求")
public class AttendanceGroupClassesPageReq extends BasePageParam implements Serializable {

    @ApiModelProperty("考勤组id")
    private Integer groupId;

    @ApiModelProperty("班次id")
    private Integer classesId;

    @ApiModelProperty("周次(1-7)")
    private Integer weekly;

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

