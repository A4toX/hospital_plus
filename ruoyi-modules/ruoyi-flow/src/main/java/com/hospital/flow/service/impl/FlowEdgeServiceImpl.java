package com.hospital.flow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.service.BaseServiceImpl;
import com.hospital.flow.domain.FlowEdge;
import com.hospital.flow.domain.bo.FlowEdgeBo;
import com.hospital.flow.domain.vo.FlowEdgeVo;
import com.hospital.flow.mapper.FlowEdgeMapper;
import com.hospital.flow.service.IFlowEdgeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程边线 服务实现
 *
 * @author liguoxian
 */
@RequiredArgsConstructor
@Service
public class FlowEdgeServiceImpl extends BaseServiceImpl<FlowEdgeMapper, FlowEdge, FlowEdgeVo, FlowEdgeBo> implements IFlowEdgeService {

    @Override
    public TableDataInfo<FlowEdgeVo> selectPageList(FlowEdgeBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlowEdge> lqw = buildQueryWrapper(bo);
        Page<FlowEdgeVo> page = mapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    @Override
    public List<FlowEdgeVo> selectList(FlowEdgeBo bo) {
        LambdaQueryWrapper<FlowEdge> lqw = buildQueryWrapper(bo);
        return mapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlowEdge> buildQueryWrapper(FlowEdgeBo bo) {
        return new LambdaQueryWrapperX<FlowEdge>()
                .eqIfPresent(FlowEdge::getConfigId, bo.getConfigId())
                .eqIfPresent(FlowEdge::getType, bo.getType())
                .eqIfPresent(FlowEdge::getSourceNodeId, bo.getSourceNodeId())
                .eqIfPresent(FlowEdge::getTargetNodeId, bo.getTargetNodeId())
                .eqIfPresent(FlowEdge::getConditionExpre, bo.getConditionExpre())
                .eqIfPresent(FlowEdge::getCreateDept, bo.getCreateDept())
                .eqIfPresent(FlowEdge::getRemark, bo.getRemark())
        ;
    }
}
