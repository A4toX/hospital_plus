package com.hospital.attendance.service;

import com.hospital.attendance.domain.vo.attendUser.AddAttendanceUserVo;
import com.hospital.attendance.domain.vo.attendUser.GroupStudentReqVo;
import com.hospital.attendance.domain.vo.attendUser.AttendanceUserRespVo;
import com.hospital.attendance.domain.vo.attendUser.StudentReqVo;
import org.dromara.common.mybatis.core.service.IBaseService;
import com.hospital.attendance.domain.bo.AttendanceGroupUserBo;
import com.hospital.attendance.domain.vo.AttendanceGroupUserVo;

import java.util.List;

/**
 * 考勤参与人员 接口
 *
 * @author liguoxian
 */
public interface IAttendanceGroupUserService extends IBaseService<AttendanceGroupUserVo, AttendanceGroupUserBo> {

    void addUser(AddAttendanceUserVo entity);

    List<AttendanceUserRespVo> listByGroupId(GroupStudentReqVo reqVO);

    List<AttendanceUserRespVo> listStudent(StudentReqVo reqVO);
}
