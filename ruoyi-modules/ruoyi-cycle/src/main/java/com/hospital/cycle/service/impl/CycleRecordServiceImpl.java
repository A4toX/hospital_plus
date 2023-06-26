package com.hospital.cycle.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hospital.cycle.domain.bo.CycleRecordBo;
import com.hospital.cycle.domain.vo.CycleRecordVo;
import com.hospital.cycle.domain.CycleRecord;
import com.hospital.cycle.mapper.CycleRecordMapper;
import com.hospital.cycle.service.ICycleRecordService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 用户轮转记录Service业务层处理
 *
 * @author yaoyingjie
 * @date 2023-06-25
 */
@RequiredArgsConstructor
@Service
public class CycleRecordServiceImpl implements ICycleRecordService {

    private final CycleRecordMapper baseMapper;

    /**
     * 查询用户轮转记录
     */
    @Override
    public CycleRecordVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询用户轮转记录列表
     */
    @Override
    public TableDataInfo<CycleRecordVo> queryPageList(CycleRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CycleRecord> lqw = buildQueryWrapper(bo);
        Page<CycleRecordVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询用户轮转记录列表
     */
    @Override
    public List<CycleRecordVo> queryList(CycleRecordBo bo) {
        LambdaQueryWrapper<CycleRecord> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CycleRecord> buildQueryWrapper(CycleRecordBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CycleRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, CycleRecord::getUserId, bo.getUserId());
        lqw.eq(bo.getDeptId() != null, CycleRecord::getDeptId, bo.getDeptId());
        lqw.eq(StringUtils.isNotBlank(bo.getCycleStatus()), CycleRecord::getCycleStatus, bo.getCycleStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getStartTime()), CycleRecord::getStartTime, bo.getStartTime());
        lqw.eq(StringUtils.isNotBlank(bo.getEndTime()), CycleRecord::getEndTime, bo.getEndTime());
        lqw.eq(bo.getRuleId() != null, CycleRecord::getRuleId, bo.getRuleId());
        lqw.eq(bo.getBaseId() != null, CycleRecord::getBaseId, bo.getBaseId());
        lqw.eq(bo.getCycleRecordIndex() != null, CycleRecord::getCycleRecordIndex, bo.getCycleRecordIndex());
        return lqw;
    }

    /**
     * 新增用户轮转记录
     */
    @Override
    public Boolean insertByBo(CycleRecordBo bo) {
        CycleRecord add = MapstructUtils.convert(bo, CycleRecord.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改用户轮转记录
     */
    @Override
    public Boolean updateByBo(CycleRecordBo bo) {
        CycleRecord update = MapstructUtils.convert(bo, CycleRecord.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CycleRecord entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除用户轮转记录
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
