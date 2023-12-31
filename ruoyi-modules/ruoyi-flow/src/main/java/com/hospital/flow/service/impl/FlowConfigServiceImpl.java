package com.hospital.flow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.dromara.common.core.enums.YesNoEnum;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.service.BaseServiceImpl;
import com.hospital.flow.domain.FlowConfig;
import com.hospital.flow.domain.bo.FlowConfigBo;
import com.hospital.flow.domain.vo.FlowConfigVo;
import com.hospital.flow.mapper.FlowConfigMapper;
import com.hospital.flow.service.IFlowConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 流程配置 服务实现
 *
 * @author liguoxian
 */
@RequiredArgsConstructor
@Service
public class FlowConfigServiceImpl extends BaseServiceImpl<FlowConfigMapper, FlowConfig, FlowConfigVo, FlowConfigBo> implements IFlowConfigService {

    @Override
    @Transactional
    public int insert(FlowConfigBo bo) {
        FlowConfig flowConfig = mapper.selectByKey(bo.getKey());
        if(flowConfig != null) {
            throw new ServiceException(String.format("关键字【%s】已存在", bo.getKey()));
        }
        flowConfig = new FlowConfig();
        flowConfig.setName(bo.getName());
        flowConfig.setKey(bo.getKey());
        flowConfig.setBpmnConfig(bo.getBpmnConfig());
        flowConfig.setVersionFlag(YesNoEnum.YES.getValue());
        flowConfig.setRemark(bo.getRemark());
        return mapper.insert(flowConfig);
    }

    @Override
    @Transactional
    public int update(FlowConfigBo bo) {
        FlowConfig flowConfig = mapper.selectById(bo.getId());
        if(flowConfig == null) {
            throw new ServiceException("配置不存在");
        }
        flowConfig.setName(bo.getName());
        flowConfig.setBpmnConfig(bo.getBpmnConfig());
        flowConfig.setRemark(bo.getRemark());
        return mapper.updateById(flowConfig);
    }

    @Override
    public TableDataInfo<FlowConfigVo> selectPageList(FlowConfigBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlowConfig> lqw = buildQueryWrapper(bo);
        Page<FlowConfigVo> page = mapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    @Override
    public List<FlowConfigVo> selectList(FlowConfigBo bo) {
        LambdaQueryWrapper<FlowConfig> lqw = buildQueryWrapper(bo);
        return mapper.selectVoList(lqw);
    }

    @Override
    @Transactional
    public int publish(Long id) {
        FlowConfig flowConfig = mapper.selectById(id);
        if(flowConfig == null) {
            throw new ServiceException("配置不存在");
        }
        if(StrUtil.isBlank(flowConfig.getPublishTime())) {
            flowConfig.setVersion(1);
            flowConfig.setVersionFlag(YesNoEnum.YES.getValue());
            flowConfig.setPublishTime(DateUtil.now());
            return mapper.updateById(flowConfig);
        }

        flowConfig.setVersionFlag(YesNoEnum.NO.getValue());
        mapper.updateById(flowConfig);

        flowConfig.setId(null);
        flowConfig.setPublishTime(DateUtil.now());
        flowConfig.setVersionFlag(YesNoEnum.YES.getValue());
        flowConfig.setVersion(flowConfig.getVersion() + 1);
        return mapper.insert(flowConfig);
    }

    private LambdaQueryWrapper<FlowConfig> buildQueryWrapper(FlowConfigBo bo) {
        return new LambdaQueryWrapperX<FlowConfig>()
                .likeIfPresent(FlowConfig::getName, bo.getName())
                .eqIfPresent(FlowConfig::getKey, bo.getKey())
                .eq(FlowConfig::getVersionFlag, YesNoEnum.YES.getValue())
                .likeIfPresent(FlowConfig::getRemark, bo.getRemark())
        ;
    }
}
