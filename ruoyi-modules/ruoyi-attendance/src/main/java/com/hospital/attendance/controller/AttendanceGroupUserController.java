package com.hospital.attendance.controller;

import com.hospital.attendance.domain.bo.AttendanceGroupUserBo;
import com.hospital.attendance.domain.vo.AttendanceGroupUserVo;
import com.hospital.attendance.domain.vo.attendUser.AddAttendanceUserVo;
import com.hospital.attendance.domain.vo.attendUser.GroupStudentReqVo;
import com.hospital.attendance.domain.vo.attendUser.AttendanceUserRespVo;
import com.hospital.attendance.domain.vo.attendUser.StudentReqVo;
import com.hospital.attendance.service.IAttendanceGroupUserService;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 新考勤/考勤参与人员
 *
 * @author yaoyingjie
 */
@RestController
@RequestMapping("/attendanceGroupUser")
@Validated
@RequiredArgsConstructor
public class AttendanceGroupUserController extends BaseController {

    private final IAttendanceGroupUserService attendanceGroupUserService;

    /**
     * 分页查询
     *
     * @param bo
     * @return
     */
    @GetMapping("/findPage")
    public TableDataInfo<AttendanceGroupUserVo> selectPageList(AttendanceGroupUserBo bo, PageQuery pageQuery) {
        return attendanceGroupUserService.selectPageList(bo, pageQuery);
    }

    /**
     * 获取参与考勤的人员列表
     *
     * @return
     */
    @GetMapping("/listByGroupId")
    public R<List<AttendanceUserRespVo>> listByGroupId(GroupStudentReqVo reqVO) {
        List<AttendanceUserRespVo> list = attendanceGroupUserService.listByGroupId(reqVO);
        return R.ok(list);
    }


    /**
     * 新增考勤人员
     *
     * @param entity
     * @return
     */
    @PostMapping("/insert")
    public R<Void> insert(@RequestBody AddAttendanceUserVo entity) {
        attendanceGroupUserService.addUser(entity);
        return R.ok();
    }

    /**
     * 获取所有学员
     *
     * @return
     */
    @GetMapping("/listStudent")
    public R<List<AttendanceUserRespVo>> listStudent(StudentReqVo reqVO) {
        List<AttendanceUserRespVo> list = attendanceGroupUserService.listStudent(reqVO);
        return R.ok(list);
    }
}


