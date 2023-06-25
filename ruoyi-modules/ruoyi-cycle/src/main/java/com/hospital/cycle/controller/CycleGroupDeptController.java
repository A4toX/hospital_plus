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
import com.hospital.cycle.domain.vo.CycleGroupDeptVo;
import com.hospital.cycle.domain.bo.CycleGroupDeptBo;
import com.hospital.cycle.service.ICycleGroupDeptService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 轮转规则组关联科室
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/cycle/groupDept")
public class CycleGroupDeptController extends BaseController {

    private final ICycleGroupDeptService cycleGroupDeptService;

    /**
     * 获取规则组下科室列表
     */
    @SaCheckPermission("cycle:groupDept:list")
    @GetMapping("/list")
    public List<CycleGroupDeptVo> list(CycleGroupDeptBo bo) {
        return cycleGroupDeptService.queryList(bo);
    }



    /**
     * 导出轮转规则组关联科室列表
     */
  /*  @SaCheckPermission("cycle:groupDept:export")
    @Log(title = "轮转规则组关联科室", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CycleGroupDeptBo bo, HttpServletResponse response) {
        List<CycleGroupDeptVo> list = cycleGroupDeptService.queryList(bo);
        ExcelUtil.exportExcel(list, "轮转规则组关联科室", CycleGroupDeptVo.class, response);
    }*/

    /**
     * 获取轮转规则组关联科室详细信息
     *
     * @param groupDeptId 主键
     */
    @SaCheckPermission("cycle:groupDept:query")
    @GetMapping("/{groupDeptId}")
    public R<CycleGroupDeptVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long groupDeptId) {
        return R.ok(cycleGroupDeptService.queryById(groupDeptId));
    }

    /**
     * 新增或编辑轮转规则组关联科室
     */
    @SaCheckPermission("cycle:groupDept:add")
    @Log(title = "轮转规则组关联科室", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated({AddGroup.class}) @RequestBody ValidList<CycleGroupDeptBo> bos) {
        return toAjax(cycleGroupDeptService.insertByBo(bos));
    }

    /**
     * 修改轮转规则组关联科室
     */
  /*  @SaCheckPermission("cycle:groupDept:edit")
    @Log(title = "轮转规则组关联科室", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CycleGroupDeptBo bo) {
        return toAjax(cycleGroupDeptService.updateByBo(bo));
    }*/

    /**
     * 删除轮转规则组关联科室
     *
     * @param ids 主键串
     */
    @SaCheckPermission("cycle:groupDept:remove")
    @Log(title = "轮转规则组关联科室", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(cycleGroupDeptService.deleteWithValidByIds(List.of(ids), true));
    }
}
