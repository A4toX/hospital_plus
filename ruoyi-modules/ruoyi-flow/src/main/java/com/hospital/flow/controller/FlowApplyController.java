package com.hospital.flow.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hospital.flow.domain.req.AuditReq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import com.hospital.flow.domain.bo.FlowApplyBo;
import com.hospital.flow.domain.vo.FlowApplyVo;
import com.hospital.flow.service.IFlowApplyService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 流程申请
 *
 * @author liguoxian
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flow/flowApply")
public class FlowApplyController extends BaseController {

    private final IFlowApplyService flowApplyService;

    /**
     * 申请通过
     * @param req
     * @return
     */
    @PutMapping("/approve")
    @SaCheckPermission("apply:approve")
    public R<Void> approve(@Valid @RequestBody AuditReq req) {
        return toAjax(flowApplyService.approve(req));
    }

    /**
     * 申请驳回
     * @param req
     * @return
     */
    @PutMapping("/reject")
    @SaCheckPermission("apply:reject")
    public R<Void> reject(@Valid @RequestBody AuditReq req) {
        return toAjax(flowApplyService.reject(req));
    }

    /**
     * 分页查询流程申请
     * @return
     */
    @SaCheckPermission("flowApply:list")
    @GetMapping("/list")
    public TableDataInfo<FlowApplyVo> list(FlowApplyBo bo, PageQuery pageQuery) {
        return flowApplyService.selectPageList(bo, pageQuery);
    }

    /**
     * 根据ID获取流程申请
     * @param id 数据ID
     * @return
     */
    @SaCheckPermission("flowApply:query")
    @GetMapping(value = "/{id}")
    public R<FlowApplyVo> getInfo(@PathVariable Long id) {
        return R.ok(flowApplyService.selectById(id));
    }

    /**
     * 新增流程申请
     * @return
     */
    @SaCheckPermission("flowApply:add")
    @Log(title = "流程申请管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@Validated @RequestBody FlowApplyBo bo) {
        return toAjax(flowApplyService.insert(bo));
    }

    /**
     * 修改流程申请
     * @return
     */
    @SaCheckPermission("flowApply:edit")
    @Log(title = "流程申请管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@Validated @RequestBody FlowApplyBo bo) {
        return toAjax(flowApplyService.update(bo));
    }

    /**
     * 删除流程申请
     * @param ids 数据ID字符串集合
     * @return
     */
    @SaCheckPermission("flowApply:remove")
    @Log(title = "流程申请管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@PathVariable Long[] ids) {
        return toAjax(flowApplyService.deleteByIds(ids));
    }
}

