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
import com.hospital.cycle.domain.vo.CycleCalcRecordVo;
import com.hospital.cycle.domain.bo.CycleCalcRecordBo;
import com.hospital.cycle.service.ICycleCalcRecordService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 轮转计算过程记录
 *
 * @author yaoyingjie
 * @date 2023-07-02
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/cycle/calcRecord")
public class CycleCalcRecordController extends BaseController {

    private final ICycleCalcRecordService cycleCalcRecordService;

    /**
     * 查询轮转计算过程记录列表
     */
    @SaCheckPermission("cycle:calcRecord:list")
    @GetMapping("/list")
    public TableDataInfo<CycleCalcRecordVo> list(CycleCalcRecordBo bo, PageQuery pageQuery) {
        return cycleCalcRecordService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出轮转计算过程记录列表
     */
    @SaCheckPermission("cycle:calcRecord:export")
    @Log(title = "轮转计算过程记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CycleCalcRecordBo bo, HttpServletResponse response) {
        List<CycleCalcRecordVo> list = cycleCalcRecordService.queryList(bo);
        ExcelUtil.exportExcel(list, "轮转计算过程记录", CycleCalcRecordVo.class, response);
    }

    /**
     * 获取轮转计算过程记录详细信息
     *
     * @param calcRecordId 主键
     */
    @SaCheckPermission("cycle:calcRecord:query")
    @GetMapping("/{calcRecordId}")
    public R<CycleCalcRecordVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long calcRecordId) {
        return R.ok(cycleCalcRecordService.queryById(calcRecordId));
    }

    /**
     * 新增轮转计算过程记录
     */
    @SaCheckPermission("cycle:calcRecord:add")
    @Log(title = "轮转计算过程记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CycleCalcRecordBo bo) {
        return toAjax(cycleCalcRecordService.insertByBo(bo));
    }

    /**
     * 修改轮转计算过程记录
     */
    @SaCheckPermission("cycle:calcRecord:edit")
    @Log(title = "轮转计算过程记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CycleCalcRecordBo bo) {
        return toAjax(cycleCalcRecordService.updateByBo(bo));
    }

    /**
     * 删除轮转计算过程记录
     *
     * @param calcRecordIds 主键串
     */
    @SaCheckPermission("cycle:calcRecord:remove")
    @Log(title = "轮转计算过程记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{calcRecordIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] calcRecordIds) {
        return toAjax(cycleCalcRecordService.deleteWithValidByIds(List.of(calcRecordIds), true));
    }
}
