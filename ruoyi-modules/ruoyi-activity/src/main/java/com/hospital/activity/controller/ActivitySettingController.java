package com.hospital.activity.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import com.hospital.activity.domain.bo.ActivitySettingBo;
import com.hospital.activity.domain.vo.ActivitySettingVo;
import com.hospital.activity.service.ActivitySettingService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 教学活动
 *
 * @author makejava
 * @since 2023-06-21 14:17:47
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/activity/activitySetting")
public class ActivitySettingController extends BaseController {

    private final ActivitySettingService activitySettingService;

    @SaCheckPermission("activity:activitySetting:list")
    @GetMapping("/list")
    public TableDataInfo<ActivitySettingVo> list(ActivitySettingBo bo, PageQuery pageQuery) {
        return activitySettingService.selectPageList(bo, pageQuery);
    }

    @SaCheckPermission("activity:activitySetting:query")
    @GetMapping(value = "/{id}")
    public R<ActivitySettingVo> getInfo(@PathVariable Long id) {
        return R.ok(activitySettingService.selectById(id));
    }

    @SaCheckPermission("activity:activitySetting:add")
    @PostMapping
    public R<Void> add(@Validated @RequestBody ActivitySettingBo bo) {
        return toAjax(activitySettingService.insert(bo));
    }

    @SaCheckPermission("activity:activitySetting:edit")
    @PutMapping
    public R<Void> edit(@Validated @RequestBody ActivitySettingBo bo) {
        return toAjax(activitySettingService.update(bo));
    }

    @SaCheckPermission("activity:activitySetting:remove")
    @DeleteMapping("/{ids}")
    public R<Void> remove(@PathVariable Long[] ids) {
        return toAjax(activitySettingService.deleteByIds(ids));
    }
}

