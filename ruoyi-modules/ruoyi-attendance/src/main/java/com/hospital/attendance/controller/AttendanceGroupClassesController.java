package com.hospital.attendance.controller;

import com.hospital.attendance.domain.bo.AttendanceGroupClassesBo;
import com.hospital.attendance.service.IAttendanceGroupClassesService;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.web.core.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 新考勤-考勤班次关联表
 *
 * @author makejava
 * @since 2023-05-21 17:56:38
 */
@RestController
@RequestMapping("/attendanceGroupClasses")
@Validated
@RequiredArgsConstructor
public class AttendanceGroupClassesController extends BaseController {

    private IAttendanceGroupClassesService attendanceGroupClassesService;


    /**
     * 新增考勤班次关联
     * @param entity
     * @return
     */
    @PostMapping("/insert")
    public R<Void> insert(@RequestBody AttendanceGroupClassesBo entity) {
        return toAjax(attendanceGroupClassesService.insert(entity));
    }

    /**
     * 修改关联关系
     * @param entity
     * @return
     */
    @PostMapping("/update")
    public R<Void> update(@RequestBody AttendanceGroupClassesBo entity) {
        return toAjax(attendanceGroupClassesService.update(entity));
    }
}

