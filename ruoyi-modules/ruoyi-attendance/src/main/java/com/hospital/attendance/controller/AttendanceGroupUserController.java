package com.hospital.attendance.controller;

import com.demo.hospital.attendance.model.AttendanceGroupUser;
import com.demo.hospital.attendance.model.AttendanceGroupUserPageReq;
import com.demo.hospital.attendance.model.vo.attendUser.AddAttendanceUserVO;
import com.demo.hospital.attendance.model.vo.attendUser.AttendanceUserReqVO;
import com.demo.hospital.attendance.model.vo.attendUser.AttendanceUserRespVO;
import com.demo.hospital.attendance.service.AttendanceGroupUserService;
import com.demo.hospital.common.base.controller.BaseController;
import com.demo.hospital.common.base.controller.Result;
import com.demo.hospital.common.page.Page;
import com.demo.hospital.user.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import java.util.List;

/**
 * 新考勤-考勤参与人员
 *
 * @author yaoyingjie
 */
@RestController
@RequestMapping("/attendanceGroupUser")
@Api(tags = "考勤参与人员")
public class AttendanceGroupUserController extends BaseController {

    @Resource
    private AttendanceGroupUserService attendanceGroupUserService;

    @Resource
    private StudentService studentService;

    /**
     * 分页查询
     * @param pageReq
     * @return
     */
    @GetMapping("/findPage")
    @ApiOperation("分页查询")
    public Result<Page<AttendanceGroupUser>> findPage(AttendanceGroupUserPageReq pageReq) {
        Page<AttendanceGroupUser> page = attendanceGroupUserService.findPage(pageReq);
        return Result.success(page);
    }

    /**
     * 获取参与考勤的人员列表
     * @return
     */
    @GetMapping("/listByGroupId")
    @ApiOperation("获取考勤组下的考勤人员列表")
    public Result<List<AttendanceUserRespVO>> listByGroupId(AttendanceUserReqVO reqVO) {
        List<AttendanceUserRespVO> list = attendanceGroupUserService.listByGroupId(reqVO);
        return Result.success(list);
    }


    /**
     * 新增考勤人员
     * @param entity
     * @return
     */
    @PostMapping("/insert")
    @ApiOperation("管理考勤组下的考勤人员")
    public Result insert(@RequestBody AddAttendanceUserVO entity) {
        return attendanceGroupUserService.insert(entity);
    }

    /**
     * 获取医院下所有学员
     * @return
     */
    @GetMapping("/listAllStudentByhosId")
    @ApiOperation("获取医院下所有的学员")
    public Result<List<AttendanceUserRespVO>> listAllStudentByhosId(AttendanceUserReqVO reqVO) {
        List<AttendanceUserRespVO> list = studentService.listAllStudentByhosId(reqVO);
        return Result.success(list);
    }


}


