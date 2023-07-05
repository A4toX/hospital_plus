package com.hospital.flow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.service.BaseServiceImpl;
import com.hospital.flow.domain.FlowNode;
import com.hospital.flow.domain.bo.FlowNodeBo;
import com.hospital.flow.domain.vo.FlowNodeVo;
import com.hospital.flow.mapper.FlowNodeMapper;
import com.hospital.flow.service.IFlowNodeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程节点 服务实现
 *
 * @author liguoxian
 */
@RequiredArgsConstructor
@Service
public class FlowNodeServiceImpl extends BaseServiceImpl<FlowNodeMapper, FlowNode, FlowNodeVo, FlowNodeBo> implements IFlowNodeService {

    @Override
    public TableDataInfo<FlowNodeVo> selectPageList(FlowNodeBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlowNode> lqw = buildQueryWrapper(bo);
        Page<FlowNodeVo> page = mapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    @Override
    public List<FlowNodeVo> selectList(FlowNodeBo bo) {
        LambdaQueryWrapper<FlowNode> lqw = buildQueryWrapper(bo);
        return mapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlowNode> buildQueryWrapper(FlowNodeBo bo) {
        return new LambdaQueryWrapperX<FlowNode>()
                .eqIfPresent(FlowNode::getType, bo.getType())
                .eqIfPresent(FlowNode::getConfigId, bo.getConfigId())
                .eqIfPresent(FlowNode::getMultiType, bo.getMultiType())
                .eqIfPresent(FlowNode::getAssigneeType, bo.getAssigneeType())
                .eqIfPresent(FlowNode::getAssigneeValue, bo.getAssigneeValue())
                .eqIfPresent(FlowNode::getCreateDept, bo.getCreateDept())
                .eqIfPresent(FlowNode::getRemark, bo.getRemark())
        ;
    }
}
