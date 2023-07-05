package com.hospital.activity.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.common.core.validate.ValidList;
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
import com.hospital.activity.domain.vo.ActivityPicVo;
import com.hospital.activity.domain.bo.ActivityPicBo;
import com.hospital.activity.service.IActivityPicService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 教学活动图片
 *
 * @author Lion Li
 * @date 2023-06-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/activity/pic")
public class ActivityPicController extends BaseController {

    private final IActivityPicService activityPicService;

    /**
     * 查询教学活动图片列表
     */
    @SaCheckPermission("activity:pic:list")
    @GetMapping("/list")
    public TableDataInfo<ActivityPicVo> list(ActivityPicBo bo, PageQuery pageQuery) {
        return activityPicService.queryPageList(bo, pageQuery);
    }



    /**
     * 新增教学活动图片
     */
    @SaCheckPermission("activity:pic:add")
    @Log(title = "教学活动图片", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ValidList<ActivityPicBo> bos) {
        activityPicService.insertByBo(bos);
        return R.ok();
    }


    /**
     * 删除教学活动图片
     *
     * @param ids 主键串
     */
    @SaCheckPermission("activity:pic:remove")
    @Log(title = "教学活动图片", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(activityPicService.deleteWithValidByIds(List.of(ids), true));
    }
}
