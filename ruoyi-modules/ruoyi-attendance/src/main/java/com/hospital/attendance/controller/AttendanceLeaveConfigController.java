package com.hospital.attendance.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import com.hospital.attendance.domain.bo.AttendanceLeaveConfigBo;
import com.hospital.attendance.domain.vo.AttendanceLeaveConfigVo;
import com.hospital.attendance.service.IAttendanceLeaveConfigService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 新考勤/请假配置
 *
 * @author liguoxian
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/attendance/attendanceLeaveConfig")
public class AttendanceLeaveConfigController extends BaseController {

    private final IAttendanceLeaveConfigService attendanceLeaveConfigService;

    /**
     * 分页查询请假配置
     * @return
     */
    @SaCheckPermission("attendanceLeaveConfig:list")
    @GetMapping("/list")
    public TableDataInfo<AttendanceLeaveConfigVo> list(AttendanceLeaveConfigBo bo, PageQuery pageQuery) {
        return attendanceLeaveConfigService.selectPageList(bo, pageQuery);
    }

    /**
     * 根据ID获取请假配置
     * @param id 数据ID
     * @return
     */
    @SaCheckPermission("attendanceLeaveConfig:query")
    @GetMapping(value = "/{id}")
    public R<AttendanceLeaveConfigVo> getInfo(@PathVariable Long id) {
        return R.ok(attendanceLeaveConfigService.selectById(id));
    }

    /**
     * 新增请假配置
     * @return
     */
    @SaCheckPermission("attendanceLeaveConfig:add")
    @Log(title = "请假配置管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@Validated @RequestBody AttendanceLeaveConfigBo bo) {
        return toAjax(attendanceLeaveConfigService.insert(bo));
    }

    /**
     * 修改请假配置
     * @return
     */
    @SaCheckPermission("attendanceLeaveConfig:edit")
    @Log(title = "请假配置管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@Validated @RequestBody AttendanceLeaveConfigBo bo) {
        return toAjax(attendanceLeaveConfigService.update(bo));
    }

    /**
     * 删除请假配置
     * @param ids 数据ID字符串集合
     * @return
     */
    @SaCheckPermission("attendanceLeaveConfig:remove")
    @Log(title = "请假配置管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@PathVariable Long[] ids) {
        return toAjax(attendanceLeaveConfigService.deleteByIds(ids));
    }

    /**
     * 修改请假配置状态
     * @param bo
     * @return
     */
    @SaCheckPermission("attendanceLeaveConfig:edit")
    @Log(title = "请假配置管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public R<Void> changeStatus(@RequestBody AttendanceLeaveConfigBo bo) {
        return toAjax(attendanceLeaveConfigService.updateStatus(bo.getId(), bo.getStatus()));
    }
}

