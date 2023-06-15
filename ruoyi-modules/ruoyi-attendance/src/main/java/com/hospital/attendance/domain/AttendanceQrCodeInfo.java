package com.hospital.attendance.domain;

import com.demo.hospital.attendance.model.vo.AttendanceGroupClassVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liguoxian
 */
@Data
public class AttendanceQrCodeInfo {

    @ApiModelProperty("考勤组id")
    private AttendanceGroup attendGroup;

    @ApiModelProperty("考勤班次")
    private AttendanceGroupClassVO attendClass;

    @ApiModelProperty("签到类型")
    private Integer attendType;

    @ApiModelProperty("刷新时间")
    private String freshTime;
}
