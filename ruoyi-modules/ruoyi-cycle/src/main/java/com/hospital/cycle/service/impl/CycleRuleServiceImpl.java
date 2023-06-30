package com.hospital.cycle.service.impl;

import com.hospital.cycle.domain.bo.CycleGroupBo;
import com.hospital.cycle.domain.bo.CycleRuleBaseBo;
import com.hospital.cycle.domain.vo.calc.CycleDeptCalcVo;
import com.hospital.cycle.domain.vo.CycleGroupVo;
import com.hospital.cycle.domain.vo.calc.CycleStudentCalcVo;
import com.hospital.cycle.service.ICycleGroupService;
import com.hospital.cycle.service.ICycleRuleBaseService;
import com.hospital.cycle.utils.CycleCalcUtils;
import com.hospital.cycle.utils.CycleUtils;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.service.StudentService;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.mybatis.helper.DataBaseHelper;
import org.springframework.stereotype.Service;
import com.hospital.cycle.domain.bo.CycleRuleBo;
import com.hospital.cycle.domain.vo.CycleRuleVo;
import com.hospital.cycle.domain.CycleRule;
import com.hospital.cycle.mapper.CycleRuleMapper;
import com.hospital.cycle.service.ICycleRuleService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

import static com.hospital.cycle.constant.CycleConstant.*;

