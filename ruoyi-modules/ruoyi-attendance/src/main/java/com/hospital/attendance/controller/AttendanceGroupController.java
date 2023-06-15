package com.hospital.attendance.controller;

import com.demo.hospital.attendance.model.AttendanceGroup;
import com.demo.hospital.attendance.model.AttendanceGroupPageReq;
import com.demo.hospital.attendance.model.vo.AttendanceGroupVO;
import com.demo.hospital.attendance.service.AttendanceGroupService;
import com.demo.hospital.common.base.controller.BaseController;
import com.demo.hospital.common.base.controller.Result;
import com.demo.hospital.common.page.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

/**
 * 新考勤-考勤组信息
 *
 * @author yaoyingjie
 */
@RestController
@RequestMapping("/attendanceGroup")
@Api(tags = "考勤组信息表")
public class AttendanceGroupController extends BaseController {

    @Resource
    private AttendanceGroupService attendanceGroupService;

    /**
     * 创建考勤组
     * @param entity
     * @return
     */
    @PostMapping("/insert")
    @ApiOperation("创建考勤组")
    public Result insert(@RequestBody AttendanceGroup entity) {
        return attendanceGroupService.insert(entity);
    }


    /**
     * 更新考勤组信息
     * @param entity
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("修改考勤组信息")
    public Result update(@RequestBody AttendanceGroup entity) {
        return attendanceGroupService.update(entity);
    }


    /**
     * 分页查询考勤组信息
     * @param pageReq
     * @return
     */
    @GetMapping("/findPage")
    @ApiOperation("分页查询考勤组信息")
    public Result<Page<AttendanceGroup>> findPage(AttendanceGroupPageReq pageReq) {
        Page<AttendanceGroup> page = attendanceGroupService.findPage(pageReq);
        return Result.success(page);
    }


    /**
     * 根据考勤组id查询考勤组详细信息
     * @param id
     * @return
     */
    @GetMapping("/get")
    @ApiOperation("根据考勤组id查询考勤组详细信息")
    @ApiImplicitParam(name = "id", value = "考勤组主键", required = true, dataTypeClass = Integer.class)
    public AttendanceGroupVO get(Integer id) {
        return attendanceGroupService.get(id);
    }


    /**
     * 删除考勤组
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除")
    @ApiImplicitParam(name = "id", value = "主键", required = true, dataTypeClass = Integer.class)
    public Result delete(@RequestParam("id") Integer id) {
        attendanceGroupService.delete(id);
        return Result.success();
    }
}

