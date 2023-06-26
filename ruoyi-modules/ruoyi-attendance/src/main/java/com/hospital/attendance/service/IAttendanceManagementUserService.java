package com.hospital.attendance.service;

import com.hospital.attendance.domain.vo.attendUser.AddAttendanceUserVo;
import com.hospital.attendance.domain.vo.attendUser.GroupManagerReqVo;
import com.hospital.attendance.domain.vo.attendUser.AttendanceMUserRespVo;
import com.hospital.attendance.domain.vo.attendUser.TeacherReqVo;
import org.dromara.common.mybatis.core.service.IBaseService;
import com.hospital.attendance.domain.bo.AttendanceManagementUserBo;
import com.hospital.attendance.domain.vo.AttendanceManagementUserVo;

import java.util.List;

/**
 * 考勤组负责人 接口
 *
 * @author liguoxian
 */
public interface IAttendanceManagementUserService extends IBaseService<AttendanceManagementUserVo, AttendanceManagementUserBo> {

    void addUser(AddAttendanceUserVo entity);

    List<AttendanceMUserRespVo> listByGroupId(GroupManagerReqVo reqVO);

    List<AttendanceMUserRespVo> listAllStaff(TeacherReqVo reqVO);
}
