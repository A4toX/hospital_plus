package com.hospital.attendance.controller;

import com.hospital.attendance.domain.bo.AttendanceClassesBo;
import com.hospital.attendance.domain.vo.AttendanceClassesVo;
import com.hospital.attendance.service.IAttendanceClassesService;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 新考勤-考勤班次
 *
 * @author yaoyingjie
 */
@RestController
@RequestMapping("/attendanceClasses")
@Validated
@RequiredArgsConstructor
public class AttendanceClassesController extends BaseController {

    private IAttendanceClassesService attendanceClassesService;

    /**
     * 创建考勤班次
     * @param entity
     * @return
     */
    @PostMapping("/insert")
    public R<Void> insert(@RequestBody AttendanceClassesBo entity) {
        return toAjax(attendanceClassesService.insert(entity));
    }


    /**
     * 更新考勤班次信息
     * @param entity
     * @return
     */
    @PostMapping("/update")
    public R<Void> update(@RequestBody AttendanceClassesBo entity) {
        return toAjax(attendanceClassesService.update(entity));
    }


    /**
     * 获取班次列表(分页)
     * @param bo
     * @return
     */
    @GetMapping("/findPage")
    public TableDataInfo<AttendanceClassesVo> findPage(AttendanceClassesBo bo, PageQuery pageQuery) {
        return attendanceClassesService.selectPageList(bo, pageQuery);
    }


    /**
     * 根据获取班次详细信息
     * @param id
     * @return
     */
    @GetMapping("/get")
    public R<AttendanceClassesVo> get(Long id) {
        return R.ok(attendanceClassesService.selectById(id));
    }


    /**
     * 根据医院id获取考勤组列表(用于下拉选择)
     * @param hosId
     * @return
     */
    @GetMapping("/listByHosId")
    public R<List<AttendanceClassesVo>> listByHosId(Long hosId) {
        List<AttendanceClassesVo> list = attendanceClassesService.listByHosId(hosId);
        return R.ok(list);
    }


    /**
     * 删除考勤班次
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public R<Void> delete(@RequestParam("id") Long id) {
        return toAjax(attendanceClassesService.deleteById(id));
    }
}

