package com.hospital.attendance.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hospital.attendance.domain.bo.AttendanceClassesBo;
import com.hospital.attendance.domain.vo.AttendanceClassesVo;
import com.hospital.attendance.service.IAttendanceClassesService;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 新考勤/考勤班次
 *
 * @author yaoyingjie
 */
@RestController
@RequestMapping("/attendanceClasses")
@Validated
@RequiredArgsConstructor
public class AttendanceClassesController extends BaseController {

    private final IAttendanceClassesService attendanceClassesService;

    /**
     * 创建考勤班次
     * @param entity
     * @return
     */
    @PostMapping("/insert")
    @SaCheckPermission("attendanceClasses:add")
    @Log(title = "考勤班次管理", businessType = BusinessType.INSERT)
    public R<Void> insert(@RequestBody AttendanceClassesBo entity) {
        return toAjax(attendanceClassesService.insert(entity));
    }


    /**
     * 更新考勤班次信息
     * @param entity
     * @return
     */
    @PostMapping("/update")
    @SaCheckPermission("attendanceClasses:edit")
    @Log(title = "考勤班次管理", businessType = BusinessType.UPDATE)
    public R<Void> update(@RequestBody AttendanceClassesBo entity) {
        return toAjax(attendanceClassesService.update(entity));
    }


    /**
     * 获取班次列表(分页)
     * @param bo
     * @return
     */
    @GetMapping("/findPage")
    @SaCheckPermission("attendanceClasses:list")
    public TableDataInfo<AttendanceClassesVo> findPage(AttendanceClassesBo bo, PageQuery pageQuery) {
        return attendanceClassesService.selectPageList(bo, pageQuery);
    }


    /**
     * 根据获取班次详细信息
     * @param id
     * @return
     */
    @GetMapping("/get")
    @SaCheckPermission("attendanceClasses:query")
    public R<AttendanceClassesVo> get(Long id) {
        return R.ok(attendanceClassesService.selectById(id));
    }


    /**
     * 获取考勤班次列表(用于下拉选择)
     * @return
     */
    @GetMapping("/listAll")
    public R<List<AttendanceClassesVo>> listAll() {
        List<AttendanceClassesVo> list = attendanceClassesService.listAll();
        return R.ok(list);
    }


    /**
     * 删除考勤班次
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    @SaCheckPermission("attendanceClasses:delete")
    @Log(title = "考勤班次管理", businessType = BusinessType.DELETE)
    public R<Void> delete(@RequestParam("id") Long id) {
        return toAjax(attendanceClassesService.deleteById(id));
    }
}

