package com.hospital.attendance.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import com.hospital.attendance.domain.bo.AttendanceFillRecordBo;
import com.hospital.attendance.domain.vo.AttendanceFillRecordVo;
import com.hospital.attendance.service.IAttendanceFillRecordService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 新考勤/补卡记录
 *
 * @author liguoxian
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/attendance/attendanceFillRecord")
public class AttendanceFillRecordController extends BaseController {

    private final IAttendanceFillRecordService attendanceFillRecordService;

    /**
     * 分页查询补卡记录
     * @return
     */
    @SaCheckPermission("attendanceFillRecord:list")
    @GetMapping("/list")
    public TableDataInfo<AttendanceFillRecordVo> list(AttendanceFillRecordBo bo, PageQuery pageQuery) {
        return attendanceFillRecordService.selectPageList(bo, pageQuery);
    }

    /**
     * 根据ID获取补卡记录
     * @param id 数据ID
     * @return
     */
    @SaCheckPermission("attendanceFillRecord:query")
    @GetMapping(value = "/{id}")
    public R<AttendanceFillRecordVo> getInfo(@PathVariable Long id) {
        return R.ok(attendanceFillRecordService.selectById(id));
    }

    /**
     * 新增补卡记录
     * @return
     */
    @SaCheckPermission("attendanceFillRecord:add")
    @Log(title = "补卡记录管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@Validated @RequestBody AttendanceFillRecordBo bo) {
        return toAjax(attendanceFillRecordService.insert(bo));
    }

    /**
     * 修改补卡记录
     * @return
     */
    @SaCheckPermission("attendanceFillRecord:edit")
    @Log(title = "补卡记录管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@Validated @RequestBody AttendanceFillRecordBo bo) {
        return toAjax(attendanceFillRecordService.update(bo));
    }

    /**
     * 删除补卡记录
     * @param ids 数据ID字符串集合
     * @return
     */
    @SaCheckPermission("attendanceFillRecord:remove")
    @Log(title = "补卡记录管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@PathVariable Long[] ids) {
        return toAjax(attendanceFillRecordService.deleteByIds(ids));
    }
}

