package com.hospital.flow.mapper;

import com.hospital.flow.enums.FlowResultEnum;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import com.hospital.flow.domain.FlowTask;
import com.hospital.flow.domain.vo.FlowTaskVo;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;

import java.util.List;

/**
 * 审核任务 数据层
 *
 * @author liguoxian
 */
public interface FlowTaskMapper extends BaseMapperPlus<FlowTask, FlowTaskVo> {

    default List<FlowTask> selectByResult(Long applyId, String result) {
        return selectList(new LambdaQueryWrapperX<FlowTask>()
            .eq(FlowTask::getApplyId, applyId)
            .eq(FlowTask::getResult, result));
    }

    default List<FlowTask> selectByNodeId(Long applyId, Long nodeId) {
        return selectList(new LambdaQueryWrapperX<FlowTask>()
            .eq(FlowTask::getApplyId, applyId)
            .eq(FlowTask::getNodeId, nodeId));
    }

    default int deleteNotAudit(Long applyId, Long nodeId) {
        return delete(new LambdaQueryWrapperX<FlowTask>()
            .eq(FlowTask::getApplyId, applyId)
            .eq(FlowTask::getResult, FlowResultEnum.PROCESS.getResult())
            .eq(FlowTask::getNodeId, nodeId));
    }
}

