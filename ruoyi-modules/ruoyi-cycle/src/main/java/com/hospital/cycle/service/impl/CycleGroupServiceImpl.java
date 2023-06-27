package com.hospital.cycle.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hospital.cycle.domain.CycleGroupDept;
import com.hospital.cycle.domain.CycleRule;
import com.hospital.cycle.mapper.CycleGroupDeptMapper;
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
import org.dromara.common.satoken.utils.LoginHelper;
import org.springframework.stereotype.Service;
import com.hospital.cycle.domain.bo.CycleGroupBo;
import com.hospital.cycle.domain.vo.CycleGroupVo;
import com.hospital.cycle.domain.CycleGroup;
import com.hospital.cycle.mapper.CycleGroupMapper;
import com.hospital.cycle.service.ICycleGroupService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

import static com.hospital.cycle.constant.CycleConstant.*;

/**
 * 轮转规则组Service业务层处理
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@RequiredArgsConstructor
@Service
public class CycleGroupServiceImpl implements ICycleGroupService {

    private final CycleGroupMapper baseMapper;
    private final CycleRuleMapper ruleMapper;
    private final CycleGroupDeptMapper groupDeptMapper;

    /**
     * 查询轮转规则组
     */
    @Override
    public CycleGroupVo queryById(Long groupId){
        return baseMapper.selectVoById(groupId);
    }

    /**
     * 查询轮转规则组列表
     */
    @Override
    public TableDataInfo<CycleGroupVo> queryPageList(CycleGroupBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CycleGroup> lqw = buildLambdaQueryWrapper(bo);
        Page<CycleGroupVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询轮转规则组列表
     */
    @Override
    public List<CycleGroupVo> queryList(CycleGroupBo bo) {
        return baseMapper.selectListWithDept(this.buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<CycleGroup> buildLambdaQueryWrapper(CycleGroupBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CycleGroup> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getRuleId() != null, CycleGroup::getRuleId, bo.getRuleId());
        lqw.like(StringUtils.isNotBlank(bo.getGroupName()), CycleGroup::getGroupName, bo.getGroupName());
        lqw.eq(bo.getBaseId() != null, CycleGroup::getBaseId, bo.getBaseId());
        lqw.eq(StringUtils.isNotBlank(bo.getGroupType()), CycleGroup::getGroupType, bo.getGroupType());
        lqw.eq(StringUtils.isNotBlank(bo.getGroupMethod()), CycleGroup::getGroupMethod, bo.getGroupMethod());
        lqw.eq(bo.getGroupUnitNum() != null, CycleGroup::getGroupUnitNum, bo.getGroupUnitNum());
        lqw.eq(bo.getMethodNumber() != null, CycleGroup::getMethodNumber, bo.getMethodNumber());
        return lqw;
    }

    private QueryWrapper<CycleGroup> buildQueryWrapper(CycleGroupBo bo) {
        Map<String, Object> params = bo.getParams();
        QueryWrapper<CycleGroup> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(bo.getGroupName()), "cg.group_name", bo.getGroupName())
            .eq(bo.getRuleId()!=null,"cg.rule_id",bo.getRuleId())
            .eq(bo.getBaseId()!=null,"cg.base_id",bo.getBaseId())
            .eq(StringUtils.isNotBlank(bo.getGroupType()), "cg.group_type", bo.getGroupType())
            .eq(StringUtils.isNotBlank(bo.getGroupMethod()), "cg.group_method", bo.getGroupMethod())
            .like(!ObjectUtil.isEmpty(params.get("deptName"))&&!ObjectUtil.equals("",params.get("deptName")), "sd.dept_name", params.get("deptName"));
        return wrapper;
    }


    /**
     * 新增轮转规则组
     */
    @Override
    public Boolean insertByBo(CycleGroupBo bo) {
        CycleGroup add = MapstructUtils.convert(bo, CycleGroup.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setRuleId(add.getRuleId());
        }
        return flag;
    }

    /**
     * 修改轮转规则组
     */
    @Override
    public Boolean updateByBo(CycleGroupBo bo) {
        CycleGroup update = MapstructUtils.convert(bo, CycleGroup.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CycleGroup entity){
        //校验规则
        CycleRule cycleRule = ruleMapper.selectById(entity.getRuleId());
        if (cycleRule == null) {
            throw new ServiceException("规则不存在");
        }
        if (CYCLE_STATUS_COMPLETE.equals(cycleRule.getRuleStatus())) {
            throw new ServiceException("规则已经排班，不能修改");
        }
        //如果规则开启了专业,则组必须选择专业
        if(YES.equalsIgnoreCase(cycleRule.getBaseFlag())){
            if(entity.getBaseId() == null){
                throw new ServiceException("请选择专业");
            }
        }else {
            if (entity.getBaseId()!=null){
                throw new ServiceException("规则不允许选择专业");
            }
        }
        //如果是必修，则不能选择方法3
        if(CYCLE_GROUP_MUST.equals(entity.getGroupType())){
            if(CYCLE_GROUP_METHOD_TIME.equals(entity.getGroupMethod())){
                throw new ServiceException("必修科室不能使用时间满足方法");
            }
        }
        //如果是任选其几，则任选数不能为空
        if(CYCLE_GROUP_METHOD_ELECTIVE.equals(entity.getGroupMethod())){
            if(entity.getMethodNumber() == null){
                throw new ServiceException("任选数不能为空");
            }
        }else {
            if(entity.getMethodNumber() != null){
                throw new ServiceException("非任选其几方法不能选择任选数");
            }
        }
        //校验同名
        Long count = baseMapper.selectCount(new QueryWrapper<CycleGroup>()
            .eq("rule_id", entity.getRuleId())
            .eq("group_name", entity.getGroupName())
            .ne(entity.getGroupId()!=null,"group_id",entity.getGroupId()));
        if (count>0){
            throw new ServiceException("同规则下的规则组名称请勿重复");
        }

        if (entity.getGroupId()!=null){//修改校验
            //校验是否有关联数据
            List<CycleGroupDept> groupDepts = groupDeptMapper.selectList(new QueryWrapper<CycleGroupDept>().eq("group_id", entity.getGroupId()));
            if (CollectionUtil.isNotEmpty(groupDepts)){
                throw new ServiceException("请删除其下科室在进行修改");
            }
        }

    }

    /**
     * 批量删除轮转规则组
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //校验规则是否已完成排班，完成排班则不能删除
            CycleRule cycleRule = ruleMapper.selectById(baseMapper.selectById(ids.iterator().next()).getRuleId());
            if (CYCLE_STATUS_COMPLETE.equals(cycleRule.getRuleStatus())) {
                throw new ServiceException("规则已经排班，不能删除");
            }
            //校验是否有关联数据
            List<CycleGroupDept> groupDepts = groupDeptMapper.selectList(new QueryWrapper<CycleGroupDept>().in("group_id", ids));
            if (CollectionUtil.isNotEmpty(groupDepts)) {
                throw new ServiceException("请删除其下科室在进行删除");
            }
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
