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

    int publish(Long id);
}
