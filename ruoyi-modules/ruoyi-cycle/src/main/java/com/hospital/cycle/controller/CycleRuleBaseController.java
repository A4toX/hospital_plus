package com.hospital.cycle.controller;

import java.util.List;

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
import com.hospital.cycle.domain.vo.CycleRuleBaseVo;
import com.hospital.cycle.domain.bo.CycleRuleBaseBo;
import com.hospital.cycle.service.ICycleRuleBaseService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 规则专业关联
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/cycle/ruleBase")
public class CycleRuleBaseController extends BaseController {

    private final ICycleRuleBaseService cycleRuleBaseService;

    /**
     * 查询规则专业关联列表
     */
    @SaCheckPermission("cycle:ruleBase:list")
    @GetMapping("/list")
    public List<CycleRuleBaseVo> list(CycleRuleBaseBo bo) {
        return cycleRuleBaseService.queryList(bo);
    }

    /**
     * 导出规则专业关联列表
     */
    @SaCheckPermission("cycle:ruleBase:export")
    @Log(title = "规则专业关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CycleRuleBaseBo bo, HttpServletResponse response) {
        List<CycleRuleBaseVo> list = cycleRuleBaseService.queryList(bo);
        ExcelUtil.exportExcel(list, "规则专业关联", CycleRuleBaseVo.class, response);
    }

    /**
     * 获取规则专业关联详细信息
     *
     * @param ruleBaseId 主键
     */
    @SaCheckPermission("cycle:ruleBase:query")
    @GetMapping("/{ruleBaseId}")
    public R<CycleRuleBaseVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long ruleBaseId) {
        return R.ok(cycleRuleBaseService.queryById(ruleBaseId));
    }

    /**
     * 新增规则专业关联
     */
    @SaCheckPermission("cycle:ruleBase:add")
    @Log(title = "规则专业关联", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ValidList<CycleRuleBaseBo> bos) {
        cycleRuleBaseService.insertByBo(bos);
        return R.ok();
    }

    /**
     * 删除规则专业关联
     *
     * @param ids 主键串
     */
    @SaCheckPermission("cycle:ruleBase:remove")
    @Log(title = "规则专业关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(cycleRuleBaseService.deleteWithValidByIds(List.of(ids), true));
    }
}
