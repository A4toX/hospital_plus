package com.hospital.cycle.controller;

import java.util.List;

import com.hospital.cycle.domain.bo.CycleRuleBaseBo;
import com.hospital.cycle.service.ICycleGroupService;
import com.hospital.cycle.service.ICycleRuleBaseService;
import com.hospital.cycle.validate.CycleStageGroup;
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
import com.hospital.cycle.domain.vo.CycleRuleVo;
import com.hospital.cycle.domain.bo.CycleRuleBo;
import com.hospital.cycle.service.ICycleRuleService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 轮转规则
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/cycle/rule")
public class CycleRuleController extends BaseController {

    private final ICycleRuleService cycleRuleService;


    /**
     * 查询轮转规则列表(分页)
     */
    @SaCheckPermission("cycle:rule:list")
    @GetMapping("/list")
    public TableDataInfo<CycleRuleVo> list(CycleRuleBo bo, PageQuery pageQuery) {
        return cycleRuleService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出轮转规则列表
     */
    @SaCheckPermission("cycle:rule:export")
    @Log(title = "轮转规则", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CycleRuleBo bo, HttpServletResponse response) {
        List<CycleRuleVo> list = cycleRuleService.queryList(bo);
        ExcelUtil.exportExcel(list, "轮转规则", CycleRuleVo.class, response);
    }

    /**
     * 获取轮转规则详细信息
     *
     * @param ruleId 主键
     */
    @SaCheckPermission("cycle:rule:query")
    @GetMapping("/{ruleId}")
    public R<CycleRuleVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long ruleId) {

        return R.ok(cycleRuleService.queryById(ruleId));
    }

    /**
     * 新增轮转规则
     */
    @SaCheckPermission("cycle:rule:add")
    @Log(title = "轮转规则", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CycleRuleBo bo) {
        return toAjax(cycleRuleService.insertByBo(bo));
    }

    /**
     * 从现有规则新增阶段
     * @param bo
     * @return
     */
    @SaCheckPermission("cycle:rule:add")
    @Log(title = "轮转规则", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/addStage")
    public R<Void> addStage(@Validated(CycleStageGroup.class) @RequestBody CycleRuleBo bo) {
        cycleRuleService.addStage(bo);
        return R.ok();
    }

    /**
     * 修改轮转规则
     */
    @SaCheckPermission("cycle:rule:edit")
    @Log(title = "轮转规则", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CycleRuleBo bo) {
        return toAjax(cycleRuleService.updateByBo(bo));
    }

    /**
     * 删除轮转规则
     *
     * @param ids 主键串
     */
    @SaCheckPermission("cycle:rule:remove")
    @Log(title = "轮转规则", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(cycleRuleService.deleteWithValidByIds(List.of(ids), true));
    }
}
