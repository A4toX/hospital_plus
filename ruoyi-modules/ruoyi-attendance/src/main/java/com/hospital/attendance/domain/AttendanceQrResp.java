package com.hospital.attendance.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liguoxian
 */
@ApiModel("考勤码 - 教师考勤码考勤信息 resp")
@Data
public class AttendanceQrResp {

    /**
     * 考勤码创建时间
     */
    @ApiModelProperty("考勤码创建时间")
    private String createTime;

    /**
     * 考勤码有效期（秒）
     */
    @ApiModelProperty("考勤码有效期（秒）")
    private Integer freshSeconds;

    /**
     * 考勤码刷新时间
     */
    @ApiModelProperty("考勤码刷新时间")
    private String freshTime;

    /**
     * 考勤认证标识
     */
    @ApiModelProperty("考勤认证标识")
    private String sign;
}
