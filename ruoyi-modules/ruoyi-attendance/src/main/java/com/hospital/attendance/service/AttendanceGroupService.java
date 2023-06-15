package com.hospital.attendance.service;

import com.hospital.attendance.domain.bo.AttendanceGroupBo;
import com.hospital.attendance.domain.vo.AttendanceGroupVO;
import org.dromara.common.mybatis.core.service.IBaseService;
import org.springframework.stereotype.Service;

/**
 * 考勤组信息表 Service
 *
 * @author yaoyingjie
 */
@Service
public interface AttendanceGroupService extends IBaseService<AttendanceGroupVO, AttendanceGroupBo> {

}
