package com.hospital.cycle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hospital.cycle.domain.CycleGroup;
import com.hospital.cycle.domain.CycleRule;
import com.hospital.cycle.domain.bo.CycleGroupBo;
import com.hospital.cycle.mapper.CycleGroupMapper;
import com.hospital.cycle.mapper.CycleRuleMapper;
import com.hospital.cycle.utils.CycleValidUtils;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hospital.cycle.domain.bo.CycleGroupDeptBo;
import com.hospital.cycle.domain.vo.CycleGroupDeptVo;
import com.hospital.cycle.domain.CycleGroupDept;
import com.hospital.cycle.mapper.CycleGroupDeptMapper;
import com.hospital.cycle.service.ICycleGroupDeptService;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.hospital.cycle.constant.CycleConstant.*;

/**
 * 轮转规则组关联科室Service业务层处理
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@RequiredArgsConstructor
@Service
public class CycleGroupDeptServiceImpl implements ICycleGroupDeptService {

    private final CycleGroupDeptMapper baseMapper;
    private final CycleRuleMapper ruleMapper;
    private final CycleGroupMapper groupMapper;

    /**
     * 查询轮转规则组关联科室
     */
    @Override
    public CycleGroupDeptVo queryById(Long groupDeptId){
        return baseMapper.selectVoById(groupDeptId);
    }

    /**
     * 查询轮转规则组关联科室列表
     */
    @Override
    public TableDataInfo<CycleGroupDeptVo> queryPageList(CycleGroupDeptBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CycleGroupDept> lqw = buildLambdaQueryWrapper(bo);
        Page<CycleGroupDeptVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询轮转规则组关联科室列表
     */
    @Override
    public List<CycleGroupDeptVo> queryList(CycleGroupDeptBo bo) {
        return baseMapper.selectWithDeptName(this.buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<CycleGroupDept> buildLambdaQueryWrapper(CycleGroupDeptBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CycleGroupDept> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getRuleId() != null, CycleGroupDept::getRuleId, bo.getRuleId());
        lqw.eq(bo.getGroupId() != null, CycleGroupDept::getGroupId, bo.getGroupId());
        lqw.eq(bo.getDeptId() != null, CycleGroupDept::getDeptId, bo.getDeptId());
        lqw.eq(bo.getDeptUnitNum() != null, CycleGroupDept::getDeptUnitNum, bo.getDeptUnitNum());
        return lqw;
    }

    private QueryWrapper<CycleGroupDept> buildQueryWrapper(CycleGroupDeptBo bo) {
        Map<String, Object> params = bo.getParams();
        QueryWrapper<CycleGroupDept> wrapper = new QueryWrapper<>();
        wrapper.eq(bo.getGroupId()!=null, "cgd.group_id", bo.getGroupId());
        return wrapper;
    }


    /**
     * 新增轮转规则组关联科室
     */
    @Override
    public Boolean insertByBo(List<CycleGroupDeptBo> bos) {
        List<CycleGroupDept> adds = MapstructUtils.convert(bos, CycleGroupDept.class);
        validEntityBeforeSave(adds);
        //删除后统一插入
        LambdaQueryWrapper<CycleGroupDept> lqw = Wrappers.lambdaQuery();
        lqw.eq(CycleGroupDept::getGroupId, bos.get(0).getGroupId());
        baseMapper.delete(lqw);
        baseMapper.insertBatch(adds);
        return true;
    }

    /**
     * 修改轮转规则组关联科室
     */
    @Override
    public Boolean updateByBo(CycleGroupDeptBo bo) {
        CycleGroupDept update = MapstructUtils.convert(bo, CycleGroupDept.class);
//        validEntityBeforeSave(update);
        baseMapper.updateById(update);
        return true;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(List<CycleGroupDept> entitys){
        CycleValidUtils.validGroupDept(entitys);
    }

    /**
     * 批量删除轮转规则组关联科室
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
