package com.hospital.flow.mapper;

import com.hospital.flow.enums.NodeTypeEnum;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import com.hospital.flow.domain.FlowNode;
import com.hospital.flow.domain.vo.FlowNodeVo;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;

/**
 * 流程节点 数据层
 *
 * @author liguoxian
 */
public interface FlowNodeMapper extends BaseMapperPlus<FlowNode, FlowNodeVo> {

    default int deleteByConfigId(Long configId) {
        return delete(new LambdaQueryWrapperX<FlowNode>().eq(FlowNode::getConfigId, configId));
    }

    default FlowNode getStartNode(Long configId) {
        return selectOne(new LambdaQueryWrapperX<FlowNode>().eq(FlowNode::getConfigId, configId)
            .eq(FlowNode::getType, NodeTypeEnum.START.getType()));
    }

    default FlowNode getEndNode(Long configId) {
        return selectOne(new LambdaQueryWrapperX<FlowNode>().eq(FlowNode::getConfigId, configId)
            .eq(FlowNode::getType, NodeTypeEnum.END.getType()));
    }
}

