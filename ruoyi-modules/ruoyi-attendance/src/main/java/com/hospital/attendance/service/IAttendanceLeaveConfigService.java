package com.hospital.attendance.service;

import org.dromara.common.mybatis.core.service.IBaseService;
import com.hospital.attendance.domain.bo.AttendanceLeaveConfigBo;
import com.hospital.attendance.domain.vo.AttendanceLeaveConfigVo;

/**
 * 请假配置 接口
 *
 * @author liguoxian
 */
public interface IAttendanceLeaveConfigService extends IBaseService<AttendanceLeaveConfigVo, AttendanceLeaveConfigBo> {

    int updateStatus(Long id, String status);
}
