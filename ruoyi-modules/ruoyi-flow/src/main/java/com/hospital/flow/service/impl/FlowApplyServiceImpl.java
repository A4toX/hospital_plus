package com.hospital.flow.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hospital.flow.domain.FlowNode;
import com.hospital.flow.domain.FlowTask;
import com.hospital.flow.domain.req.AuditReq;
import com.hospital.flow.enums.FlowResultEnum;
import com.hospital.flow.enums.FlowStatusEnum;
import com.hospital.flow.enums.MultiTypeEnum;
import com.hospital.flow.enums.NodeTypeEnum;
import com.hospital.flow.exception.FlowException;
import com.hospital.flow.mapper.FlowNodeMapper;
import com.hospital.flow.mapper.FlowTaskMapper;
import com.hospital.flow.utils.FlowUtils;
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
import org.dromara.common.satoken.utils.LoginHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 流程申请 服务实现
 *
 * @author liguoxian
 */
@RequiredArgsConstructor
@Service
public class FlowApplyServiceImpl extends BaseServiceImpl<FlowApplyMapper, FlowApply, FlowApplyVo, FlowApplyBo> implements IFlowApplyService {

    private final FlowTaskMapper taskMapper;
    private final FlowNodeMapper nodeMapper;

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
    @Transactional
    public int approve(AuditReq req) {
        if(StrUtil.isBlank(req.getTaskIds())) {
            throw new FlowException("审核失败：没有要审核的任务");
        }
        int result = 0;
        Long userId = LoginHelper.getUserId();
        String now = DateUtil.now();
        long[] taskIds = StrUtil.splitToLong(req.getTaskIds(), ",");
        for (long taskId : taskIds) {
            FlowTask task = taskMapper.selectById(taskId);
            if(task == null || !userId.equals(task.getAssigneeUserId())) {
                throw new FlowException("审核失败：审核任务传参异常");
            }
            FlowApply apply = mapper.selectById(task.getApplyId());
            if(FlowStatusEnum.RUNNING.getStatus().equals(apply.getStatus())) {
                throw new FlowException("审核失败：仅能审核进行中的流程");
            }
            task.setAuditTime(now);
            task.setResult(FlowResultEnum.APPROVE.getResult());
            task.setComment(req.getComment());
            result += taskMapper.updateById(task);

            FlowNode node = nodeMapper.selectById(task.getNodeId());
            boolean auditFinished = true;

            if(MultiTypeEnum.AND_SIGN.getType().equals(node.getMultiType())) {
                List<FlowTask> flowTasks = taskMapper.selectByNodeId(apply.getId(), node.getId());
                for (FlowTask flowTask : flowTasks) {
                    if(!FlowResultEnum.APPROVE.getResult().equals(flowTask.getResult())) {
                        auditFinished = false;
                    }
                }
            } else if(MultiTypeEnum.OR_SIGN.getType().equals(node.getMultiType())) {
                taskMapper.deleteNotAudit(apply.getId(), node.getId());
            }

            if(auditFinished) {
                FlowNode nextNode = FlowUtils.getNextTask(apply.getConfigId(), node, FlowUtils.getVariable(apply.getId()));
                if(nextNode != null) {
                    if(NodeTypeEnum.END.getType().equals(nextNode.getType())) {
                        apply.setStatus(FlowStatusEnum.FINISH.getStatus());
                        mapper.updateById(apply);
                    } else if(NodeTypeEnum.TASK.getType().equals(nextNode.getType())) {
                        List<FlowTask> tasks = FlowUtils.getTasks(nextNode);
                        for (FlowTask flowTask : tasks) {
                            flowTask.setApplyId(apply.getId());
                            taskMapper.insert(flowTask);
                        }
                    }
                } else {
                    apply.setStatus(FlowStatusEnum.END.getStatus());
                    mapper.updateById(apply);
                }
            }
        }
        return result;
    }

    @Override
    public int reject(AuditReq req) {
        if(StrUtil.isBlank(req.getTaskIds())) {
            throw new FlowException("审核失败：没有要审核的任务");
        }
        int result = 0;
        Long userId = LoginHelper.getUserId();
        String now = DateUtil.now();
        long[] taskIds = StrUtil.splitToLong(req.getTaskIds(), ",");
        for (long taskId : taskIds) {
            FlowTask task = taskMapper.selectById(taskId);
            if(task == null || !userId.equals(task.getAssigneeUserId())) {
                throw new FlowException("审核失败：审核任务传参异常");
            }
            FlowApply apply = mapper.selectById(task.getApplyId());
            if(FlowStatusEnum.RUNNING.getStatus().equals(apply.getStatus())) {
                throw new FlowException("审核失败：仅能审核进行中的流程");
            }
            task.setAuditTime(now);
            task.setResult(FlowResultEnum.REJECT.getResult());
            task.setComment(req.getComment());
            result += taskMapper.updateById(task);

            FlowNode node = nodeMapper.selectById(task.getNodeId());
            taskMapper.deleteNotAudit(apply.getId(), node.getId());

            FlowNode nextNode = FlowUtils.getNextTask(apply.getConfigId(), node, FlowUtils.getVariable(apply.getId()));
            if(nextNode != null) {
                if(NodeTypeEnum.END.getType().equals(nextNode.getType())) {
                    apply.setStatus(FlowStatusEnum.FINISH.getStatus());
                    mapper.updateById(apply);
                } else if(NodeTypeEnum.TASK.getType().equals(nextNode.getType())) {
                    List<FlowTask> tasks = FlowUtils.getTasks(nextNode);
                    for (FlowTask flowTask : tasks) {
                        flowTask.setApplyId(apply.getId());
                        taskMapper.insert(flowTask);
                    }
                }
            } else {
                apply.setStatus(FlowStatusEnum.END.getStatus());
                mapper.updateById(apply);
            }
        }
        return result;
    }

    @Override
    @Transactional
    public int cancel(Long[] applyIds) {
        if(applyIds.length == 0) {
            throw new FlowException("撤回失败：没有要撤回的申请");
        }
        Long userId = LoginHelper.getUserId();
        int result = 0;
        for (Long applyId : applyIds) {
            FlowApply apply = mapper.selectById(applyId);
            if(!apply.getCreateBy().equals(userId)) {
                throw new FlowException("撤回失败：仅能撤回本人的申请");
            }
            List<FlowTask> approveTasks = taskMapper.selectByResult(applyId, FlowResultEnum.APPROVE.getResult());
            if(!approveTasks.isEmpty()) {
                throw new FlowException("撤回失败：已有人审核通过，禁止撤回");
            }
            apply.setStatus(FlowStatusEnum.CANCEL.getStatus());
            result += mapper.updateById(apply);

            taskMapper.delete(new LambdaQueryWrapperX<FlowTask>()
                .eq(FlowTask::getApplyId, applyId)
                .eq(FlowTask::getResult, FlowResultEnum.PROCESS.getResult()));
        }
        return result;
    }
}
