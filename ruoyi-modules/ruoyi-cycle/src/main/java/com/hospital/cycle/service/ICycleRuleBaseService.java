package com.hospital.cycle.service;

import com.hospital.cycle.domain.CycleRuleBase;
import com.hospital.cycle.domain.vo.CycleRuleBaseVo;
import com.hospital.cycle.domain.bo.CycleRuleBaseBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 规则专业关联Service接口
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
public interface ICycleRuleBaseService {

    /**
     * 查询规则专业关联
     */
    CycleRuleBaseVo queryById(Long ruleBaseId);

    /**
     * 查询规则专业关联列表
     */
    TableDataInfo<CycleRuleBaseVo> queryPageList(CycleRuleBaseBo bo, PageQuery pageQuery);

    /**
     * 查询规则专业关联列表
     */
    List<CycleRuleBaseVo> queryList(CycleRuleBaseBo bo);

    /**
     * 新增规则专业关联
     */
    void insertByBo(List<CycleRuleBaseBo> bos);

    /**
     * 修改规则专业关联
     */
    Boolean updateByBo(CycleRuleBaseBo bo);

    /**
     * 校验并批量删除规则专业关联信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
