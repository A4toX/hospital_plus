package com.hospital.attendance.domain;

import com.demo.hospital.common.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 考勤地点表(AttendanceGroupArea) model
 *
 * @author makejava
 * @since 2023-05-21 18:25:42
 */
@EqualsAndHashCode(of = {"areaName", "longitude", "latitude"})
@Data
@ApiModel("考勤地点表")
public class AttendanceGroupArea extends BaseEntity<Integer> implements Serializable {

    /**
     * 考勤组id
     */
    @ApiModelProperty("考勤组id")
    private Integer groupId;

    /**
     * 地点名称
     */
    @ApiModelProperty("地点名称")
    private String areaName;

    /**
     * 经度
     */
    @ApiModelProperty("经度")
    private Double longitude;

    /**
     * 纬度
     */
    @ApiModelProperty("纬度")
    private Double latitude;

    /**
     * 考勤范围
     */
    @ApiModelProperty("考勤范围")
    private Double attendRange;


}

