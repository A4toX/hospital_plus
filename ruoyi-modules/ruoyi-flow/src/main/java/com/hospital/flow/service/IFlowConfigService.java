package com.hospital.flow.service;

import org.dromara.common.mybatis.core.service.IBaseService;
import com.hospital.flow.domain.bo.FlowConfigBo;
import com.hospital.flow.domain.vo.FlowConfigVo;

/**
 * 流程配置 接口
 *
 * @author liguoxian
 */
public interface IFlowConfigService extends IBaseService<FlowConfigVo, FlowConfigBo> {

    /**
     * 配置发布
     * @param id
     * @return
     */
    int publish(Long id);
}
