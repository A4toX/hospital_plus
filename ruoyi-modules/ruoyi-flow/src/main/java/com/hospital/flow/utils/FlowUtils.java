package com.hospital.flow.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hospital.flow.domain.*;
import com.hospital.flow.enums.EdgeTypeEnum;
import com.hospital.flow.enums.FlowStatusEnum;
import com.hospital.flow.enums.MultiTypeEnum;
import com.hospital.flow.enums.NodeTypeEnum;
import com.hospital.flow.exception.FlowException;
import com.hospital.flow.mapper.*;
import org.dromara.common.core.enums.YesNoEnum;
import org.dromara.common.core.utils.SpringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程工具类
 * @author lgx
 */
public class FlowUtils {

    private final static FlowConfigMapper configMapper = SpringUtils.getBean(FlowConfigMapper.class);
    private final static FlowApplyMapper applyMapper = SpringUtils.getBean(FlowApplyMapper.class);
    private final static FlowNodeMapper nodeMapper = SpringUtils.getBean(FlowNodeMapper.class);
    private final static FlowEdgeMapper edgeMapper = SpringUtils.getBean(FlowEdgeMapper.class);
    private final static FlowTaskMapper taskMapper = SpringUtils.getBean(FlowTaskMapper.class);

    public static boolean apply(String flowKey, Long businessId, Map<String, Object> variables) {
        FlowConfig config = getConfig(flowKey);
        if(config == null) {
            throw new FlowException("申请失败：没有对应的申请流程配置");
        }
        FlowNode task = getNextTask(config.getId(), null, variables);
        if(NodeTypeEnum.END.getType().equals(task.getType())){
            throw new FlowException("申请失败：申请流程配置错误");
        }
        List<FlowTask> tasks = getTasks(task);

        FlowApply apply = new FlowApply();
        apply.setFlowKey(flowKey);
        apply.setConfigId(config.getId());
        apply.setStatus(FlowStatusEnum.RUNNING.getStatus());
        apply.setBusinessId(businessId);
        int result = applyMapper.insert(apply);
        for (FlowTask flowTask : tasks) {
            flowTask.setApplyId(apply.getId());
            taskMapper.insert(flowTask);
        }
        return result > 0;
    }

    public static FlowConfig getConfig(String flowKey) {
        return configMapper.selectByKey(flowKey);
    }

