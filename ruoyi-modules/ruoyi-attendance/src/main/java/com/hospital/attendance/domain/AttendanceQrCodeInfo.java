package com.hospital.attendance.domain;

import com.hospital.attendance.domain.vo.AttendanceGroupClassVO;
import com.hospital.attendance.domain.vo.AttendanceGroupVo;
import lombok.Data;

/**
 * @author liguoxian
 */
@Data
public class AttendanceQrCodeInfo {

    /**
     * 考勤组id
     */
    private AttendanceGroupVo attendGroup;

    /**
     * 考勤班次
     */
    private AttendanceGroupClassVO attendClass;

    /**
     * 签到类型
     */
    private Integer attendType;

    /**
     * 刷新时间
     */
    private String freshTime;
}
