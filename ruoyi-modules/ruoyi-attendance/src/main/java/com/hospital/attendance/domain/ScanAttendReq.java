package com.hospital.attendance.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author liguoxian
 */
@ApiModel("考勤 - 扫码考勤打卡 req")
@Data
public class ScanAttendReq {

    /**
     * 认证信息
     */
    @ApiModelProperty(value = "认证信息", required = true)
    @NotNull
    private String sign;

    /**
     * 签到种类 1上班 2下班
     */
    @ApiModelProperty(value = "签到种类 1上班 2下班", required = true)
    @NotNull
    private String attendKind;
}