    public static void handleNodeAndEdge(FlowConfig config) {
        nodeMapper.deleteByConfigId(config.getId());
        edgeMapper.deleteByConfigId(config.getId());

        if(StrUtil.isBlank(config.getBpmnConfig())) {
            throw new FlowException("操作失败：没有对应的流程图配置");
        }

        List<FlowNode> nodes = new ArrayList<>();
        List<FlowEdge> edges = new ArrayList<>();

        try {
            JSONObject jsonObject = JSONUtil.parseObj(config.getBpmnConfig());
            Map<String, FlowNode> nodeMap = new HashMap<>();

            List<JSONObject> nodeJsonObjects = jsonObject.getByPath("nodes", List.class);
            boolean hasStart = false;
            boolean hasEnd = false;
            for (JSONObject nodeJsonObject : nodeJsonObjects) {
                FlowNode node = new FlowNode();
                node.setId(IdUtil.getSnowflakeNextId());
                node.setConfigId(config.getId());
                String type = nodeJsonObject.get("type", String.class);
                if(NodeTypeEnum.START.getType().equals(type)) {
                    hasStart = true;
                } else if(NodeTypeEnum.END.getType().equals(type)) {
                    hasEnd = true;
                }
                node.setType(type);
                String multiType = nodeJsonObject.get("multiType", String.class);

                node.setMultiType(StrUtil.isNotBlank(multiType) ? multiType : MultiTypeEnum.NO.getType());

                String assigneeType = nodeJsonObject.get("assigneeType", String.class);
                String assigneeValue = nodeJsonObject.get("assigneeValue", String.class);
                if(NodeTypeEnum.TASK.getType().equals(type) && StrUtil.isBlank(assigneeType)) {
                    throw new FlowException("操作失败：存在节点没有操作人员");
                }

                node.setAssigneeType(assigneeType);
                node.setAssigneeValue(assigneeValue);
                String id = nodeJsonObject.get("id", String.class);
                nodeMap.put(id, node);
                nodes.add(node);
            }

            if(!hasStart) {
                throw new FlowException("操作失败：流程没有开始节点！");
            }
            if(!hasEnd) {
                throw new FlowException("操作失败：流程没有结束节点！");
            }

            List<JSONObject> edgeJsonObjects = jsonObject.getByPath("edges", List.class);
            for (JSONObject edgeJsonObject : edgeJsonObjects) {
                FlowEdge edge = new FlowEdge();
                edge.setConfigId(config.getId());
                String sourceNodeId = edgeJsonObject.get("sourceNodeId", String.class);
                if(!nodeMap.containsKey(sourceNodeId)) {
                    throw new FlowException("操作失败：流程边线指向有问题！");
                }
                edge.setSourceNodeId(nodeMap.get(sourceNodeId).getId());

                String targetNodeId = edgeJsonObject.get("targetNodeId", String.class);
                if(!nodeMap.containsKey(targetNodeId)) {
                    throw new FlowException("操作失败：流程边线指向有问题！");
                }
                edge.setTargetNodeId(nodeMap.get(targetNodeId).getId());

                String conditionExpre = edgeJsonObject.get("conditionExpre", String.class);
                edge.setType(StrUtil.isBlank(conditionExpre) ? EdgeTypeEnum.DEFAULT.getType() : EdgeTypeEnum.CONDITION.getType());
                edge.setConditionExpre(conditionExpre);
                edges.add(edge);
            }
        } catch (FlowException e) {
            throw e;
        } catch (Exception e) {
            throw new FlowException("操作失败：流程图格式错误");
        }

        if(!nodes.isEmpty()) {
            nodeMapper.insertBatch(nodes);
        }
        if(!edges.isEmpty()) {
            edgeMapper.insertBatch(edges);
        }
    }

    public static List<FlowTask> getTasks(FlowNode node) {
        Set<Long> assigneeIds = FlowAssigneeUtils.assigneeMap.get(node.getAssigneeType()).getAssigneeIds(node.getAssigneeValue());
        if(CollUtil.isEmpty(assigneeIds)) {
            throw new FlowException("申请失败：获取审核人失败");
        }
        return assigneeIds.stream().map(userId -> {
            FlowTask flowTask = new FlowTask();
            flowTask.setNodeId(node.getId());
            flowTask.setAssigneeUserId(userId);
            flowTask.setCurrentFlag(YesNoEnum.YES.getValue());
            return flowTask;
        }).collect(Collectors.toList());
    }

    public static FlowNode getNextTask(Long configId, FlowNode node, Map<String, Object> variables) {
        if(node == null) {
            node = nodeMapper.getStartNode(configId);
        }
        if(node == null) {
            throw new FlowException("申请失败：申请流程配置错误");
        }
        // 防止循环网关
        Set<Long> nodeIds = new HashSet<>();
        FlowNode nextNode;
        do {
            nextNode = getNextNode(configId, node, variables);
            if(nextNode == null || nodeIds.contains(nextNode.getId())) {
                throw new FlowException("申请失败：申请流程配置错误");
            }
        } while (NodeTypeEnum.GATEWAY.getType().equals(nextNode.getType()));
        return nextNode;
    }

    public static FlowNode getNextNode(Long configId, FlowNode node, Map<String, Object> variables) {
        List<FlowEdge> edges = edgeMapper.selectEdgeBySourceNode(configId, node.getId());
        FlowEdge defaultEdge = null;
        for (FlowEdge edge : edges) {
            if(EdgeTypeEnum.DEFAULT.getType().equals(edge.getType())) {
                defaultEdge = edge;
                continue;
            }
            if(StrUtil.isNotBlank(edge.getConditionExpre())) {
                boolean expressionValue = ExpressionUtils.getBooleanValue(edge.getConditionExpre(), variables);
                if(expressionValue) {
                    return nodeMapper.selectById(edge.getTargetNodeId());
                }
            }
        }
        if(defaultEdge != null) {
            return nodeMapper.selectById(defaultEdge.getTargetNodeId());
        }
        return null;
    }
}
