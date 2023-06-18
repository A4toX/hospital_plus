package com.hospital.attendance.controller;

import com.hospital.attendance.api.StudentAPi;
import com.hospital.attendance.domain.bo.AttendanceGroupUserBo;
import com.hospital.attendance.domain.vo.AttendanceGroupUserVo;
import com.hospital.attendance.domain.vo.attendUser.AttendanceUserReqVO;
import com.hospital.attendance.domain.vo.attendUser.AttendanceUserRespVO;
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
 * 新考勤-考勤参与人员
 *
 * @author yaoyingjie
 */
@RestController
@RequestMapping("/attendanceGroupUser")
@Validated
@RequiredArgsConstructor
public class AttendanceGroupUserController extends BaseController {

    private IAttendanceGroupUserService attendanceGroupUserService;
    private StudentAPi studentAPi;

    /**
     * 分页查询
     *
     * @param bo
     * @return
     */
    @GetMapping("/findPage")
    public TableDataInfo<AttendanceGroupUserVo> findPage(AttendanceGroupUserBo bo, PageQuery pageQuery) {
        return attendanceGroupUserService.selectPageList(bo, pageQuery);
    }

    /**
     * 获取参与考勤的人员列表
     *
     * @return
     */
    @GetMapping("/listByGroupId")
    public R<List<AttendanceUserRespVO>> listByGroupId(AttendanceUserReqVO reqVO) {
        List<AttendanceUserRespVO> list = attendanceGroupUserService.listByGroupId(reqVO);
        return R.ok(list);
    }


    /**
     * 新增考勤人员
     *
     * @param entity
     * @return
     */
    @PostMapping("/insert")
    public R insert(@RequestBody AttendanceGroupUserBo entity) {
        return toAjax(attendanceGroupUserService.insert(entity));
    }

    /**
     * 获取医院下所有学员
     *
     * @return
     */
    @GetMapping("/listAllStudentByhosId")
    public R<List<AttendanceUserRespVO>> listAllStudentByhosId(AttendanceUserReqVO reqVO) {
        List<AttendanceUserRespVO> list = studentAPi.listAllStudentByhosId(reqVO);
        return R.ok(list);
    }
}


