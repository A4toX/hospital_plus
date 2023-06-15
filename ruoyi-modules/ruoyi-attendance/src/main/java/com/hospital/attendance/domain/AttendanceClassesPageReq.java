package com.hospital.attendance.domain;

import com.demo.hospital.common.page.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 班次设置表(AttendanceClasses) model
 *
 * @author makejava
 * @since 2023-05-21 17:37:13
 */
@Data
@ApiModel("班次设置表 - 分页请求")
public class AttendanceClassesPageReq extends BasePageParam implements Serializable {

    @ApiModelProperty("班次名称")
    private String name;

}

