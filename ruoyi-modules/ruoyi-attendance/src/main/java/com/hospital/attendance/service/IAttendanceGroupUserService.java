package com.hospital.attendance.service;

import com.hospital.attendance.domain.vo.attendUser.AddAttendanceUserVo;
import com.hospital.attendance.domain.vo.attendUser.AttendanceUserReqVO;
import com.hospital.attendance.domain.vo.attendUser.AttendanceUserRespVO;
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

    List<AttendanceUserRespVO> listByGroupId(AttendanceUserReqVO reqVO);
}
