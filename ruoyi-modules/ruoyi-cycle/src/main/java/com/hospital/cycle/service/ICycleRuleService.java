package com.hospital.cycle.service;

import com.hospital.cycle.domain.CycleRule;
import com.hospital.cycle.domain.vo.CycleRuleVo;
import com.hospital.cycle.domain.bo.CycleRuleBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 轮转规则Service接口
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
public interface ICycleRuleService {

    /**
     * 查询轮转规则
     */
    CycleRuleVo queryById(Long ruleId);

    /**
     * 查询轮转规则列表
     */
    TableDataInfo<CycleRuleVo> queryPageList(CycleRuleBo bo, PageQuery pageQuery);

    /**
     * 查询轮转规则列表
     */
    List<CycleRuleVo> queryList(CycleRuleBo bo);

    /**
     * 新增轮转规则
     */
    Boolean insertByBo(CycleRuleBo bo);

    /**
     * 修改轮转规则
     */
    Boolean updateByBo(CycleRuleBo bo);

    /**
     * 校验并批量删除轮转规则信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    void addStage(CycleRuleBo bo);

    List<CycleRuleVo> queryStudentSelectDept(Long userId);

    void startCycle(Long ruleId);
}
