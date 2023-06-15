package com.hospital.attendance.controller;

import com.demo.hospital.attendance.model.*;
import com.demo.hospital.attendance.model.vo.AttendanceFlowCountByDateRangeVO;
import com.demo.hospital.attendance.model.vo.AttendanceFlowCountByDayVO;
import com.demo.hospital.attendance.service.AttendanceFlowService;
import com.demo.hospital.common.base.controller.BaseController;
import com.demo.hospital.common.base.controller.Result;
import com.demo.hospital.common.security.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 考勤记录表
 *
 * @author liguoxian
 */
@RestController
@RequestMapping("/attendanceFlow")
@Api(tags = "考勤记录表")
public class AttendanceFlowController extends BaseController {

    @Resource
    private AttendanceFlowService attendanceFlowService;

    /**
     * 获取当前登录人所在考勤组
     * @return
     */
    @GetMapping("/getAttendGroup")
    @ApiOperation("获取当前登录人所在考勤组")
    public Result<List<AttendanceGroup>> getAttendGroup() {
        return Result.success(attendanceFlowService.getAttendGroup());
    }

    /**
     * 获取当前登录人考勤组考勤信息
     * @param groupId 考勤组ID
     * @return
     */
    @GetMapping("/getAttendInfo")
    @ApiOperation("获取当前登录人考勤组考勤信息")
    @ApiImplicitParam(name = "groupId", value = "考勤组ID", required = true, dataTypeClass = Integer.class)
    public Result<AttendanceInfoResp> getAttendInfo(Integer groupId) {
        AttendanceInfoResp resp = attendanceFlowService.getAttendInfo(groupId);
        return Result.success(resp);
    }

    /**
     * 获取当前登录人管理的考勤组
     * @return
     */
    @GetMapping("/getManagerAttendGroup")
    @ApiOperation("获取当前登录人管理的考勤组")
    public Result<List<AttendanceGroup>> getManagerAttendGroup() {
        return Result.success(attendanceFlowService.getManagerAttendGroup());
    }

    /**
     * 教师获取考勤二维码信息
     * @param groupId 考勤组ID
     * @return
     */
    @GetMapping("/getQrCodeInfo")
    @ApiOperation("教师获取考勤二维码信息")
    public Result<AttendanceQrResp> getQrCodeInfo(Integer groupId) {
        AttendanceQrResp resp = attendanceFlowService.getQrCodeInfo(groupId);
        return Result.success(resp);
    }

    /**
     * 定位考勤打卡
     * @param req
     * @return
     */
    @PostMapping("/attend")
    @ApiOperation("定位考勤打卡")
    public Result<Boolean> attend(AttendReq req) {
        return Result.of(attendanceFlowService.attend(req));
    }

    /**
     * 扫码考勤打卡
     * @param req
     * @return
     */
    @PostMapping("scanAttend")
    @ApiOperation("扫码考勤打卡")
    public Result<Boolean> scanAttend(ScanAttendReq req) {
        return Result.of(attendanceFlowService.scanAttend(req));
    }

    /**
     * 获取考勤记录
     * @param userId 用户ID，默认当前登录人
     * @param groupId 考勤组ID
     * @param date 日期
     * @return
     */
    @GetMapping("/getAttendRecord")
    @ApiOperation("获取考勤记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID，默认当前登录人", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "groupId", value = "考勤组ID", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "date", value = "日期", example = "2022-01-01", required = true, dataTypeClass = String.class)
    })
    public Result<List<AttendanceFlow>> getAttendRecord(Integer userId, Integer groupId, String date) {
        if(userId == null) {
            userId = UserUtil.getCurrentUserId();
        }
        List<AttendanceFlow> list = attendanceFlowService.getAttendRecord(userId, groupId, date);
        return Result.success(list);
    }

    /**
     * 获取指定月份考勤记录
     * @param userId 用户ID，默认当前登录人
     * @param groupId 考勤组ID
     * @param month 月份
     * @return
     */
    @GetMapping("/getAttendRecordByMonth")
    @ApiOperation("获取指定月份考勤记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID，默认当前登录人", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "groupId", value = "考勤组ID", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "month", value = "月份", example = "2022-01", required = true, dataTypeClass = String.class)
    })
    public Result<Map<String, List<AttendanceFlow>>> getAttendRecordByMonth(Integer userId, Integer groupId, String month) {
        if(userId == null) {
            userId = UserUtil.getCurrentUserId();
        }
        Map<String, List<AttendanceFlow>> list = attendanceFlowService.getAttendRecordByMonth(userId, groupId, month);
        return Result.success(list);
    }

    /**
     * 考勤组日统计汇总
     * @param groupId 考勤组ID
     * @param date 日期
     * @return
     */
    @GetMapping("/attendCountByDay")
    @ApiOperation("考勤组日统计汇总")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "考勤组ID", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "date", value = "日期", example = "2022-01-01", required = true, dataTypeClass = String.class)
    })
    public Result<AttendanceFlowCountByDayVO> attendCountByDay(Integer groupId, String date) {
        return Result.success(attendanceFlowService.attendCountByDay(groupId, date));
    }

    /**
     * 考勤组日期范围统计汇总
     * @param groupId 考勤组ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    @GetMapping("/attendCountByDateRange")
    @ApiOperation("考勤组日期范围统计汇总")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "考勤组ID", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "startDate", value = "开始日期", example = "2022-01-01", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "endDate", value = "结束日期", example = "2022-01-07", required = true, dataTypeClass = String.class)
    })
    public Result<AttendanceFlowCountByDateRangeVO> attendCountByDateRange(Integer groupId, String startDate, String endDate) {
        return Result.success(attendanceFlowService.attendCountByDateRange(groupId, startDate, endDate));
    }
}

