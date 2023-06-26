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
import com.hospital.cycle.domain.vo.CycleStudentVo;
import com.hospital.cycle.domain.bo.CycleStudentBo;
import com.hospital.cycle.service.ICycleStudentService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 学员规则关联
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/hospital/student")
public class CycleStudentController extends BaseController {

    private final ICycleStudentService cycleStudentService;

    /**
     * 查询学员规则关联列表
     */
    @SaCheckPermission("hospital:student:list")
    @GetMapping("/list")
    public TableDataInfo<CycleStudentVo> list(CycleStudentBo bo, PageQuery pageQuery) {
        return cycleStudentService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出学员规则关联列表
     */
    @SaCheckPermission("hospital:student:export")
    @Log(title = "学员规则关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CycleStudentBo bo, HttpServletResponse response) {
        List<CycleStudentVo> list = cycleStudentService.queryList(bo);
        ExcelUtil.exportExcel(list, "学员规则关联", CycleStudentVo.class, response);
    }

    /**
     * 获取学员规则关联详细信息
     *
     * @param cycleStudentId 主键
     */
    @SaCheckPermission("hospital:student:query")
    @GetMapping("/{cycleStudentId}")
    public R<CycleStudentVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long cycleStudentId) {
        return R.ok(cycleStudentService.queryById(cycleStudentId));
    }

    /**
     * 新增学员规则关联
     */
    @SaCheckPermission("hospital:student:add")
    @Log(title = "学员规则关联", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add( @Validated(AddGroup.class) @RequestBody ValidList<CycleStudentBo> bos) {
        return toAjax(cycleStudentService.insertByBo(bos));
    }

    /**
     * 修改学员规则关联
     */
    @SaCheckPermission("hospital:student:edit")
    @Log(title = "学员规则关联", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CycleStudentBo bo) {
        return toAjax(cycleStudentService.updateByBo(bo));
    }

    /**
     * 删除学员规则关联
     *
     * @param ids 主键串
     */
    @SaCheckPermission("hospital:student:remove")
    @Log(title = "学员规则关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(cycleStudentService.deleteWithValidByIds(List.of(ids), true));
    }
}
