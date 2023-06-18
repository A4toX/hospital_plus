package com.hospital.attendance.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.hospital.attendance.domain.*;
import com.hospital.attendance.domain.bo.AttendanceFlowBo;
import com.hospital.attendance.domain.vo.*;
import com.hospital.attendance.enums.AttendanceMethodEnum;
import com.hospital.attendance.mapper.AttendanceFlowMapper;
import com.hospital.attendance.mapper.AttendanceManagementUserMapper;
import com.hospital.attendance.service.IAttendanceFlowService;
import com.hospital.attendance.utils.AttendanceUtils;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.enums.YesNoEnum;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.mybatis.core.service.BaseServiceImpl;
import org.dromara.common.satoken.utils.LoginHelper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 考勤记录表 Service
 *
 * @author liguoxian
 */
@Service
@RequiredArgsConstructor
public class AttendanceFlowServiceImpl extends BaseServiceImpl<AttendanceFlowMapper, AttendanceFlow, AttendanceFlowVo, AttendanceFlowBo> implements IAttendanceFlowService {

    private AttendanceManagementUserMapper attendanceManagementUserMapper;

    @Override
    public List<AttendanceGroupVo> getAttendGroup() {
        List<AttendanceGroup> groups = AttendanceUtils.getMyGroup(LoginHelper.getUserId());
        return MapstructUtils.convert(groups, AttendanceGroupVo.class);
    }

    @Override
    public List<AttendanceGroupVo> getManagerAttendGroup() {
        List<AttendanceManagementUser> managementUsers = attendanceManagementUserMapper.selectByUserId(LoginHelper.getUserId());
        List<AttendanceGroup> groups = managementUsers.stream()
            .map(groupUser -> AttendanceUtils.getGroup(groupUser.getGroupId()))
            .collect(Collectors.toList());
        return MapstructUtils.convert(groups, AttendanceGroupVo.class);
    }

    @Override
    public AttendanceInfoResp getAttendInfo(Long groupId) {
        AttendanceInfoResp resp = new AttendanceInfoResp();
        List<AttendanceFlowVo> attendanceFlows = mapper.selectByDate(groupId, LoginHelper.getUserId(), DateUtil.today());
        resp.setFlows(attendanceFlows);
        resp.setClasses(AttendanceUtils.getGroupClass(groupId));
        resp.setAreas(AttendanceUtils.getGroupAreas(groupId));
        return resp;
    }

    @Override
    public AttendanceQrResp getQrCodeInfo(Long groupId) {
        AttendanceGroup group = AttendanceUtils.getGroup(groupId);
        if (group.getGroupMethod() != AttendanceMethodEnum.scan.getType()) {
            throw new ServiceException("该考勤组未启用扫码打卡");
        }
        DateTime dateTime = new DateTime();
        AttendanceQrCodeInfo qrCodeInfo = new AttendanceQrCodeInfo();
        AttendanceQrResp qrResp = new AttendanceQrResp();
        qrCodeInfo.setAttendGroup(MapstructUtils.convert(group, AttendanceGroupVo.class));
        qrCodeInfo.setAttendType(AttendanceMethodEnum.scan.getType());
        if (YesNoEnum.YES.getValue().equals(group.getGroupCode())) {
            if (group.getCodeFreshTime() == null || group.getCodeFreshTime() <= 0) {
                throw new ServiceException("该考勤组启动了二维码刷新，但是刷新时间配置错误");
            }
            String freshTime = DateUtil.offsetSecond(dateTime, group.getCodeFreshTime()).toString("yyyy-MM-dd HH:mm:ss");
            qrResp.setFreshTime(freshTime);
            qrCodeInfo.setFreshTime(freshTime);
        }
        AttendanceGroupClassVo groupClass = AttendanceUtils.getGroupClass(groupId);
        if (groupClass == null) {
            throw new ServiceException("该考勤组班次获取失败");
        }
        qrCodeInfo.setAttendClass(groupClass);
        qrResp.setSign(AttendanceUtils.getSign(qrCodeInfo, group.getCodeFreshTime()));
        qrResp.setCreateTime(dateTime.toString());
        qrResp.setFreshSeconds(group.getCodeFreshTime());
        return qrResp;
    }

