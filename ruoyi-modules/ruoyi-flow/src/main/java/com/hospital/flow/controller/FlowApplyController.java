package com.hospital.flow.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import com.hospital.flow.domain.req.AuditReq;
import com.hospital.flow.domain.req.MyFlowReq;
import com.hospital.flow.domain.req.TodoFlowReq;
import com.hospital.flow.domain.resp.ApplyResp;
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
 * 流程管理/流程申请
 *
 * @author liguoxian
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flow/flowApply")
@SaIgnore
public class FlowApplyController extends BaseController {

    private final IFlowApplyService flowApplyService;

    /**
     * 申请撤回
     * @param applyIds 撤回ID集合
     * @return
     */
    @PutMapping("/cancel")
    @SaCheckPermission("apply:cancel")
    public R<Void> cancel(Long[] applyIds) {
        return toAjax(flowApplyService.cancel(applyIds));
    }

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
     * 分页查询我的流程
     * @return
     */
    @SaCheckPermission("flowApply:myFlow")
    @GetMapping("/myFlow")
    public TableDataInfo<ApplyResp> myFlow(MyFlowReq req, PageQuery pageQuery) {
        return flowApplyService.myFlow(req, pageQuery);
    }

    /**
     * 分页查询我的待办流程
     * @return
     */
    @SaCheckPermission("flowApply:todoFlow")
    @GetMapping("/todoFlow")
    public TableDataInfo<ApplyResp> todoFlow(TodoFlowReq req, PageQuery pageQuery) {
        return flowApplyService.todoFlow(req, pageQuery);
    }

    /**
     * 分页查询我的已办流程
     * @return
     */
    @SaCheckPermission("flowApply:doneFlow")
    @GetMapping("/doneFlow")
    public TableDataInfo<FlowApplyVo> doneFlow(FlowApplyBo bo, PageQuery pageQuery) {
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
}

