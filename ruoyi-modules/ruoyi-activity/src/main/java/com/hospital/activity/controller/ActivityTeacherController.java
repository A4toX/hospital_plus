package com.hospital.activity.controller;

import java.util.List;

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
import com.hospital.activity.domain.vo.ActivityTeacherVo;
import com.hospital.activity.domain.bo.ActivityTeacherBo;
import com.hospital.activity.service.IActivityTeacherService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 教学活动教师
 *
 * @author Lion Li
 * @date 2023-06-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/activity/teacher")
public class ActivityTeacherController extends BaseController {

    private final IActivityTeacherService activityTeacherService;


    /**
     * 导出教学活动教师列表
     */
    @SaCheckPermission("activity:teacher:export")
    @Log(title = "教学活动教师", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ActivityTeacherBo bo, HttpServletResponse response) {
        List<ActivityTeacherVo> list = activityTeacherService.queryList(bo);
        ExcelUtil.exportExcel(list, "教学活动教师", ActivityTeacherVo.class, response);
    }


    /**
     * 新增教学活动教师
     */
    @SaCheckPermission("activity:teacher:add")
    @Log(title = "教学活动教师", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody List<ActivityTeacherBo> bos) {
        return toAjax(activityTeacherService.insertByBo(bos));
    }

}
