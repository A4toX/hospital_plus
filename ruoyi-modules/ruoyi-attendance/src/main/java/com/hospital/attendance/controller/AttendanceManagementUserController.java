package com.hospital.attendance.controller;

import com.demo.hospital.attendance.model.AttendanceManagementUser;
import com.demo.hospital.attendance.model.AttendanceManagementUserPageReq;
import com.demo.hospital.attendance.model.vo.attendUser.*;
import com.demo.hospital.attendance.service.AttendanceManagementUserService;
import com.demo.hospital.common.base.controller.BaseController;
import com.demo.hospital.common.base.controller.Result;
import com.demo.hospital.common.page.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import java.util.List;

/**
 * 新考勤-考勤组负责人
 *
 * @author makejava
 * @since 2023-05-21 19:29:23
 */
@RestController
@RequestMapping("/attendanceManagementUser")
@Api(tags = "考勤组负责人")
public class AttendanceManagementUserController extends BaseController {

    @Resource
    private AttendanceManagementUserService attendanceManagementUserService;

    @GetMapping("/findPage")
    @ApiOperation("分页查询")
    public Result<Page<AttendanceManagementUser>> findPage(AttendanceManagementUserPageReq pageReq) {
        Page<AttendanceManagementUser> page = attendanceManagementUserService.findPage(pageReq);
        return Result.success(page);
    }

    /**
     * 新增考勤组负责人
     * @param entity
     * @return
     */
    @PostMapping("/insert")
    @ApiOperation("新增考勤组负责人")
    public Result insert(@RequestBody AddAttendanceUserVO entity) {
        return attendanceManagementUserService.insert(entity);
    }


    /**
     * 获取参与考勤的人员列表
     * @return
     */
    @GetMapping("/listByGroupId")
    @ApiOperation("获取考勤组管理员列表")
    public Result<List<AttendanceMUserRespVO>> listByGroupId(AttendanceMUserReqVO reqVO) {
        List<AttendanceMUserRespVO> list = attendanceManagementUserService.listByGroupId(reqVO);
        return Result.success(list);
    }


    /**
     * 获取医院下所有员工
     * @param reqVO
     * @return
     */
    @GetMapping("/listAllStaffByHosId")
    @ApiOperation("获取医院下所有员工")
    public Result<List<AttendanceMUserRespVO>> listAllStaffByHosId(AttendanceMUserReqVO reqVO) {
        List<AttendanceMUserRespVO> list = attendanceManagementUserService.listAllStaffByHosId(reqVO);
        return Result.success(list);
    }
}

