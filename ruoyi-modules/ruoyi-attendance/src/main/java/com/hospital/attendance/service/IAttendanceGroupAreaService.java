package com.hospital.attendance.service;

import com.hospital.attendance.domain.AttendanceGroupArea;
import com.hospital.attendance.domain.bo.AttendanceGroupAreaBo;
import com.hospital.attendance.domain.vo.AttendanceGroupAreaVo;
import org.dromara.common.mybatis.core.service.IBaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 考勤组信息表 Service
 *
 * @author yaoyingjie
 */
@Service
public interface IAttendanceGroupAreaService extends IBaseService<AttendanceGroupAreaVo, AttendanceGroupAreaBo> {

    List<AttendanceGroupAreaVo> getHistoryByHosId(Long hosId);
}
