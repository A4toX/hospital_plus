package com.hospital.flow.mapper;

import org.dromara.common.core.enums.YesNoEnum;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import com.hospital.flow.domain.FlowConfig;
import com.hospital.flow.domain.vo.FlowConfigVo;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;

/**
 * 流程配置 数据层
 *
 * @author liguoxian
 */
public interface FlowConfigMapper extends BaseMapperPlus<FlowConfig, FlowConfigVo> {

    default FlowConfig selectByKey(String key) {
        return selectOne(new LambdaQueryWrapperX<FlowConfig>()
            .eq(FlowConfig::getKey, key)
            .eq(FlowConfig::getVersionFlag, YesNoEnum.YES.getValue()));
    }
}

