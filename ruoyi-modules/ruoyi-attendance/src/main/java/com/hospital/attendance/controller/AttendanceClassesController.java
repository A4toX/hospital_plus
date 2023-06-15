package com.hospital.attendance.controller;

import com.demo.hospital.attendance.model.AttendanceClasses;
import com.demo.hospital.attendance.model.AttendanceClassesPageReq;
import com.demo.hospital.attendance.service.AttendanceClassesService;
import com.demo.hospital.common.base.controller.BaseController;
import com.demo.hospital.common.base.controller.Result;
import com.demo.hospital.common.page.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.dromara.common.web.core.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 新考勤-考勤班次
 *
 * @author yaoyingjie
 */
@RestController
@RequestMapping("/attendanceClasses")
@Api(tags = "班次设置表")
@Validated
@RequiredArgsConstructor
public class AttendanceClassesController extends BaseController {

    @Resource
    private AttendanceClassesService attendanceClassesService;


    /**
     * 创建考勤班次
     * @param entity
     * @return
     */
    @PostMapping("/insert")
    @ApiOperation("创建考勤班次")
    public Result insert(@RequestBody AttendanceClasses entity) {
        return attendanceClassesService.insert(entity);
    }


    /**
     * 更新考勤班次信息
     * @param entity
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("修改")
    public Result update(@RequestBody AttendanceClasses entity) {
        return attendanceClassesService.update(entity);
    }


    /**
     * 获取班次列表(分页)
     * @param pageReq
     * @return
     */
    @GetMapping("/findPage")
    @ApiOperation("分页查询")
    public Result<Page<AttendanceClasses>> findPage(AttendanceClassesPageReq pageReq) {
        Page<AttendanceClasses> page = attendanceClassesService.findPage(pageReq);
        return Result.success(page);
    }


    /**
     * 根据获取班次详细信息
     * @param id
     * @return
     */
    @GetMapping("/get")
    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "主键", required = true, dataTypeClass = Integer.class)
    public AttendanceClasses get(Integer id) {
        return attendanceClassesService.get(id);
    }


    /**
     * 根据医院id获取考勤组列表(用于下拉选择)
     * @param hosId
     * @return
     */
    @GetMapping("/listByHosId")
    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "hosId", value = "医院id", required = true, dataTypeClass = Integer.class)
    public Result<List<AttendanceClasses>> listByHosId(Integer hosId) {
        List<AttendanceClasses> list = attendanceClassesService.listByHosId(hosId);
        return Result.success(list);
    }


    /**
     * 删除考勤班次
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除")
    @ApiImplicitParam(name = "id", value = "主键", required = true, dataTypeClass = Integer.class)
    public Result delete(@RequestParam("id") Integer id) {
        return attendanceClassesService.delete(id);
    }
}

