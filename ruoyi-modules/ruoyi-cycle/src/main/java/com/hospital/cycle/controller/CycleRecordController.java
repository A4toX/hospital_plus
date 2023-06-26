package com.hospital.cycle.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.excel.EasyExcel;
import com.hospital.cycle.domain.vo.CycleRecordImportVo;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.common.excel.core.ExcelResult;
import org.dromara.system.domain.vo.SysUserImportVo;
import org.dromara.system.listener.SysUserImportListener;
import org.springframework.http.MediaType;
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
import com.hospital.cycle.domain.vo.CycleRecordVo;
import com.hospital.cycle.domain.bo.CycleRecordBo;
import com.hospital.cycle.service.ICycleRecordService;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

/**
 * 用户轮转记录
 *
 * @author yaoyingjie
 * @date 2023-06-25
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/cycle/record")
public class CycleRecordController extends BaseController {

    private final ICycleRecordService cycleRecordService;

    /**
     * 查询用户轮转记录列表
     */
    @SaCheckPermission("cycle:record:list")
    @GetMapping("/list")
    public TableDataInfo<CycleRecordVo> list(CycleRecordBo bo, PageQuery pageQuery) {
        return cycleRecordService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户轮转记录列表
     */
    @SaCheckPermission("cycle:record:export")
    @Log(title = "用户轮转记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CycleRecordBo bo, HttpServletResponse response) {
        List<CycleRecordVo> list = cycleRecordService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户轮转记录", CycleRecordVo.class, response);
    }

    /**
     * 获取用户轮转记录详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("cycle:record:query")
    @GetMapping("/{id}")
    public R<CycleRecordVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(cycleRecordService.queryById(id));
    }

    /**
     * 新增用户轮转记录
     */
    @SaCheckPermission("cycle:record:add")
    @Log(title = "用户轮转记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CycleRecordBo bo) {
        return toAjax(cycleRecordService.insertByBo(bo));
    }

    /**
     * 修改用户轮转记录
     */
    @SaCheckPermission("cycle:record:edit")
    @Log(title = "用户轮转记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CycleRecordBo bo) {
        return toAjax(cycleRecordService.updateByBo(bo));
    }

    /**
     * 删除用户轮转记录
     *
     * @param ids 主键串
     */
    @SaCheckPermission("cycle:record:remove")
    @Log(title = "用户轮转记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(cycleRecordService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 导入数据
     *
     * @param file          导入文件
     * @param updateSupport 是否更新已存在数据
     */
    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @SaCheckPermission("system:user:import")
    @PostMapping(value = "/importData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<Void> importData(@RequestPart("file") MultipartFile file, boolean updateSupport) throws Exception {
        cycleRecordService.importData(file.getInputStream());
        return R.ok();
    }
}
