package com.hospital.attendance.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.demo.hospital.attendance.dao.AttendanceFlowDao;
import com.demo.hospital.attendance.dao.AttendanceManagementUserDao;
import com.demo.hospital.attendance.enums.AttendanceTypeEnum;
import com.demo.hospital.attendance.model.*;
import com.demo.hospital.attendance.model.vo.*;
import com.demo.hospital.attendance.utils.AttendanceUtils;
import com.demo.hospital.common.constant.ErrCode;
import com.demo.hospital.common.exceptions.BizException;
import com.demo.hospital.common.security.UserUtil;
import com.demo.hospital.common.util.DateUtils;
import com.demo.hospital.common.util.NumberUtils;
import com.demo.hospital.systemcode.enums.YesNoEnum;
import com.demo.hospital.user.model.Users;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 考勤记录表 Service
 *
 * @author liguoxian
 */
@Service
public class AttendanceFlowService {

    @Resource
    private AttendanceFlowDao attendanceFlowDao;
    @Resource
    private AttendanceManagementUserDao attendanceManagementUserDao;

    public AttendanceFlow get(Integer id) {
        return attendanceFlowDao.selectByPrimaryKey(id);
    }

    public List<AttendanceGroup> getAttendGroup() {
        return AttendanceUtils.getMyGroup(UserUtil.getCurrentUserId());
    }

    public List<AttendanceGroup> getManagerAttendGroup() {
        List<AttendanceManagementUser> managementUsers = attendanceManagementUserDao.selectByUserId(UserUtil.getCurrentUserId());
        return managementUsers.stream()
                .map(groupUser -> AttendanceUtils.getGroup(groupUser.getGroupId()))
                .filter(group -> group.getIsDelete().equals("1"))
                .collect(Collectors.toList());
    }

    public AttendanceInfoResp getAttendInfo(Integer groupId) {
        AttendanceInfoResp resp = new AttendanceInfoResp();
        List<AttendanceFlow> attendanceFlows = attendanceFlowDao.selectByDate(groupId, UserUtil.getCurrentUserId(), DateUtil.today());
        resp.setFlows(attendanceFlows);
        resp.setClasses(AttendanceUtils.getGroupClass(groupId));
        resp.setAreas(AttendanceUtils.getGroupAreas(groupId));
        return resp;
    }

    public AttendanceQrResp getQrCodeInfo(Integer groupId) {
        AttendanceGroup group = AttendanceUtils.getGroup(groupId);
        if(group.getGroupMethod() != AttendanceTypeEnum.scan.getType()) {
            throw new BizException(ErrCode.ERROR, "该考勤组未启用扫码打卡");
        }
        DateTime dateTime = new DateTime();
        AttendanceQrCodeInfo qrCodeInfo = new AttendanceQrCodeInfo();
        AttendanceQrResp qrResp = new AttendanceQrResp();
        qrCodeInfo.setAttendGroup(group);
        qrCodeInfo.setAttendType(AttendanceTypeEnum.scan.getType());
        if(YesNoEnum.yes.getValue().equals(group.getGroupCode().toString())) {
            if(group.getCodeFreshTime() == null || group.getCodeFreshTime() <= 0) {
                throw new BizException(ErrCode.ERROR, "该考勤组启动了二维码刷新，但是刷新时间配置错误");
            }
            String freshTime = DateUtils.offsetSecond(dateTime, group.getCodeFreshTime()).toString("yyyy-MM-dd HH:mm:ss");
            qrResp.setFreshTime(freshTime);
            qrCodeInfo.setFreshTime(freshTime);
        }
        AttendanceGroupClassVO groupClass = AttendanceUtils.getGroupClass(groupId);
        if(groupClass == null) {
            throw new BizException(ErrCode.ERROR, "该考勤组班次获取失败");
        }
        qrCodeInfo.setAttendClass(groupClass);
        qrResp.setSign(AttendanceUtils.getSign(qrCodeInfo, group.getCodeFreshTime()));
        qrResp.setCreateTime(dateTime.toString());
        qrResp.setFreshSeconds(group.getCodeFreshTime());
        return qrResp;
    }

