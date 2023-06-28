package com.hospital.attendance.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hospital.attendance.domain.AttendReq;
import com.hospital.attendance.domain.AttendanceQrResp;
import com.hospital.attendance.domain.ScanAttendReq;
import com.hospital.attendance.domain.vo.*;
import com.hospital.attendance.service.IAttendanceFlowService;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.common.web.core.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 新考勤/考勤记录
 *
 * @author liguoxian
 */
@RestController
@RequestMapping("/attendanceFlow")
@Validated
@RequiredArgsConstructor
public class AttendanceFlowController extends BaseController {

    private final IAttendanceFlowService attendanceFlowService;

    /**
     * 获取当前登录人所在考勤组
     *
     * @return
     */
    @GetMapping("/getAttendGroup")
    public R<List<AttendanceGroupVo>> getAttendGroup() {
        return R.ok(attendanceFlowService.getAttendGroup());
    }

    /**
     * 获取当前登录人考勤组考勤信息
     *
     * @param groupId 考勤组ID
     * @return
     */
    @GetMapping("/getAttendInfo")
    public R<AttendanceInfoResp> getAttendInfo(Long groupId) {
        AttendanceInfoResp resp = attendanceFlowService.getAttendInfo(groupId);
        return R.ok(resp);
    }

    /**
     * 获取当前登录人管理的考勤组
     *
     * @return
     */
    @GetMapping("/getManagerAttendGroup")
    public R<List<AttendanceGroupVo>> getManagerAttendGroup() {
        return R.ok(attendanceFlowService.getManagerAttendGroup());
    }

    /**
     * 教师获取考勤二维码信息
     *
     * @param groupId 考勤组ID
     * @return
     */
    @GetMapping("/getQrCodeInfo")
    public R<AttendanceQrResp> getQrCodeInfo(Long groupId) {
        AttendanceQrResp resp = attendanceFlowService.getQrCodeInfo(groupId);
        return R.ok(resp);
    }

    /**
     * 定位考勤打卡
     *
     * @param req
     * @return
     */
    @PostMapping("/attend")
    @RepeatSubmit
    public R<Void> attend(AttendReq req) {
        return toAjax(attendanceFlowService.attend(req));
    }

    /**
     * 扫码考勤打卡
     *
     * @param req
     * @return
     */
    @PostMapping("scanAttend")
    @RepeatSubmit
    public R<Void> scanAttend(ScanAttendReq req) {
        return toAjax(attendanceFlowService.scanAttend(req));
    }

    /**
     * 获取当前登录人考勤记录
     *
     * @param groupId 考勤组ID
     * @param date    日期
     * @return
     */
    @GetMapping("/getAttendRecord")
    public R<List<AttendanceFlowVo>> getAttendRecord(Long groupId, String date) {
        List<AttendanceFlowVo> list = attendanceFlowService.getAttendRecord(LoginHelper.getUserId(), groupId, date);
        return R.ok(list);
    }

    /**
     * 获取考勤记录
     *
     * @param userId  用户ID
     * @param groupId 考勤组ID
     * @param date    日期
     * @return
     */
    @GetMapping("/getUserAttendRecord")
    @SaCheckPermission("attendanceFlow:query")
    public R<List<AttendanceFlowVo>> getUserAttendRecord(Long userId, Long groupId, String date) {
        List<AttendanceFlowVo> list = attendanceFlowService.getAttendRecord(userId, groupId, date);
        return R.ok(list);
    }

    /**
     * 获取指定月份考勤记录
     *
     * @param groupId 考勤组ID
     * @param month   月份
     * @return
     */
    @GetMapping("/getAttendRecordByMonth")
    public R<Map<String, List<AttendanceFlowVo>>> getAttendRecordByMonth(Long groupId, String month) {
        Map<String, List<AttendanceFlowVo>> list = attendanceFlowService.getAttendRecordByMonth(LoginHelper.getUserId(), groupId, month);
        return R.ok(list);
    }

    /**
     * 获取指定月份考勤记录
     *
     * @param userId  用户ID，默认当前登录人
     * @param groupId 考勤组ID
     * @param month   月份
     * @return
     */
    @GetMapping("/getUserAttendRecordByMonth")
    @SaCheckPermission("attendanceFlow:query")
    public R<Map<String, List<AttendanceFlowVo>>> getUserAttendRecordByMonth(Long userId, Long groupId, String month) {
        Map<String, List<AttendanceFlowVo>> list = attendanceFlowService.getAttendRecordByMonth(userId, groupId, month);
        return R.ok(list);
    }

    /**
     * 考勤组日统计汇总
     *
     * @param groupId 考勤组ID
     * @param date    日期
     * @return
     */
    @GetMapping("/attendCountByDay")
    @SaCheckPermission("attendanceFlow:count")
    public R<AttendanceFlowCountByDayVo> attendCountByDay(Long groupId, String date) {
        return R.ok(attendanceFlowService.attendCountByDay(groupId, date));
    }

    /**
     * 考勤组日期范围统计汇总
     *
     * @param groupId   考勤组ID
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    @GetMapping("/attendCountByDateRange")
    @SaCheckPermission("attendanceFlow:count")
    public R<AttendanceFlowCountByDateRangeVo> attendCountByDateRange(Long groupId, String startDate, String endDate) {
        return R.ok(attendanceFlowService.attendCountByDateRange(groupId, startDate, endDate));
    }
}

