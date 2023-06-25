package com.hospital.cycle.controller;

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
import com.hospital.cycle.domain.vo.CycleGroupVo;
import com.hospital.cycle.domain.bo.CycleGroupBo;
import com.hospital.cycle.service.ICycleGroupService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 轮转规则组
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/cycle/group")
public class CycleGroupController extends BaseController {

    private final ICycleGroupService cycleGroupService;

    /**
     * 查询轮转规则组列表(包含其下科室)
     */
    @SaCheckPermission("cycle:group:list")
    @GetMapping("/list")
    public List<CycleGroupVo> list(CycleGroupBo bo) {
        //todo 左连接有空数据需要处理
        return cycleGroupService.queryList(bo);
    }

    /**
     * 导出轮转规则组列表
     */
    @SaCheckPermission("cycle:group:export")
    @Log(title = "轮转规则组", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CycleGroupBo bo, HttpServletResponse response) {
        List<CycleGroupVo> list = cycleGroupService.queryList(bo);
        ExcelUtil.exportExcel(list, "轮转规则组", CycleGroupVo.class, response);
    }

    /**
     * 获取轮转规则组详细信息
     *
     * @param groupId 主键
     */
    @SaCheckPermission("cycle:group:query")
    @GetMapping("/{groupId}")
    public R<CycleGroupVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long groupId) {
        return R.ok(cycleGroupService.queryById(groupId));
    }

    /**
     * 新增轮转规则组
     */
    @SaCheckPermission("cycle:group:add")
    @Log(title = "轮转规则组", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CycleGroupBo bo) {
        return toAjax(cycleGroupService.insertByBo(bo));
    }

    /**
     * 修改轮转规则组
     */
    @SaCheckPermission("cycle:group:edit")
    @Log(title = "轮转规则组", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CycleGroupBo bo) {
        return toAjax(cycleGroupService.updateByBo(bo));
    }

    /**
     * 删除轮转规则组
     *
     * @param ids 主键串
     */
    @SaCheckPermission("cycle:group:remove")
    @Log(title = "轮转规则组", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(cycleGroupService.deleteWithValidByIds(List.of(ids), true));
    }
}
