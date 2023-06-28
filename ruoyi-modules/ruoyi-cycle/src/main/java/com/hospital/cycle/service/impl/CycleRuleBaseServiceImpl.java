package com.hospital.cycle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hospital.cycle.domain.CycleRule;
import com.hospital.cycle.mapper.CycleRuleMapper;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.service.BaseService;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hospital.cycle.domain.bo.CycleRuleBaseBo;
import com.hospital.cycle.domain.vo.CycleRuleBaseVo;
import com.hospital.cycle.domain.CycleRuleBase;
import com.hospital.cycle.mapper.CycleRuleBaseMapper;
import com.hospital.cycle.service.ICycleRuleBaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

import static com.hospital.cycle.constant.CycleConstant.CYCLE_STATUS_COMPLETE;
import static com.hospital.cycle.constant.CycleConstant.NO;

/**
 * 规则专业关联Service业务层处理
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@RequiredArgsConstructor
@Service
public class CycleRuleBaseServiceImpl implements ICycleRuleBaseService {

    private final CycleRuleBaseMapper baseMapper;
    private final CycleRuleMapper ruleMapper;
    private final BaseService baseService;

    /**
     * 查询规则专业关联
     */
    @Override
    public CycleRuleBaseVo queryById(Long ruleBaseId){
        return baseMapper.selectVoById(ruleBaseId);
    }

    /**
     * 查询规则专业关联列表
     */
    @Override
    public TableDataInfo<CycleRuleBaseVo> queryPageList(CycleRuleBaseBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CycleRuleBase> lqw = LambdabuildQueryWrapper(bo);
        Page<CycleRuleBaseVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询规则专业关联列表
     */
    @Override
    public List<CycleRuleBaseVo> queryList(CycleRuleBaseBo bo) {
        LambdaQueryWrapper<CycleRuleBase> lqw = this.LambdabuildQueryWrapper(bo);
        List<CycleRuleBaseVo> voList = baseMapper.selectVoList(lqw);
        if (voList.isEmpty()){
            return new ArrayList<>();
        }
        voList.forEach(vo->{
            vo.setBaseName(baseService.selectBaseNameById(vo.getBaseId()));
        });
        return voList;
    }

    private LambdaQueryWrapper<CycleRuleBase> LambdabuildQueryWrapper(CycleRuleBaseBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CycleRuleBase> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getRuleId() != null, CycleRuleBase::getRuleId, bo.getRuleId());
        lqw.eq(bo.getBaseId() != null, CycleRuleBase::getBaseId, bo.getBaseId());
        return lqw;
    }

    private QueryWrapper<CycleRuleBase> buildQueryWrapper(CycleRuleBaseBo bo) {
        Map<String, Object> params = bo.getParams();
        QueryWrapper<CycleRuleBase> wrapper = new QueryWrapper<>();
        wrapper.eq(bo.getRuleId() != null, "crb.rule_id", bo.getRuleId());
        wrapper.eq(bo.getBaseId() != null, "crb.base_id", bo.getBaseId());
        return wrapper;
    }



    /**
     * 新增规则专业关联
     */
    @Override
    public void insertByBo(List<CycleRuleBaseBo> bos) {
        List<CycleRuleBase> adds = MapstructUtils.convert(bos, CycleRuleBase.class);
        adds.forEach(this::validEntityBeforeSave);
        baseMapper.insertBatch(adds);
    }

    /**
     * 修改规则专业关联
     */
    @Override
    public Boolean updateByBo(CycleRuleBaseBo bo) {
        CycleRuleBase update = MapstructUtils.convert(bo, CycleRuleBase.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CycleRuleBase entity){
        //校验规则是否开启了专业关联
        CycleRule cycleRule = ruleMapper.selectById(entity.getRuleId());
        if (cycleRule==null){
            throw new ServiceException("该规则不存在");
        }
        if (CYCLE_STATUS_COMPLETE.equals(cycleRule.getRuleStatus())) {
            throw new ServiceException("该规则已经排班，不能修改");
        }
        if (NO.equals(cycleRule.getBaseFlag())) {
            throw new ServiceException("该规则未开启专业关联功能");
        }
        //校验规则专业关联是否已经存在
        CycleRuleBase cycleRuleBase = baseMapper.selectOne(Wrappers.<CycleRuleBase>lambdaQuery()
                .eq(CycleRuleBase::getRuleId, entity.getRuleId())
                .eq(CycleRuleBase::getBaseId, entity.getBaseId()));
        if (cycleRuleBase!=null){
            throw new ServiceException("请重新选择专业，该专业已经存在");
        }
    }

    /**
     * 批量删除规则专业关联
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
