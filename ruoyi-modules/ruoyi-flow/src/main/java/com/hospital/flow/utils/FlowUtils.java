package com.hospital.flow.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hospital.flow.domain.FlowApply;
import com.hospital.flow.domain.FlowConfig;
import com.hospital.flow.domain.FlowEdge;
import com.hospital.flow.domain.FlowNode;
import com.hospital.flow.enums.EdgeTypeEnum;
import com.hospital.flow.enums.FlowStatusEnum;
import com.hospital.flow.enums.MultiTypeEnum;
import com.hospital.flow.enums.NodeTypeEnum;
import com.hospital.flow.mapper.FlowApplyMapper;
import com.hospital.flow.mapper.FlowConfigMapper;
import com.hospital.flow.mapper.FlowEdgeMapper;
import com.hospital.flow.mapper.FlowNodeMapper;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.SpringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程工具类
 * @author lgx
 */
public class FlowUtils {

    private final static FlowConfigMapper configMapper = SpringUtils.getBean(FlowConfigMapper.class);
    private final static FlowApplyMapper applyMapper = SpringUtils.getBean(FlowApplyMapper.class);
    private final static FlowNodeMapper nodeMapper = SpringUtils.getBean(FlowNodeMapper.class);
    private final static FlowEdgeMapper edgeMapper = SpringUtils.getBean(FlowEdgeMapper.class);

    public static boolean apply(String key, Long businessId) {
        FlowConfig config = getConfig(key);
        if(config == null) {
            throw new ServiceException("申请失败：没有对应的申请流程配置");
        }
        FlowApply apply = new FlowApply();
        apply.setKey(key);
        apply.setConfigId(config.getId());
        apply.setStatus(FlowStatusEnum.RUNNING.getStatus());
        apply.setBusinessId(businessId);
        int result = applyMapper.insert(apply);
        return result > 0;
    }

    public static FlowConfig getConfig(String key) {
        return configMapper.selectByKey(key);
    }

    public static void handleNodeAndEdge(FlowConfig config) {
        nodeMapper.deleteByConfigId(config.getId());
        edgeMapper.deleteByConfigId(config.getId());

        if(StrUtil.isBlank(config.getBpmnConfig())) {
            throw new ServiceException("操作失败：没有对应的流程图配置");
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
                if(StrUtil.isBlank(assigneeType)) {
                    throw new ServiceException("操作失败：存在节点没有操作人员");
                }

                node.setAssigneeType(assigneeType);
                node.setAssigneeValue(assigneeValue);
                String id = nodeJsonObject.get("id", String.class);
                nodeMap.put(id, node);
                nodes.add(node);
            }

            if(!hasStart) {
                throw new ServiceException("操作失败：流程没有开始节点！");
            }
            if(!hasEnd) {
                throw new ServiceException("操作失败：流程没有结束节点！");
            }

            List<JSONObject> edgeJsonObjects = jsonObject.getByPath("edges", List.class);
            for (JSONObject edgeJsonObject : edgeJsonObjects) {
                FlowEdge edge = new FlowEdge();
                edge.setConfigId(config.getId());
                String sourceNodeId = edgeJsonObject.get("sourceNodeId", String.class);
                if(!nodeMap.containsKey(sourceNodeId)) {
                    throw new ServiceException("操作失败：流程边线指向有问题！");
                }
                edge.setSourceNodeId(nodeMap.get(sourceNodeId).getId());

                String targetNodeId = edgeJsonObject.get("targetNodeId", String.class);
                if(!nodeMap.containsKey(targetNodeId)) {
                    throw new ServiceException("操作失败：流程边线指向有问题！");
                }
                edge.setTargetNodeId(nodeMap.get(targetNodeId).getId());

                String conditionExpre = edgeJsonObject.get("conditionExpre", String.class);
                edge.setType(StrUtil.isBlank(conditionExpre) ? EdgeTypeEnum.DEFAULT.getType() : EdgeTypeEnum.CONDITION.getType());
                edge.setConditionExpre(conditionExpre);
                edges.add(edge);
            }
        } catch (Exception e) {
            throw new ServiceException("操作失败：流程图格式错误");
        }

        if(!nodes.isEmpty()) {
            nodeMapper.insertBatch(nodes);
        }
        if(!edges.isEmpty()) {
            edgeMapper.insertBatch(edges);
        }
    }
}
