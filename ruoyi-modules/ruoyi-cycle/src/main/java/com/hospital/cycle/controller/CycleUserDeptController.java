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
import com.hospital.cycle.domain.vo.CycleUserDeptVo;
import com.hospital.cycle.domain.bo.CycleUserDeptBo;
import com.hospital.cycle.service.ICycleUserDeptService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 轮转学生选课记录
 *
 * @author yaoyingjie
 * @date 2023-06-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/cycle/userDept")
public class CycleUserDeptController extends BaseController {

    private final ICycleUserDeptService cycleUserDeptService;




    /**
     * 查询轮转学生选课记录列表
     */
    @SaCheckPermission("cycle:userDept:list")
    @GetMapping("/list")
    public TableDataInfo<CycleUserDeptVo> list(CycleUserDeptBo bo, PageQuery pageQuery) {
        return cycleUserDeptService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出轮转学生选课记录列表
     */
    @SaCheckPermission("cycle:userDept:export")
    @Log(title = "轮转学生选课记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CycleUserDeptBo bo, HttpServletResponse response) {
        List<CycleUserDeptVo> list = cycleUserDeptService.queryList(bo);
        ExcelUtil.exportExcel(list, "轮转学生选课记录", CycleUserDeptVo.class, response);
    }

    /**
     * 获取轮转学生选课记录详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("cycle:userDept:query")
    @GetMapping("/{id}")
    public R<CycleUserDeptVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(cycleUserDeptService.queryById(id));
    }

    /**
     * 新增轮转学生选课记录(每次提交一个阶段的选课)
     */
    @SaCheckPermission("cycle:userDept:add")
    @Log(title = "轮转学生选课记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ValidList<CycleUserDeptBo> bos) {
        return toAjax(cycleUserDeptService.insertByBo(bos));
    }

    /**
     * 修改轮转学生选课记录
     */
    @SaCheckPermission("cycle:userDept:edit")
    @Log(title = "轮转学生选课记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CycleUserDeptBo bo) {
        return toAjax(cycleUserDeptService.updateByBo(bo));
    }

    /**
     * 删除轮转学生选课记录
     *
     * @param ids 主键串
     */
    @SaCheckPermission("cycle:userDept:remove")
    @Log(title = "轮转学生选课记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(cycleUserDeptService.deleteWithValidByIds(List.of(ids), true));
    }
}
