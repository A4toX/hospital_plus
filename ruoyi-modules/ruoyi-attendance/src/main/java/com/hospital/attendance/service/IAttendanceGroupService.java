package com.hospital.attendance.service;

import com.hospital.attendance.domain.bo.AttendanceGroupBo;
import com.hospital.attendance.domain.vo.AttendanceGroupRespVo;
import com.hospital.attendance.domain.vo.AttendanceGroupVo;
import org.dromara.common.mybatis.core.service.IBaseService;
import org.springframework.stereotype.Service;

/**
 * 考勤组信息表 Service
 *
 * @author yaoyingjie
 */
public interface IAttendanceGroupService extends IBaseService<AttendanceGroupVo, AttendanceGroupBo> {

    AttendanceGroupRespVo selectAllInfoById(Long id);
}