    @Override
    public int attend(AttendReq req) {
        LoginUser user = LoginHelper.getLoginUser();
        AttendanceFlow flow = BeanUtil.copyProperties(req, AttendanceFlow.class);
        flow.setUserId(user.getUserId());
        flow.setAutomaticFlag(YesNoEnum.NO.getValue());
        Date nowDate = new DateTime();
        flow.setAttendDate(DateUtil.formatDate(nowDate));
        flow.setAttendTime(DateUtil.formatTime(nowDate));
        flow.setAttendNumber(1);

        AttendanceGroupClassVo groupClass = AttendanceUtils.getGroupClass(req.getAttendGroupId());
        flow.setAttendClassesId(groupClass.getGroupClassesId());
        AttendanceUtils.handlerAttendStatus(flow, groupClass);
        flow.setNeedAttendFlag(YesNoEnum.getValueByBool(AttendanceUtils.checkNeedAttendance(flow.getAttendGroupId(), flow.getAttendDate())));
        return mapper.insert(flow);
    }

    @Override
    public int scanAttend(ScanAttendReq req) {
        AttendanceQrCodeInfo qrCodeInfo = AttendanceUtils.verifySign(req.getSign());
        Date nowDate = new DateTime();
        if (qrCodeInfo == null || (qrCodeInfo.getFreshTime() != null && nowDate.after(DateUtil.parseDateTime(qrCodeInfo.getFreshTime())))) {
            throw new ServiceException("考勤信息已过期，请刷新考勤码");
        }
        LoginUser user = LoginHelper.getLoginUser();
        AttendanceFlow flow = new AttendanceFlow();
        flow.setUserId(user.getUserId());
        flow.setAttendGroupId(qrCodeInfo.getAttendGroup().getId());
        flow.setAttendClassesId(qrCodeInfo.getAttendClass().getId());
        flow.setAttendType(qrCodeInfo.getAttendType());
        flow.setAreaOutside(YesNoEnum.NO.getValue());
        flow.setAutomaticFlag(YesNoEnum.NO.getValue());
        flow.setAttendKind(req.getAttendKind());
        flow.setAttendDate(DateUtil.formatDate(nowDate));
        flow.setAttendTime(DateUtil.formatTime(nowDate));
        flow.setAttendClassesId(qrCodeInfo.getAttendClass().getClassesId());
        flow.setAttendNumber(1);

        AttendanceGroupClassVo groupClass = qrCodeInfo.getAttendClass();
        AttendanceUtils.handlerAttendStatus(flow, groupClass);
        flow.setNeedAttendFlag(YesNoEnum.getValueByBool(AttendanceUtils.checkNeedAttendance(flow.getAttendGroupId(), flow.getAttendDate())));
        return mapper.insert(flow);
    }

    @Override
    public List<AttendanceFlowVo> getAttendRecord(Long userId, Long groupId, String date) {
        List<AttendanceFlowVo> flows = mapper.selectByDate(groupId, userId, date);
        flows.forEach(flow -> {
            flow.setAttendanceGroup(MapstructUtils.convert(AttendanceUtils.getGroup(flow.getAttendGroupId()), AttendanceGroupVo.class));
            flow.setAttendanceClasses(AttendanceUtils.getGroupClass(flow.getAttendGroupId(), flow.getAttendClassesId()));
        });
        return flows;
    }

    @Override
    public Map<String, List<AttendanceFlowVo>> getAttendRecordByMonth(Long userId, Long groupId, String month) {
        List<AttendanceFlowVo> flows = mapper.selectByUserIdAndMonth(groupId, userId, month);
        flows.forEach(flow -> {
            flow.setAttendanceGroup(MapstructUtils.convert(AttendanceUtils.getGroup(flow.getAttendGroupId()), AttendanceGroupVo.class));
            flow.setAttendanceClasses(AttendanceUtils.getGroupClass(flow.getAttendGroupId(), flow.getAttendClassesId()));
        });
        return flows.stream()
            .collect(Collectors.groupingBy(flow -> flow.getAttendDate()));
    }

