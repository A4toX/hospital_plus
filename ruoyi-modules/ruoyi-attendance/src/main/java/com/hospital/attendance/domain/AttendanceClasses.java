package com.hospital.attendance.domain;

import com.demo.hospital.common.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 班次设置表(AttendanceClasses) model
 *
 * @author makejava
 * @since 2023-05-21 17:37:13
 */
@ApiModel("班次设置表")
@Data
public class AttendanceClasses extends BaseEntity<Integer> implements Serializable {

    /**
     * 班次名称
     */
    @ApiModelProperty("班次名称")
    private String name;

    /**
     * 上班开始打卡时间
     */
    @ApiModelProperty("上班开始打卡时间")
    private String workTime;

    /**
     * 下班开始打卡时间
     */
    @ApiModelProperty("下班开始打卡时间")
    private String afterTime;

    /**
     * 晚多少分钟为迟到
     */
    @ApiModelProperty("晚多少分钟为迟到")
    private Integer workLateMin;

    /**
     * 是否开启严重迟到(1是2否)
     */
    @ApiModelProperty("是否开启严重迟到(1是2否)")
    private Integer isSeriousLate;

    /**
     * 晚多少分钟为严重迟到
     */
    @ApiModelProperty("晚多少分钟为严重迟到")
    private Integer workSeriousLateMin;

    /**
     * 晚多少分钟为上班旷工
     */
    @ApiModelProperty("晚多少分钟为上班旷工")
    private Integer workAbsMin;

    /**
     * 是否开启下班自动打卡(1是2否)
     */
    @ApiModelProperty("是否开启下班自动打卡(1是2否)")
    private Integer isAutoAfter;

    /**
     * 早多少分钟为下班旷工
     */
    @ApiModelProperty("早多少分钟为下班旷工")
    private Integer afterAbsMin;

    /**
     * 早多少分钟为早退
     */
    @ApiModelProperty("早多少分钟为早退")
    private Integer afterLeaveEarly;
}

