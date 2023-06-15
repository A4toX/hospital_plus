package org.dromara.system.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.system.domain.bo.SysBaseBo;
import org.dromara.system.domain.vo.SysBaseVo;
import org.dromara.system.domain.SysBase;
import org.dromara.system.mapper.SysBaseMapper;
import org.dromara.system.service.ISysBaseService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 专业基地Service业务层处理
 *
 * @author yaoyingjie
 * @date 2023-06-15
 */
@RequiredArgsConstructor
@Service
public class SysBaseServiceImpl implements ISysBaseService {

    private final SysBaseMapper baseMapper;

    /**
     * 查询专业基地
     */
    @Override
    public SysBaseVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询专业基地列表
     */
    @Override
    public TableDataInfo<SysBaseVo> queryPageList(SysBaseBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysBase> lqw = buildQueryWrapper(bo);
        Page<SysBaseVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询专业基地列表
     */
    @Override
    public List<SysBaseVo> queryList(SysBaseBo bo) {
        LambdaQueryWrapper<SysBase> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysBase> buildQueryWrapper(SysBaseBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysBase> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getBaseName()), SysBase::getBaseName, bo.getBaseName());
        lqw.eq(StringUtils.isNotBlank(bo.getBaseCode()), SysBase::getBaseCode, bo.getBaseCode());
        lqw.eq(StringUtils.isNotBlank(bo.getBaseLeaderPhone()), SysBase::getBaseLeaderPhone, bo.getBaseLeaderPhone());
        lqw.like(StringUtils.isNotBlank(bo.getBaseLeaderName()), SysBase::getBaseLeaderName, bo.getBaseLeaderName());
        return lqw;
    }

    /**
     * 新增专业基地
     */
    @Override
    public Boolean insertByBo(SysBaseBo bo) {
        SysBase add = MapstructUtils.convert(bo, SysBase.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改专业基地
     */
    @Override
    public Boolean updateByBo(SysBaseBo bo) {
        SysBase update = MapstructUtils.convert(bo, SysBase.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysBase entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除专业基地
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