    public int attend(AttendReq req) {
        Users currentUser = UserUtil.getCurrentUser();
        AttendanceFlow flow = BeanUtil.copyProperties(req, AttendanceFlow.class);
        flow.setHosId(currentUser.getHosId());
        flow.setUserId(currentUser.getUserId());
        flow.setAutomaticFlag(YesNoEnum.no.getValue());
        Date nowDate = new DateTime();
        flow.setAttendDate(DateUtils.formatDate(nowDate));
        flow.setAttendTime(DateUtils.formatTime(nowDate));
        flow.setAttendNumber(1);

        AttendanceGroupClassVO groupClass = AttendanceUtils.getGroupClass(req.getAttendGroupId());
        flow.setAttendClassesId(groupClass.getGroupClassesId());
        AttendanceUtils.handlerAttendStatus(flow, groupClass);
        flow.setNeedAttendFlag(YesNoEnum.getValueByBool(AttendanceUtils.checkNeedAttendance(flow.getAttendGroupId(), flow.getAttendDate())));
        return attendanceFlowDao.insert(flow);
    }

    public int scanAttend(ScanAttendReq req) {
        AttendanceQrCodeInfo qrCodeInfo = AttendanceUtils.verifySign(req.getSign());
        Date nowDate = new DateTime();
        if(qrCodeInfo == null || (qrCodeInfo.getFreshTime() != null && nowDate.after(DateUtil.parseDateTime(qrCodeInfo.getFreshTime())))) {
            throw new BizException(ErrCode.ERROR, "考勤信息已过期，请刷新考勤码");
        }
        Users currentUser = UserUtil.getCurrentUser();
        AttendanceFlow flow = new AttendanceFlow();
        flow.setHosId(currentUser.getHosId());
        flow.setUserId(currentUser.getUserId());
        flow.setAttendGroupId(qrCodeInfo.getAttendGroup().getId());
        flow.setAttendClassesId(qrCodeInfo.getAttendClass().getId());
        flow.setAttendType(qrCodeInfo.getAttendType());
        flow.setAreaOutside(Integer.parseInt(YesNoEnum.no.getValue()));
        flow.setAutomaticFlag(YesNoEnum.no.getValue());
        flow.setAttendKind(req.getAttendKind());
        flow.setAttendDate(DateUtils.formatDate(nowDate));
        flow.setAttendTime(DateUtils.formatTime(nowDate));
        flow.setAttendClassesId(qrCodeInfo.getAttendClass().getClassesId());
        flow.setAttendNumber(1);

        AttendanceGroupClassVO groupClass = qrCodeInfo.getAttendClass();
        AttendanceUtils.handlerAttendStatus(flow, groupClass);
        flow.setNeedAttendFlag(YesNoEnum.getValueByBool(AttendanceUtils.checkNeedAttendance(flow.getAttendGroupId(), flow.getAttendDate())));
        return attendanceFlowDao.insert(flow);
    }

    public List<AttendanceFlow> getAttendRecord(Integer userId, Integer groupId, String date) {
        List<AttendanceFlow> flows = attendanceFlowDao.selectByDate(groupId, userId, date);
        flows.forEach(flow -> {
            flow.setAttendanceGroup(AttendanceUtils.getGroup(flow.getAttendGroupId()));
            flow.setAttendanceClasses(AttendanceUtils.getGroupClass(flow.getAttendGroupId(), flow.getAttendClassesId()));
        });
        return flows;
    }

    public Map<String, List<AttendanceFlow>> getAttendRecordByMonth(Integer userId, Integer groupId, String month) {
        List<AttendanceFlow> flows = attendanceFlowDao.selectByUserIdAndMonth(groupId, userId, month);
        flows.forEach(flow -> {
            flow.setAttendanceGroup(AttendanceUtils.getGroup(flow.getAttendGroupId()));
            flow.setAttendanceClasses(AttendanceUtils.getGroupClass(flow.getAttendGroupId(), flow.getAttendClassesId()));
        });
        return flows.stream()
                .collect(Collectors.groupingBy(flow -> flow.getAttendDate()));
    }

