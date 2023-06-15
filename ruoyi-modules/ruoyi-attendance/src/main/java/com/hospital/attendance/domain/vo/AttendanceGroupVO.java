package com.hospital.attendance.domain.vo;

import com.hospital.attendance.domain.AttendanceGroup;
import com.hospital.attendance.domain.AttendanceGroupArea;
import lombok.Data;

import java.util.List;
@Data
public class AttendanceGroupVO {

    private AttendanceGroup attendanceGroup;
    private List<AttendanceGroupClassesSimpleRespVO> groupClassesSimpleRespVOS;
    private List<AttendanceGroupArea> attendanceGroupAreaList;
}
