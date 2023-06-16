package com.hospital.attendance.domain.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 考勤组信息表 视图对象
 *
 * @author liguoxian
 */
@Data
public class AttendanceGroupRespVo implements Serializable {

    @Serial
    private AttendanceGroupVo attendanceGroup;

    /**
     * 考勤班次信息(固定班次)
     */
    private List<AttendanceGroupClassesSimpleRespVO> groupClassesSimpleRespVOS;

    /**
     * 考勤地点信息(定位考勤)
     */
    private List<AttendanceGroupAreaVo> attendanceGroupAreaList;
}