/**
 * 轮转规则Service业务层处理
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@RequiredArgsConstructor
@Service
public class CycleRuleServiceImpl implements ICycleRuleService {

    private final CycleRuleMapper baseMapper;
    private final ICycleRuleBaseService cycleRuleBaseService;
    private final ICycleGroupService cycleGroupService;
    private final StudentService studentService;
    /**
     * 查询轮转规则
     */
    @Override
    public CycleRuleVo queryById(Long ruleId){
        CycleRuleVo vo = baseMapper.selectVoById(ruleId);
        if (vo != null){
            if (YES.equals(vo.getBaseFlag())){
                CycleRuleBaseBo bo = new CycleRuleBaseBo();
                bo.setRuleId(ruleId);
                vo.setBaseList(cycleRuleBaseService.queryList(bo));
            }else {//不分专业，直接把所有group放进去
                CycleGroupBo bo = new CycleGroupBo();
                bo.setRuleId(ruleId);
                vo.setCycleGroupList(cycleGroupService.queryList(bo));
            }
        }
        return vo;
    }

    /**
     * 查询轮转规则列表
     */
    @Override
    public TableDataInfo<CycleRuleVo> queryPageList(CycleRuleBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CycleRule> lqw = buildQueryWrapper(bo);
        Page<CycleRuleVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        //封装子规则
        result.getRecords().forEach(cycleRuleVo -> {
            List<CycleRuleVo> childList = baseMapper.selectVoList(Wrappers.<CycleRule>lambdaQuery().like(CycleRule::getAncestors, cycleRuleVo.getRuleId()));
            if (!childList.isEmpty()){
                cycleRuleVo.setChildList(childList);
            }
        });
        return TableDataInfo.build(result);
    }

    /**
     * 查询轮转规则列表
     */
    @Override
    public List<CycleRuleVo> queryList(CycleRuleBo bo) {
        LambdaQueryWrapper<CycleRule> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CycleRule> buildQueryWrapper(CycleRuleBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CycleRule> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getBaseFlag()), CycleRule::getBaseFlag, bo.getBaseFlag());
        lqw.eq(CycleRule::getParentId, 0L);
        lqw.like(StringUtils.isNotBlank(bo.getRuleName()), CycleRule::getRuleName, bo.getRuleName());
        lqw.eq(StringUtils.isNotBlank(bo.getRuleUnit()), CycleRule::getRuleUnit, bo.getRuleUnit());
        lqw.eq(StringUtils.isNotBlank(bo.getHolidays()), CycleRule::getHolidays, bo.getHolidays());
        lqw.eq(bo.getStageIndex() != null, CycleRule::getStageIndex, bo.getStageIndex());
        lqw.eq(StringUtils.isNotBlank(bo.getRuleYear()), CycleRule::getRuleYear, bo.getRuleYear());
        lqw.eq(StringUtils.isNotBlank(bo.getStartDate()), CycleRule::getStartDate, bo.getStartDate());
        lqw.eq(StringUtils.isNotBlank(bo.getRuleValid()), CycleRule::getRuleValid, bo.getRuleValid());
        lqw.eq(StringUtils.isNotBlank(bo.getRuleStatus()), CycleRule::getRuleStatus, bo.getRuleStatus());
        return lqw;
    }

   /* private QueryWrapper<CycleRule> buildQueryWrapperByChild(CycleRuleBo bo) {
        Map<String, Object> params = bo.getParams();
        QueryWrapper<CycleRule> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(bo.getRuleName()), "p.rule_name", bo.getRuleName())
            .eq(StringUtils.isNotBlank(bo.getRuleYear()), "p.rule_year", bo.getRuleYear())
            .eq(StringUtils.isNotBlank(bo.getRuleStatus()), "p.rule_status", bo.getRuleStatus())
            .eq("p.parent_id", 0)
            ;
        return wrapper;
    }*/

    /**
     * 新增轮转规则
     */
    @Override
    public Boolean insertByBo(CycleRuleBo bo) {
        CycleRule add = MapstructUtils.convert(bo, CycleRule.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setRuleId(add.getRuleId());
        }
        return flag;
    }

    /**
     * 修改轮转规则
     */
    @Override
    public Boolean updateByBo(CycleRuleBo bo) {
        CycleRule update = MapstructUtils.convert(bo, CycleRule.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CycleRule entity){

        if (entity.getRuleId()!=null){
            //同年份轮转规，除了上级规则外不能重复
            List<CycleRule> cycleRepeatList = baseMapper.selectList(
                Wrappers.<CycleRule>lambdaQuery()
                    .eq(CycleRule::getRuleYear, entity.getRuleYear()) // 筛选 ruleYear 相等的记录
                    .ne(CycleRule::getRuleId, entity.getRuleId()) // 排除 ruleId 相等的记录
                    .notLike(CycleRule::getAncestors, "%" + entity.getRuleId() + "%") // 筛选祖级 id 列表中不包含 ruleId 的记录
                    .eq(CycleRule::getRuleName, entity.getRuleName()) // 筛选 ruleName 相等的记录
            );
            if (!cycleRepeatList.isEmpty()){
                throw new ServiceException("同年份轮转规则名称不能重复");
            }

            //如果状态为已排完则不能修改
            CycleRule cycleRule = baseMapper.selectById(entity.getRuleId());
            if (CYCLE_STATUS_COMPLETE.equals(cycleRule.getRuleStatus())) {
                throw new ServiceException("已排完的规则不能修改");
            }

        }else {//新增
            if (entity.getParentId()!=null){//新增阶段
                //获取父级规则
                CycleRule parentRule = baseMapper.selectById(entity.getParentId());
                //同年份轮转规则名称只能和父级相同，不能和其他规则相同
                List<CycleRule> cycleRepeatList1 = baseMapper.selectList(
                    Wrappers.<CycleRule>lambdaQuery()
                        .eq(CycleRule::getRuleYear, parentRule.getRuleYear()) // 筛选 ruleYear 相等的记录
                        .eq(CycleRule::getRuleName, entity.getRuleName()) // 筛选 ruleName 相等的记录
                        .ne(CycleRule::getRuleId, entity.getParentId()) // 排除 ruleId 相等的记录
                        .notIn(CycleRule::getRuleId, parentRule.getAncestors())); // 筛选 ruleId 不在祖级 id 列表中的记录
                if (!cycleRepeatList1.isEmpty()){
                    throw new ServiceException("阶段不能和上级以外的规则同名");
                }

                //如果有子规则则不能新增
                List<CycleRule> cycleRuleList = baseMapper.selectList(Wrappers.<CycleRule>lambdaQuery().eq(CycleRule::getParentId, entity.getParentId()));
                if (!cycleRuleList.isEmpty()){
                    throw new ServiceException("请在最终阶段下新增阶段");
                }
            }else {//新增第一阶段
                //同年份轮转规则不能重复
                List<CycleRule> cycleRepeatList = baseMapper.selectList(Wrappers.<CycleRule>lambdaQuery().eq(CycleRule::getRuleYear, entity.getRuleYear()).eq(CycleRule::getRuleName, entity.getRuleName()));
                if (!cycleRepeatList.isEmpty()){
                    throw new ServiceException("同年份轮转规则名称不能重复");
                }
            }


        }
    }

    /**
     * 批量删除轮转规则
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //如果状态为已排完则不能删除
            List<CycleRule> cycleRules = baseMapper.selectBatchIds(ids);
            cycleRules.forEach(cycleRule -> {
                if (CYCLE_STATUS_COMPLETE.equals(cycleRule.getRuleStatus())) {
                    throw new ServiceException("已排完的规则不能删除");
                }
                //如果有子规则则不能删除
                LambdaQueryWrapper<CycleRule> lqw = Wrappers.lambdaQuery();
                lqw.eq(CycleRule::getParentId, cycleRule.getRuleId());
                Long count = baseMapper.selectCount(lqw);
                if (count > 0) {
                    throw new ServiceException("有子规则的规则不能删除");
                }
            });
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public void addStage(CycleRuleBo bo){
        CycleRule add = MapstructUtils.convert(bo, CycleRule.class);
        validEntityBeforeSave(add);
        CycleRule info = baseMapper.selectById(bo.getParentId());
        add.setAncestors(info.getAncestors() + StringUtils.SEPARATOR + info.getRuleId());
        add.setRuleUnit(info.getRuleUnit());
        add.setStageIndex(info.getStageIndex() + 1);
        add.setRuleYear(info.getRuleYear());
        baseMapper.insert(add);
    }


    @Override
    public List<CycleRuleVo> queryStudentSelectDept(Long userId){
        //获取学生所在的轮转规则以及其阶段规则
//        CycleRuleVo cycleRuleVo = baseMapper.queryUserRuleWithStage(LoginHelper.getUserId());
        CycleRuleVo cycleRuleVo = baseMapper.queryUserRuleWithStage(userId);
       if (cycleRuleVo==null){
           return null;
       }
        //获取其下的阶段规则
        List<CycleRuleVo> stageList = baseMapper.selectVoList(Wrappers.<CycleRule>lambdaQuery()
            .apply(DataBaseHelper.findInSet(cycleRuleVo.getRuleId(), "ancestors"))
            .eq(CycleRule::getDeptSelectFlag, YES));
       if (YES.equals(cycleRuleVo.getDeptSelectFlag())){
           stageList.add(0,cycleRuleVo);
       }
        //遍历获取其下所有的科室
        stageList.forEach(stage ->{
            //如果不需要选科室则直接返回
            if (NO.equals(stage.getDeptSelectFlag())){
                return;
            }
            //组装查询对象
            CycleGroupBo cycleGroupBo = new CycleGroupBo();
            cycleGroupBo.setRuleId(stage.getRuleId());//规则id
            cycleGroupBo.setGroupType(CYCLE_GROUP_ELECTIVE);//选修
            if (YES.equals(stage.getBaseFlag())){//如果开启了专业，只查对应专业下的科室
//                cycleGroupBo.setBaseId(studentService.selectStudentBaseIdByUserId(LoginHelper.getUserId()));
                cycleGroupBo.setBaseId(studentService.selectStudentBaseIdByUserId(userId));
                List<CycleGroupVo> cycleGroupVoList = cycleGroupService.queryList(cycleGroupBo);
                if (!cycleGroupVoList.isEmpty()){
                    stage.setCycleGroupList(cycleGroupVoList);
                }
            }
        });
        return stageList;
    }

    @Override
    public void startCycle(Long ruleId){
        //获取规则
        CycleRule cycleRule = baseMapper.selectById(ruleId);
        //如果规则状态为未通过校验则不能开始
        if(NO.equals(cycleRule.getRuleValid())){
            throw new ServiceException("规则未通过校验，不能开始");
        }
        //如果规则状态为已排完则不能开始
        if(CYCLE_STATUS_COMPLETE.equals(cycleRule.getRuleStatus())){
            throw new ServiceException("规则已排完，请选择重新排班");
        }
        if (cycleRule.getParentId()!=0){
            //如果不是第一阶段则需要判断上一阶段是否已排完
            CycleRule parentRule = baseMapper.selectById(cycleRule.getParentId());
            if (CYCLE_STATUS_STAFF.equals(parentRule.getRuleStatus())){
                throw new ServiceException("上一阶段未排完，不能开始");
            }
        }
        //初始化规则中的所有学生
        List<CycleStudentCalcVo> allStudentList = CycleCalcUtils.calcAllStudent(ruleId);
        //获取规则的轮转时间
        Integer ruleTotalTimeUnit = CycleCalcUtils.getTotalTimeUnit(ruleId);
        //初始化规则科室
        List<CycleDeptCalcVo> allDeptList = CycleCalcUtils.calcAllDept(ruleId,ruleTotalTimeUnit);
        //排班
        CycleCalcUtils.calc(allStudentList,allDeptList,ruleId,ruleTotalTimeUnit);
    }

    @Override
    public void initStudent(Long ruleId){
        CycleUtils.initStudentDept(ruleId);
    }

}
