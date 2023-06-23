package com.hospital.activity.controller;

import java.util.List;

import com.hospital.activity.domain.ActivitySetting;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.data.repository.query.Param;
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
import com.hospital.activity.domain.vo.ActivitySettingVo;
import com.hospital.activity.domain.bo.ActivitySettingBo;
import com.hospital.activity.service.IActivitySettingService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

import static com.hospital.activity.constant.ActivityConstant.*;

/**
 * 教学活动配置
 *
 * @author yaoyignjie
 * @date 2023-06-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/activity/setting")
public class ActivitySettingController extends BaseController {

    private final IActivitySettingService activitySettingService;

    /**
     * 查询教学活动列表
     */
    @SaCheckPermission("activity:setting:list")
    @GetMapping("/list")
    public TableDataInfo<ActivitySettingVo> list(ActivitySettingBo bo, PageQuery pageQuery) {
        return activitySettingService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出教学活动列表
     */
    @SaCheckPermission("activity:setting:export")
    @Log(title = "教学活动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ActivitySettingBo bo, HttpServletResponse response) {
        List<ActivitySettingVo> list = activitySettingService.getExportList(bo);
        ExcelUtil.exportExcel(list, "教学活动", ActivitySettingVo.class, response);
    }

    /**
     * 获取教学活动详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("activity:setting:query")
    @GetMapping("/{id}")
    public R<ActivitySettingVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(activitySettingService.queryById(id));
    }

    /**
     * 新增教学活动
     */
    @SaCheckPermission("activity:setting:add")
    @Log(title = "教学活动", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ActivitySettingBo bo) {
        if (ACTIVITY_TYPE_SCHOOL.equalsIgnoreCase(bo.getActivityLevel())&&bo.getDeptId()!=null){
            return R.fail("院级活动请勿选择科室");
        }else if (ACTIVITY_TYPE_DEPT.equalsIgnoreCase(bo.getActivityLevel())&&bo.getDeptId()==null) {
            return R.fail("科室类型必须选择科室");
        }
        return toAjax(activitySettingService.insertByBo(bo));
    }

    /**
     * 修改教学活动
     */
    @SaCheckPermission("activity:setting:edit")
    @Log(title = "教学活动", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ActivitySettingBo bo) {
        activitySettingService.updateByBo(bo);
        return R.ok();
    }



    /**
     * 手动开启或结束教学活动
     */
    @SaCheckPermission("activity:setting:edit")
    @Log(title = "教学活动", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/updateActivityStatus/{id}")
    public R<Void> updateActivityStatus(@PathVariable(value = "id") Long id, String activityStatus) {
        activitySettingService.updateActivityStatus(id,activityStatus);
        return R.ok();
    }

    /**
     * 开启或关闭教学活动签到功能
     */
    @SaCheckPermission("activity:setting:edit")
    @Log(title = "教学活动", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/updateActivitySign/{id}")
    public R<Void> updateActivitySign(@PathVariable(value = "id") Long id, String signStatus) {
        activitySettingService.updateActivitySign(id,signStatus);
        return R.ok();
    }

    /**
     * 删除教学活动
     *
     * @param ids 主键串
     */
    @SaCheckPermission("activity:setting:remove")
    @Log(title = "教学活动", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        activitySettingService.deleteWithValidByIds(List.of(ids), true);
        return R.ok();
    }

}
