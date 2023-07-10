package com.hospital.flow.mapper;

import com.hospital.flow.domain.FlowEdge;
import com.hospital.flow.domain.vo.FlowEdgeVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;

import java.util.List;

/**
 * 流程边线 数据层
 *
 * @author liguoxian
 */
public interface FlowEdgeMapper extends BaseMapperPlus<FlowEdge, FlowEdgeVo> {

    default int deleteByConfigId(Long configId) {
        return delete(new LambdaQueryWrapperX<FlowEdge>().eq(FlowEdge::getConfigId, configId));
    }

    default List<FlowEdge> selectEdgeBySourceNode(Long configId, Long sourceNodeId) {
        return selectList(new LambdaQueryWrapperX<FlowEdge>()
            .eq(FlowEdge::getConfigId, configId).eq(FlowEdge::getSourceNodeId, sourceNodeId));
    }
}

