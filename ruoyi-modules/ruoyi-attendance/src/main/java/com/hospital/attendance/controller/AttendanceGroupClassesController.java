package com.hospital.attendance.controller;

import com.demo.hospital.attendance.model.AttendanceGroupClasses;
import com.demo.hospital.attendance.service.AttendanceGroupClassesService;
import com.demo.hospital.common.base.controller.BaseController;
import com.demo.hospital.common.base.controller.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 新考勤-考勤班次关联表
 *
 * @author makejava
 * @since 2023-05-21 17:56:38
 */
@RestController
@RequestMapping("/attendanceGroupClasses")
@Api(tags = "排班班次表")
public class AttendanceGroupClassesController extends BaseController {

    @Resource
    private AttendanceGroupClassesService attendanceGroupClassesService;


    /**
     * 新增考勤班次关联
     * @param entity
     * @return
     */
    @PostMapping("/insert")
    @ApiOperation("新增考勤班次关联")
    public Result insert(@RequestBody AttendanceGroupClasses entity) {
        return attendanceGroupClassesService.insert(entity);
    }

    /**
     * 修改关联关系
     * @param entity
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("修改")
    public Result update(@RequestBody AttendanceGroupClasses entity) {
        return attendanceGroupClassesService.update(entity);
    }


}