    @Override
    public AttendanceFlowCountByDayVo attendCountByDay(Long groupId, String date) {
        AttendanceFlowCountByDayVo data = new AttendanceFlowCountByDayVo();
        data.setGroupId(groupId);
        data.setDate(date);
        if (AttendanceUtils.checkNeedAttendance(groupId, date)) {
            data.setExpectedAttendNum(CollUtil.size(AttendanceUtils.getUsersByGroupId(groupId)));
        }

        List<AttendanceFlowVo> flows = mapper.selectByDate(groupId, null, date);
        Map<Long, List<AttendanceFlowVo>> flowMap = flows.stream().collect(Collectors.groupingBy(AttendanceFlowVo::getUserId, LinkedHashMap::new, Collectors.toList()));

        List<AttendanceFlowCountDetailByDayVo> details = new ArrayList<>();
        double workHours = 0;
        for (Map.Entry<Long, List<AttendanceFlowVo>> entry : flowMap.entrySet()) {
            List<AttendanceFlowVo> userFlows = entry.getValue();
            AttendanceFlowCountDetailByDayVo userCount = AttendanceUtils.getUserFlowCountForOneDay(userFlows);
            details.add(userCount);
            if (YesNoEnum.YES.getValue().equals(userCount.getAttendFlag())) {
                data.setAttendNum(data.getAttendNum() + 1);
            }
            workHours += userCount.getWorkHours();
            data.setLateNum(data.getLateNum() + userCount.getLateNum());
            data.setSeriousLateNum(data.getSeriousLateNum() + userCount.getSeriousLateNum());
            data.setEarlyNum(data.getEarlyNum() + userCount.getEarlyNum());
            data.setNoAttendNum(data.getNoAttendNum() + userCount.getNoAttendNum());
            data.setOutsideNum(data.getOutsideNum() + userCount.getOutsideNum());
        }
        data.setAverageWorkHours(data.getExpectedAttendNum() > 0 ? NumberUtil.div(workHours, data.getExpectedAttendNum(), 1) : 0);
        data.setDetails(details);
        return data;
    }

    @Override
    public AttendanceFlowCountByDateRangeVo attendCountByDateRange(Long groupId, String startDate, String endDate) {
        AttendanceFlowCountByDateRangeVo data = new AttendanceFlowCountByDateRangeVo();
        data.setGroupId(groupId);

        List<AttendanceFlowVo> flows = mapper.selectByDateRange(groupId, null, startDate, endDate);
        Map<Long, List<AttendanceFlowVo>> flowMap = flows.stream().collect(Collectors.groupingBy(AttendanceFlowVo::getUserId, LinkedHashMap::new, Collectors.toList()));
        List<AttendanceFlowCountDetailByDateRangeVo> details = new ArrayList<>();
        double workHours = 0;
        int needAttendDays = 0;
        for (Map.Entry<Long, List<AttendanceFlowVo>> entry : flowMap.entrySet()) {
            List<AttendanceFlowVo> userFlows = entry.getValue();
            AttendanceFlowCountDetailByDateRangeVo countDateRange = AttendanceUtils.getUserFlowCountForDateRange(groupId, startDate, endDate, userFlows);
            details.add(countDateRange);

            workHours += countDateRange.getWorkHours();
            data.setLateNum(data.getLateNum() + (countDateRange.getLateNum() > 0 ? 1 : 0));
            data.setSeriousLateNum(data.getSeriousLateNum() + (countDateRange.getSeriousLateNum() > 0 ? 1 : 0));
            data.setEarlyNum(data.getEarlyNum() + (countDateRange.getEarlyNum() > 0 ? 1 : 0));
            data.setNoAttendNum(data.getNoAttendNum() + (countDateRange.getNoAttendNum() > 0 ? 1 : 0));
            data.setOutsideNum(data.getOutsideNum() + (countDateRange.getOutsideNum() > 0 ? 1 : 0));
            data.setNoWorkNum(data.getNoWorkNum() + (countDateRange.getNoAttendDays() > 0 ? 1 : 0));
            needAttendDays += countDateRange.getNeedAttendDays();
        }
        data.setAverageWorkHours(needAttendDays > 0 ? NumberUtil.div(workHours, needAttendDays, 1) : 0);
        data.setDetails(details);

        return data;
    }
}
