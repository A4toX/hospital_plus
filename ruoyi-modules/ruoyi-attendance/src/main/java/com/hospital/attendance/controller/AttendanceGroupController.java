package com.hospital.attendance.controller;

import com.hospital.attendance.domain.bo.AttendanceGroupBo;
import com.hospital.attendance.domain.vo.AttendanceGroupVo;
import com.hospital.attendance.service.IAttendanceGroupService;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 新考勤-考勤组信息
 *
 * @author yaoyingjie
 */
@RestController
@RequestMapping("/attendanceGroup")
@Validated
@RequiredArgsConstructor
public class AttendanceGroupController extends BaseController {

    private IAttendanceGroupService attendanceGroupService;

    /**
     * 创建考勤组
     * @param entity
     * @return
     */
    @PostMapping("/insert")
    public R<Void> insert(@RequestBody AttendanceGroupBo entity) {
        return toAjax(attendanceGroupService.insert(entity));
    }


    /**
     * 更新考勤组信息
     * @param entity
     * @return
     */
    @PostMapping("/update")
    public R<Void> update(@RequestBody AttendanceGroupBo entity) {
        return toAjax(attendanceGroupService.update(entity));
    }


    /**
     * 分页查询考勤组信息
     * @param bo
     * @return
     */
    @GetMapping("/findPage")
    public TableDataInfo<AttendanceGroupVo> findPage(AttendanceGroupBo bo, PageQuery pageQuery) {
        return attendanceGroupService.selectPageList(bo, pageQuery);
    }


    /**
     * 根据考勤组id查询考勤组详细信息
     * @param id
     * @return
     */
    @GetMapping("/get")
    public R<AttendanceGroupVo> get(Long id) {
        return R.ok(attendanceGroupService.selectById(id));
    }


    /**
     * 删除考勤组
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public R<Void> delete(@RequestParam("id") Long id) {
        return toAjax(attendanceGroupService.deleteById(id));
    }
}

