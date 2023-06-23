package com.hospital.activity.controller;

import java.util.List;

import com.hospital.activity.domain.bo.ActivitySettingBo;
import com.hospital.activity.domain.vo.ActivitySettingVo;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import com.hospital.activity.domain.vo.ActivityUserVo;
import com.hospital.activity.domain.bo.ActivityUserBo;
import com.hospital.activity.service.IActivityUserService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 教学活动参与用户
 *
 * @author yyj
 * @date 2023-06-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/activity/user")
public class ActivityUserController extends BaseController {

    private final IActivityUserService activityUserService;


    /**
     * 导出教学活动参与用户列表
     */
    @SaCheckPermission("activity:user:export")
    @Log(title = "教学活动参与用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ActivityUserBo bo, HttpServletResponse response) {
        List<ActivityUserVo> list = activityUserService.queryList(bo);
        ExcelUtil.exportExcel(list, "教学活动参与用户", ActivityUserVo.class, response);
    }


    /**
     * 新增或者修改教学活动参与用户
     */
    @SaCheckPermission("activity:user:add")
    @Log(title = "教学活动参与用户", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody List<ActivityUserBo> bos) {
        activityUserService.insertByBo(bos);
        return R.ok();
    }

    /**
     * 参与人员签到
     */
    @SaCheckPermission("activity:user:add")
    @Log(title = "教学活动参与用户", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("usersign/{acvitityId}")
    public R<Void> usersign(@PathVariable("acvitityId") Long acvitityId) {
        activityUserService.usersign(acvitityId);
        return R.ok();
    }

    /**
     * 获取教学活动下学院的签到列表
     */
    @SaCheckPermission("activity:setting:list")
    @GetMapping("/list")
    public TableDataInfo<ActivityUserVo> list(ActivityUserBo bo, PageQuery pageQuery) {
        return activityUserService.queryPageList(bo, pageQuery);
    }


}
