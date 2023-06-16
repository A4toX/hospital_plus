package com.hospital.attendance.api;

import com.hospital.attendance.domain.vo.attendUser.AttendanceUserReqVO;
import com.hospital.attendance.domain.vo.attendUser.AttendanceUserRespVO;

import java.util.List;

/**
 * @author lgx
 */
public interface StudentAPi {

    List<AttendanceUserRespVO> listAllStudentByhosId(AttendanceUserReqVO reqVO);
}
