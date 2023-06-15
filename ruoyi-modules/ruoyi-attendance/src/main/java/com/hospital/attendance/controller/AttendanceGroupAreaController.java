package com.hospital.attendance.controller;

import com.demo.hospital.attendance.model.AttendanceGroupArea;
import com.demo.hospital.attendance.service.AttendanceGroupAreaService;
import com.demo.hospital.common.base.controller.BaseController;
import com.demo.hospital.common.base.controller.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import java.util.List;

/**
 * 新考勤-考勤组地点
 *
 * @author yaoyingjie
 */
@RestController
@RequestMapping("/attendanceGroupArea")
@Api(tags = "考勤地点表")
public class AttendanceGroupAreaController extends BaseController {

    @Resource
    private AttendanceGroupAreaService attendanceGroupAreaService;

    /**
     * 新增考勤组下的考勤地点
     * @param entity
     * @return
     */
    @PostMapping("/insert")
    @ApiOperation("新增考勤组下的考勤地点")
    public Result insert(@RequestBody AttendanceGroupArea entity) {
        return attendanceGroupAreaService.insert(entity);
    }


    /**
     * 更新考勤地点信息
     * @param entity
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("修改")
    public Result update(@RequestBody AttendanceGroupArea entity) {
        return attendanceGroupAreaService.update(entity);
    }


    /**
     * 获取考勤地点详细信息
     * @param id
     * @return
     */
    @GetMapping("/get")
    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "主键", required = true, dataTypeClass = Integer.class)
    public AttendanceGroupArea get(Integer id) {
        return attendanceGroupAreaService.get(id);
    }


    /**
     * 删除考勤地点
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除")
    @ApiImplicitParam(name = "id", value = "主键", required = true, dataTypeClass = Integer.class)
    public Result delete(@RequestParam("id") Integer id) {
        return attendanceGroupAreaService.delete(id);
    }


    /**
     * 获取历史考勤地点
     * @param hosId
     * @return
     */
    @GetMapping("/getHistoryByHosId")
    @ApiOperation("获取历史考勤地点")
    @ApiImplicitParam(name = "hosId", value = "医院id", required = true, dataTypeClass = Integer.class)
    public Result<List<AttendanceGroupArea>> getHistoryByHosId(Integer hosId) {
        List<AttendanceGroupArea> list = attendanceGroupAreaService.getHistoryByHosId(hosId);
        return Result.success(list);
    }
}

