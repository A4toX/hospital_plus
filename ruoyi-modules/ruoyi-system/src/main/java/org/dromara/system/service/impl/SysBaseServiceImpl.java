package org.dromara.system.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.system.domain.SysDictData;
import org.dromara.system.mapper.SysDictDataMapper;
import org.springframework.stereotype.Service;
import org.dromara.system.domain.bo.SysBaseBo;
import org.dromara.system.domain.vo.SysBaseVo;
import org.dromara.system.domain.SysBase;
import org.dromara.system.mapper.SysBaseMapper;
import org.dromara.system.service.ISysBaseService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 专业Service业务层处理
 *
 * @author yaoyingjie
 * @date 2023-06-18
 */
@RequiredArgsConstructor
@Service
public class SysBaseServiceImpl implements ISysBaseService {

    private final SysBaseMapper baseMapper;
    private final SysDictDataMapper dictDataMapper;

    /**
     * 查询专业
     */
    @Override
    public SysBaseVo queryById(Long baseId){
        return baseMapper.selectVoById(baseId);
    }

    /**
     * 查询专业列表
     */
    @Override
    public TableDataInfo<SysBaseVo> queryPageList(SysBaseBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysBase> lqw = buildQueryWrapper(bo);
        Page<SysBaseVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询专业列表
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
        return lqw;
    }

    /**
     * 新增专业
     */
    @Override
    public Boolean insertByBo(SysBaseBo bo) {
        SysBase add = MapstructUtils.convert(bo, SysBase.class);
        String[] baseCode = add.getBaseCode().split(",");
        validEntityBeforeSave(add);
        Arrays.asList(baseCode).forEach(item -> {
            SysBase sysBase = new SysBase();
            SysDictData dictData = dictDataMapper.selectOne(Wrappers.<SysDictData>lambdaQuery().eq(SysDictData::getDictType,"sys_base").eq(SysDictData::getDictValue, item));
            sysBase.setBaseCode(item);
            sysBase.setBaseName(dictData.getDictLabel());
            baseMapper.insert(sysBase);
        });

        return true;
    }

    /**
     * 修改专业
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
        //新增时如果有重复数据，则直接抛出异常
        String[] baseCode = entity.getBaseCode().split(",");
        List<SysBase> list = baseMapper.selectList(Wrappers.<SysBase>lambdaQuery().in(SysBase::getBaseCode, baseCode));
        if (!list.isEmpty()) {
            throw new RuntimeException("专业编码已存在");
        }
    }

    /**
     * 批量删除专业
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public SysBase selectBaseByCode(String baseCode){
        return baseMapper.selectOne(Wrappers.<SysBase>lambdaQuery().eq(SysBase::getBaseCode,baseCode));
    }
}
