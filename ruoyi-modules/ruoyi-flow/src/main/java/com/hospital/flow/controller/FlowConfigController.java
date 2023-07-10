package com.hospital.flow.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import com.hospital.flow.domain.vo.FlowTypeVo;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import com.hospital.flow.domain.bo.FlowConfigBo;
import com.hospital.flow.domain.vo.FlowConfigVo;
import com.hospital.flow.service.IFlowConfigService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 流程管理/流程配置
 *
 * @author liguoxian
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flow/flowConfig")
@SaIgnore
public class FlowConfigController extends BaseController {

    private final IFlowConfigService flowConfigService;

    /**
     * 分页查询流程配置
     * @return
     */
    @SaCheckPermission("flowConfig:list")
    @GetMapping("/list")
    public TableDataInfo<FlowConfigVo> list(FlowConfigBo bo, PageQuery pageQuery) {
        return flowConfigService.selectPageList(bo, pageQuery);
    }

    /**
     * 获取流程类别
     * @return
     */
    @SaCheckPermission("flowConfig:flowType")
    @GetMapping(value = "/flowType")
    public R<List<FlowTypeVo>> flowType(FlowConfigBo bo) {
        List<FlowConfigVo> vos = flowConfigService.selectList(bo);
        List<FlowTypeVo> list = vos.stream().map(v -> new FlowTypeVo(v.getFlowKey(), v.getFlowName())).collect(Collectors.toList());
        return R.ok(list);
    }

    /**
     * 根据ID获取流程配置
     * @param id 数据ID
     * @return
     */
    @SaCheckPermission("flowConfig:query")
    @GetMapping(value = "/{id}")
    public R<FlowConfigVo> getInfo(@PathVariable Long id) {
        return R.ok(flowConfigService.selectById(id));
    }

    /**
     * 新增流程配置
     * @return
     */
    @SaCheckPermission("flowConfig:add")
    @Log(title = "流程配置管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@Validated @RequestBody FlowConfigBo bo) {
        return toAjax(flowConfigService.insert(bo));
    }

    /**
     * 修改流程配置
     * @return
     */
    @SaCheckPermission("flowConfig:edit")
    @Log(title = "流程配置管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@Validated @RequestBody FlowConfigBo bo) {
        return toAjax(flowConfigService.update(bo));
    }

    /**
     * 删除流程配置
     * @param id 数据ID
     * @return
     */
    @SaCheckPermission("flowConfig:remove")
    @Log(title = "流程配置管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@PathVariable Long id) {
        return toAjax(flowConfigService.deleteById(id));
    }

    /**
     * 流程发布
     * @param id 数据ID
     * @return
     */
    @SaCheckPermission("flowConfig:publish")
    @Log(title = "流程配置管理", businessType = BusinessType.OTHER)
    @PutMapping("/publish")
    public R<Void> publish(Long id) {
        return toAjax(flowConfigService.publish(id));
    }


}

