package com.hospital.flow.mapper;

import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import com.hospital.flow.domain.FlowVariable;
import com.hospital.flow.domain.vo.FlowVariableVo;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;

import java.util.List;

/**
 * 流程当前参数 数据层
 *
 * @author liguoxian
 */
public interface FlowVariableMapper extends BaseMapperPlus<FlowVariable, FlowVariableVo> {

    default List<FlowVariable> selectByApplyId(Long applyId) {
        return selectList(new LambdaQueryWrapperX<FlowVariable>().eq(FlowVariable::getApplyId, applyId));
    }
}

