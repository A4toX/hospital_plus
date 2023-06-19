package com.hospital.attendance.controller;

import com.hospital.attendance.domain.bo.AttendanceGroupAreaBo;
import com.hospital.attendance.domain.vo.AttendanceGroupAreaVo;
import com.hospital.attendance.service.IAttendanceGroupAreaService;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.web.core.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 新考勤/考勤组地点
 *
 * @author yaoyingjie
 */
@RestController
@RequestMapping("/attendanceGroupArea")
@Validated
@RequiredArgsConstructor
public class AttendanceGroupAreaController extends BaseController {

    private final IAttendanceGroupAreaService attendanceGroupAreaService;

    /**
     * 新增考勤组下的考勤地点
     *
     * @param entity
     * @return
     */
    @PostMapping("/insert")
    public R<Void> insert(@RequestBody AttendanceGroupAreaBo entity) {
        return toAjax(attendanceGroupAreaService.insert(entity));
    }


    /**
     * 更新考勤地点信息
     *
     * @param entity
     * @return
     */
    @PostMapping("/update")
    public R<Void> update(@RequestBody AttendanceGroupAreaBo entity) {
        return toAjax(attendanceGroupAreaService.update(entity));
    }


    /**
     * 获取考勤地点详细信息
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public R<AttendanceGroupAreaVo> get(Long id) {
        return R.ok(attendanceGroupAreaService.selectById(id));
    }


    /**
     * 删除考勤地点
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public R<Void> delete(@RequestParam("id") Long id) {
        return toAjax(attendanceGroupAreaService.deleteById(id));
    }


    /**
     * 获取历史考勤地点
     * @return
     */
    @GetMapping("/getHistoryAreas")
    public R<List<AttendanceGroupAreaVo>> getHistoryAreas() {
        List<AttendanceGroupAreaVo> list = attendanceGroupAreaService.getHistoryAreas();
        return R.ok(list);
    }
}

