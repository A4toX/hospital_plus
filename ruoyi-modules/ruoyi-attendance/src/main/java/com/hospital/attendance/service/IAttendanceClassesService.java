package com.hospital.attendance.service;

import com.hospital.attendance.domain.bo.AttendanceClassesBo;
import com.hospital.attendance.domain.vo.AttendanceClassesVo;
import org.dromara.common.mybatis.core.service.IBaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 班次设置表 Service
 *
 * @author yaoyingjie
 */
public interface IAttendanceClassesService extends IBaseService<AttendanceClassesVo, AttendanceClassesBo> {

    List<AttendanceClassesVo> listAll();
}
