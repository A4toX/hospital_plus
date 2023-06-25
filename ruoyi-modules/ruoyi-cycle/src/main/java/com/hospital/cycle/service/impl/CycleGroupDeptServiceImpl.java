package com.hospital.cycle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hospital.cycle.domain.CycleGroup;
import com.hospital.cycle.domain.CycleRule;
import com.hospital.cycle.domain.bo.CycleGroupBo;
import com.hospital.cycle.mapper.CycleGroupMapper;
import com.hospital.cycle.mapper.CycleRuleMapper;
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
        //校验规则是否能够修改
        CycleRule cycleRule = ruleMapper.selectById(entitys.get(0).getRuleId());
        if(cycleRule == null){
            throw new ServiceException("规则不存在");
        }
        if(CYCLE_STATUS_COMPLETE.equals(cycleRule.getRuleStatus())){
            throw new ServiceException("规则已经完成排班，不能修改");
        }
        //根据方法判断
       CycleGroup cyclegroup = groupMapper.selectById(entitys.get(0).getGroupId());
        if(cyclegroup == null){
            throw new ServiceException("规则组不存在");
        }
        //判断规则内科室是否已经添加
        List<CycleGroupDept> cycleGroupDepts = baseMapper.selectList(Wrappers.<CycleGroupDept>lambdaQuery()
            .eq(CycleGroupDept::getRuleId,cycleRule.getRuleId())
            .in(CycleGroupDept::getDeptId,entitys.stream().map(CycleGroupDept::getDeptId).collect(Collectors.toSet())));
        if (!cycleGroupDepts.isEmpty()){
            throw new ServiceException("科室在此规则中已被添加");
        }

        switch (cyclegroup.getGroupMethod()){
            case CYCLE_GROUP_METHOD_MUST://必选只需要校验时间是否满足
                List<Integer> deptUnitNums = entitys.stream().map(CycleGroupDept::getDeptUnitNum).collect(Collectors.toList());
                //相加
                Integer sum = deptUnitNums.stream().reduce(0, Integer::sum);
                if(sum !=cyclegroup.getGroupUnitNum()){
                    throw new ServiceException("所有科室轮转时间必须等于规则组的轮转时间");
                }
                break;
            case CYCLE_GROUP_METHOD_ELECTIVE://任选其几
                Integer electiveNum = cyclegroup.getMethodNumber();//任选数量
                if(entitys.size() < electiveNum){
                    throw new ServiceException("科室数量不能小于任选数量");
                }
                //entitys中每个科室的时间必须相同
                Integer deptUnitNum = entitys.get(0).getDeptUnitNum();
                entitys.forEach(entity -> {
                    if(!deptUnitNum.equals(entity.getDeptUnitNum())){
                        throw new ServiceException("科室轮转时间必须相同");
                    }
                });
                //确定任选数量相加是否等于规则组的轮转时间
                if (cyclegroup.getGroupUnitNum()!=deptUnitNum*electiveNum){
                    throw new ServiceException("科室轮转时间必须等于规则组的轮转时间");
                }
                break;
            case CYCLE_GROUP_METHOD_TIME://按照时间选
                break;
            default:
                throw new ServiceException("错误的规则组方法");

        }

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
