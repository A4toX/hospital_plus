package com.hospital.flow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.service.BaseServiceImpl;
import com.hospital.flow.domain.FlowTask;
import com.hospital.flow.domain.bo.FlowTaskBo;
import com.hospital.flow.domain.vo.FlowTaskVo;
import com.hospital.flow.mapper.FlowTaskMapper;
import com.hospital.flow.service.IFlowTaskService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 审核任务 服务实现
 *
 * @author liguoxian
 */
@RequiredArgsConstructor
@Service
public class FlowTaskServiceImpl extends BaseServiceImpl<FlowTaskMapper, FlowTask, FlowTaskVo, FlowTaskBo> implements IFlowTaskService {

    @Override
    public TableDataInfo<FlowTaskVo> selectPageList(FlowTaskBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlowTask> lqw = buildQueryWrapper(bo);
        Page<FlowTaskVo> page = mapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    @Override
    public List<FlowTaskVo> selectList(FlowTaskBo bo) {
        LambdaQueryWrapper<FlowTask> lqw = buildQueryWrapper(bo);
        return mapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlowTask> buildQueryWrapper(FlowTaskBo bo) {
        return new LambdaQueryWrapperX<FlowTask>()
                .eqIfPresent(FlowTask::getFlowKey, bo.getFlowKey())
                .eqIfPresent(FlowTask::getNodeId, bo.getNodeId())
                .eqIfPresent(FlowTask::getAssigneeUserId, bo.getAssigneeUserId())
                .eqIfPresent(FlowTask::getResult, bo.getResult())
                .eqIfPresent(FlowTask::getComment, bo.getComment())
                .eqIfPresent(FlowTask::getCurrentFlag, bo.getCurrentFlag())
                .eqIfPresent(FlowTask::getCreateDept, bo.getCreateDept())
                .eqIfPresent(FlowTask::getRemark, bo.getRemark())
        ;
    }
}
