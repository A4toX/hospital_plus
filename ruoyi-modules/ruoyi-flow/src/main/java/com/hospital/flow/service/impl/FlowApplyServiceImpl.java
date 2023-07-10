package com.hospital.flow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hospital.flow.domain.req.AuditReq;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.service.BaseServiceImpl;
import com.hospital.flow.domain.FlowApply;
import com.hospital.flow.domain.bo.FlowApplyBo;
import com.hospital.flow.domain.vo.FlowApplyVo;
import com.hospital.flow.mapper.FlowApplyMapper;
import com.hospital.flow.service.IFlowApplyService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程申请 服务实现
 *
 * @author liguoxian
 */
@RequiredArgsConstructor
@Service
public class FlowApplyServiceImpl extends BaseServiceImpl<FlowApplyMapper, FlowApply, FlowApplyVo, FlowApplyBo> implements IFlowApplyService {

    @Override
    public TableDataInfo<FlowApplyVo> selectPageList(FlowApplyBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlowApply> lqw = buildQueryWrapper(bo);
        Page<FlowApplyVo> page = mapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    @Override
    public List<FlowApplyVo> selectList(FlowApplyBo bo) {
        LambdaQueryWrapper<FlowApply> lqw = buildQueryWrapper(bo);
        return mapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlowApply> buildQueryWrapper(FlowApplyBo bo) {
        return new LambdaQueryWrapperX<FlowApply>()
                .eqIfPresent(FlowApply::getConfigId, bo.getConfigId())
                .eqIfPresent(FlowApply::getFlowKey, bo.getFlowKey())
                .eqIfPresent(FlowApply::getStatus, bo.getStatus())
                .eqIfPresent(FlowApply::getBusinessId, bo.getBusinessId())
                .eqIfPresent(FlowApply::getCreateDept, bo.getCreateDept())
                .eqIfPresent(FlowApply::getRemark, bo.getRemark())
        ;
    }

    @Override
    public int approve(AuditReq req) {
        return 0;
    }

    @Override
    public int reject(AuditReq req) {
        return 0;
    }

    @Override
    public int cancel(Long[] applyIds) {
        return 0;
    }
}
