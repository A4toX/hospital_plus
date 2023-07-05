package com.hospital.flow.utils;

import com.hospital.flow.domain.FlowApply;
import com.hospital.flow.domain.FlowConfig;
import com.hospital.flow.enums.FlowStatusEnum;
import com.hospital.flow.mapper.FlowApplyMapper;
import com.hospital.flow.mapper.FlowConfigMapper;
import com.hospital.flow.mapper.FlowEdgeMapper;
import com.hospital.flow.mapper.FlowNodeMapper;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.SpringUtils;

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


    }
}
