package com.hospital.attendance.domain;

import com.demo.hospital.common.page.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 考勤组信息表 model
 *
 * @author yaoyingjie
 */
@Data
@ApiModel("考勤组信息表 - 分页请求")
public class AttendanceGroupPageReq extends BasePageParam implements Serializable {

    @ApiModelProperty("考勤组名称")
    private String groupName;

}