    public AttendanceFlowCountByDayVO attendCountByDay(Integer groupId, String date) {
        AttendanceFlowCountByDayVO data = new AttendanceFlowCountByDayVO();
        data.setGroupId(groupId);
        data.setDate(date);
        if(AttendanceUtils.checkNeedAttendance(groupId, date)) {
            data.setExpectedAttendNum(CollUtil.size(AttendanceUtils.getUsersByGroupId(groupId)));
        }

        List<AttendanceFlow> flows = attendanceFlowDao.selectByDate(groupId, null, date);
        Map<Integer, List<AttendanceFlow>> flowMap = flows.stream().collect(Collectors.groupingBy(AttendanceFlow::getUserId, LinkedHashMap::new, Collectors.toList()));

        List<AttendanceFlowCountDetailByDayVO> details = new ArrayList<>();
        double workHours = 0;
        for (Map.Entry<Integer, List<AttendanceFlow>> entry : flowMap.entrySet()) {
            List<AttendanceFlow> userFlows = entry.getValue();
            AttendanceFlowCountDetailByDayVO userCount = AttendanceUtils.getUserFlowCountForOneDay(userFlows);
            details.add(userCount);
            if(YesNoEnum.yes.getValue().equals(userCount.getAttendFlag())) {
                data.setAttendNum(data.getAttendNum() + 1);
            }
            workHours += userCount.getWorkHours();
            data.setLateNum(data.getLateNum() + userCount.getLateNum());
            data.setSeriousLateNum(data.getSeriousLateNum() + userCount.getSeriousLateNum());
            data.setEarlyNum(data.getEarlyNum() + userCount.getEarlyNum());
            data.setNoAttendNum(data.getNoAttendNum() + userCount.getNoAttendNum());
            data.setOutsideNum(data.getOutsideNum() + userCount.getOutsideNum());
        }
        data.setAverageWorkHours(data.getExpectedAttendNum() > 0 ? NumberUtils.div(workHours, data.getExpectedAttendNum(), 1) : 0);
        data.setDetails(details);
        return data;
    }

    public AttendanceFlowCountByDateRangeVO attendCountByDateRange(Integer groupId, String startDate, String endDate) {
        AttendanceFlowCountByDateRangeVO data = new AttendanceFlowCountByDateRangeVO();
        data.setGroupId(groupId);

        List<AttendanceFlow> flows = attendanceFlowDao.selectByDateRange(groupId, null, startDate, endDate);
        Map<Integer, List<AttendanceFlow>> flowMap = flows.stream().collect(Collectors.groupingBy(AttendanceFlow::getUserId, LinkedHashMap::new, Collectors.toList()));
        List<AttendanceFlowCountDetailByDateRangeVO> details = new ArrayList<>();
        double workHours = 0;
        int needAttendDays = 0;
        for (Map.Entry<Integer, List<AttendanceFlow>> entry : flowMap.entrySet()) {
            List<AttendanceFlow> userFlows = entry.getValue();
            AttendanceFlowCountDetailByDateRangeVO countDateRange = AttendanceUtils.getUserFlowCountForDateRange(groupId, startDate, endDate, userFlows);
            details.add(countDateRange);

            workHours += countDateRange.getWorkHours();
            data.setLateNum(data.getLateNum() + (countDateRange.getLateNum() > 0 ? 1: 0));
            data.setSeriousLateNum(data.getSeriousLateNum() + (countDateRange.getSeriousLateNum() > 0 ? 1: 0));
            data.setEarlyNum(data.getEarlyNum() + (countDateRange.getEarlyNum() > 0 ? 1: 0));
            data.setNoAttendNum(data.getNoAttendNum() + (countDateRange.getNoAttendNum() > 0 ? 1: 0));
            data.setOutsideNum(data.getOutsideNum() + (countDateRange.getOutsideNum() > 0 ? 1: 0));
            data.setNoWorkNum(data.getNoWorkNum() + (countDateRange.getNoAttendDays() > 0 ? 1: 0));
            needAttendDays += countDateRange.getNeedAttendDays();
        }
        data.setAverageWorkHours(needAttendDays > 0 ? NumberUtils.div(workHours, needAttendDays, 1) : 0);
        data.setDetails(details);
        return data;
    }
}
