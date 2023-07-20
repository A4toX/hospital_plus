package com.hospital.flow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.service.BaseServiceImpl;
import com.hospital.flow.domain.FlowVariable;
import com.hospital.flow.domain.bo.FlowVariableBo;
import com.hospital.flow.domain.vo.FlowVariableVo;
import com.hospital.flow.mapper.FlowVariableMapper;
import com.hospital.flow.service.IFlowVariableService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程当前参数 服务实现
 *
 * @author liguoxian
 */
@RequiredArgsConstructor
@Service
public class FlowVariableServiceImpl extends BaseServiceImpl<FlowVariableMapper, FlowVariable, FlowVariableVo, FlowVariableBo> implements IFlowVariableService {

    @Override
    public TableDataInfo<FlowVariableVo> selectPageList(FlowVariableBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlowVariable> lqw = buildQueryWrapper(bo);
        Page<FlowVariableVo> page = mapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    @Override
    public List<FlowVariableVo> selectList(FlowVariableBo bo) {
        LambdaQueryWrapper<FlowVariable> lqw = buildQueryWrapper(bo);
        return mapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlowVariable> buildQueryWrapper(FlowVariableBo bo) {
        return new LambdaQueryWrapperX<FlowVariable>()
                .eqIfPresent(FlowVariable::getApplyId, bo.getApplyId())
                .eqIfPresent(FlowVariable::getParamType, bo.getParamType())
                .eqIfPresent(FlowVariable::getParamName, bo.getParamName())
                .eqIfPresent(FlowVariable::getParamValue, bo.getParamValue())
                .eqIfPresent(FlowVariable::getCreateDept, bo.getCreateDept())
                .eqIfPresent(FlowVariable::getRemark, bo.getRemark())
        ;
    }
}
