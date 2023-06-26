package com.hospital.attendance.service;

import com.hospital.attendance.domain.*;
import com.hospital.attendance.domain.bo.AttendanceFlowBo;
import com.hospital.attendance.domain.vo.*;
import org.dromara.common.mybatis.core.service.IBaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 考勤组信息表 Service
 *
 * @author yaoyingjie
 */
public interface IAttendanceFlowService extends IBaseService<AttendanceFlowVo, AttendanceFlowBo> {

    List<AttendanceGroupVo> getAttendGroup();

    List<AttendanceGroupVo> getManagerAttendGroup();

    AttendanceInfoResp getAttendInfo(Long groupId);

    AttendanceQrResp getQrCodeInfo(Long groupId);

    int attend(AttendReq req);

    int scanAttend(ScanAttendReq req);

    List<AttendanceFlowVo> getAttendRecord(Long userId, Long groupId, String date);

    Map<String, List<AttendanceFlowVo>> getAttendRecordByMonth(Long userId, Long groupId, String month);

    AttendanceFlowCountByDayVo attendCountByDay(Long groupId, String date);

    AttendanceFlowCountByDateRangeVo attendCountByDateRange(Long groupId, String startDate, String endDate);
}
