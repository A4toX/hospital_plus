package com.hospital.attendance.controller;

import com.hospital.attendance.domain.bo.AttendanceManagementUserBo;
import com.hospital.attendance.domain.vo.AttendanceManagementUserVo;
import com.hospital.attendance.domain.vo.attendUser.AddAttendanceUserVo;
import com.hospital.attendance.domain.vo.attendUser.AttendanceMUserReqVO;
import com.hospital.attendance.domain.vo.attendUser.AttendanceMUserRespVo;
import com.hospital.attendance.service.IAttendanceManagementUserService;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 新考勤-考勤组负责人
 *
 * @author makejava
 * @since 2023-05-21 19:29:23
 */
@RestController
@RequestMapping("/attendanceManagementUser")
@Validated
@RequiredArgsConstructor
public class AttendanceManagementUserController extends BaseController {

    private IAttendanceManagementUserService attendanceManagementUserService;

    @GetMapping("/findPage")
    public TableDataInfo<AttendanceManagementUserVo> findPage(AttendanceManagementUserBo bo, PageQuery pageQuery) {
        return attendanceManagementUserService.selectPageList(bo, pageQuery);
    }

    /**
     * 新增考勤组负责人
     * @param entity
     * @return
     */
    @PostMapping("/insert")
    public R<Void> insert(@RequestBody AddAttendanceUserVo entity) {
        attendanceManagementUserService.addUser(entity);
        return R.ok();
    }


    /**
     * 获取参与考勤的人员列表
     * @return
     */
    @GetMapping("/listByGroupId")
    public R<List<AttendanceMUserRespVo>> listByGroupId(AttendanceMUserReqVO reqVO) {
        List<AttendanceMUserRespVo> list = attendanceManagementUserService.listByGroupId(reqVO);
        return R.ok(list);
    }


    /**
     * 获取医院下所有员工
     * @param reqVO
     * @return
     */
    @GetMapping("/listAllStaffByHosId")
    public R<List<AttendanceMUserRespVo>> listAllStaffByHosId(AttendanceMUserReqVO reqVO) {
        List<AttendanceMUserRespVo> list = attendanceManagementUserService.listAllStaffByHosId(reqVO);
        return R.ok(list);
    }
}

