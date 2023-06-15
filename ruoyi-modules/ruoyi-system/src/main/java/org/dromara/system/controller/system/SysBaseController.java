package org.dromara.system.controller.system;

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
import org.dromara.system.domain.vo.SysBaseVo;
import org.dromara.system.domain.bo.SysBaseBo;
import org.dromara.system.service.ISysBaseService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 专业基地
 *
 * @author yaoyingjie
 * @date 2023-06-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/base")
public class SysBaseController extends BaseController {

    private final ISysBaseService sysBaseService;

    /**
     * 查询专业基地列表
     */
    @SaCheckPermission("system:base:list")
    @GetMapping("/list")
    public TableDataInfo<SysBaseVo> list(SysBaseBo bo, PageQuery pageQuery) {
        return sysBaseService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出专业基地列表
     */
    @SaCheckPermission("system:base:export")
    @Log(title = "专业基地", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysBaseBo bo, HttpServletResponse response) {
        List<SysBaseVo> list = sysBaseService.queryList(bo);
        ExcelUtil.exportExcel(list, "专业基地", SysBaseVo.class, response);
    }

    /**
     * 获取专业基地详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:base:query")
    @GetMapping("/{id}")
    public R<SysBaseVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(sysBaseService.queryById(id));
    }

    /**
     * 新增专业基地
     */
    @SaCheckPermission("system:base:add")
    @Log(title = "专业基地", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysBaseBo bo) {
        return toAjax(sysBaseService.insertByBo(bo));
    }

    /**
     * 修改专业基地
     */
    @SaCheckPermission("system:base:edit")
    @Log(title = "专业基地", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysBaseBo bo) {
        return toAjax(sysBaseService.updateByBo(bo));
    }

    /**
     * 删除专业基地
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:base:remove")
    @Log(title = "专业基地", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(sysBaseService.deleteWithValidByIds(List.of(ids), true));
    }
}
