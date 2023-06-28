package com.hospital.attendance.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import com.hospital.attendance.domain.bo.AttendanceHolidayConfigBo;
import com.hospital.attendance.domain.vo.AttendanceHolidayConfigVo;
import com.hospital.attendance.service.IAttendanceHolidayConfigService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 节假日配置
 *
 * @author liguoxian
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/attendance/attendanceHolidayConfig")
public class AttendanceHolidayConfigController extends BaseController {

    private final IAttendanceHolidayConfigService attendanceHolidayConfigService;

    /**
     * 分页查询节假日配置
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo<AttendanceHolidayConfigVo> list(AttendanceHolidayConfigBo bo, PageQuery pageQuery) {
        return attendanceHolidayConfigService.selectPageList(bo, pageQuery);
    }

    /**
     * 根据ID获取节假日配置
     * @param id 数据ID
     * @return
     */
    @SaCheckPermission("attendanceHolidayConfig:query")
    @GetMapping(value = "/{id}")
    public R<AttendanceHolidayConfigVo> getInfo(@PathVariable Long id) {
        return R.ok(attendanceHolidayConfigService.selectById(id));
    }

    /**
     * 新增节假日配置
     * @return
     */
    @SaCheckPermission("attendanceHolidayConfig:add")
    @Log(title = "节假日配置管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@Validated @RequestBody AttendanceHolidayConfigBo bo) {
        return toAjax(attendanceHolidayConfigService.insert(bo));
    }

    /**
     * 修改节假日配置
     * @return
     */
    @SaCheckPermission("attendanceHolidayConfig:edit")
    @Log(title = "节假日配置管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@Validated @RequestBody AttendanceHolidayConfigBo bo) {
        return toAjax(attendanceHolidayConfigService.update(bo));
    }

    /**
     * 删除节假日配置
     * @param ids 数据ID字符串集合
     * @return
     */
    @SaCheckPermission("attendanceHolidayConfig:remove")
    @Log(title = "节假日配置管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@PathVariable Long[] ids) {
        return toAjax(attendanceHolidayConfigService.deleteByIds(ids));
    }
}

