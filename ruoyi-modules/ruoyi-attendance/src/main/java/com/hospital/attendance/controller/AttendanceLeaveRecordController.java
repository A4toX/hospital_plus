package com.hospital.attendance.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import com.hospital.attendance.domain.bo.AttendanceLeaveRecordBo;
import com.hospital.attendance.domain.vo.AttendanceLeaveRecordVo;
import com.hospital.attendance.service.IAttendanceLeaveRecordService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 请假记录
 *
 * @author liguoxian
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/attendance/attendanceLeaveRecord")
public class AttendanceLeaveRecordController extends BaseController {

    private final IAttendanceLeaveRecordService attendanceLeaveRecordService;

    /**
     * 分页查询请假记录
     * @return
     */
    @SaCheckPermission("attendanceLeaveRecord:list")
    @GetMapping("/list")
    public TableDataInfo<AttendanceLeaveRecordVo> list(AttendanceLeaveRecordBo bo, PageQuery pageQuery) {
        return attendanceLeaveRecordService.selectPageList(bo, pageQuery);
    }

    /**
     * 根据ID获取请假记录
     * @param id 数据ID
     * @return
     */
    @SaCheckPermission("attendanceLeaveRecord:query")
    @GetMapping(value = "/{id}")
    public R<AttendanceLeaveRecordVo> getInfo(@PathVariable Long id) {
        return R.ok(attendanceLeaveRecordService.selectById(id));
    }

    /**
     * 新增请假记录
     * @return
     */
    @SaCheckPermission("attendanceLeaveRecord:add")
    @Log(title = "请假记录管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@Validated @RequestBody AttendanceLeaveRecordBo bo) {
        return toAjax(attendanceLeaveRecordService.insert(bo));
    }

    /**
     * 修改请假记录
     * @return
     */
    @SaCheckPermission("attendanceLeaveRecord:edit")
    @Log(title = "请假记录管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@Validated @RequestBody AttendanceLeaveRecordBo bo) {
        return toAjax(attendanceLeaveRecordService.update(bo));
    }

    /**
     * 删除请假记录
     * @param ids 数据ID字符串集合
     * @return
     */
    @SaCheckPermission("attendanceLeaveRecord:remove")
    @Log(title = "请假记录管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@PathVariable Long[] ids) {
        return toAjax(attendanceLeaveRecordService.deleteByIds(ids));
    }
}

