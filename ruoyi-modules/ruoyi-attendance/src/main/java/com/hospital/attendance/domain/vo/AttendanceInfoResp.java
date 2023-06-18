package com.hospital.attendance.domain.vo;

import com.hospital.attendance.domain.AttendanceFlow;
import com.hospital.attendance.domain.AttendanceGroupArea;
import lombok.Data;

import java.util.List;

/**
 * 考勤信息 resp
 *
 * @author liguoxian
 */
@Data
public class AttendanceInfoResp {

    /**
     * 考勤记录
     */
    private List<AttendanceFlowVo> flows;

    /**
     * 考勤范围
     */
    private List<AttendanceGroupAreaVo> areas;

    /**
     * 考勤班次
     */
    private AttendanceClassesVo classes;

    /**
     * 是否需要打卡
     */
    private String needAttendFlag;
}
