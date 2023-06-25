package com.hospital.cycle.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital.cycle.domain.CycleRule;
import com.hospital.cycle.domain.vo.CycleRuleVo;
import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 轮转规则Mapper接口
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
public interface CycleRuleMapper extends BaseMapperPlus<CycleRule, CycleRuleVo> {

//    Page<CycleRuleVo> selectVoWithChild(@Param("page") Page<Object> page, @Param(Constants.WRAPPER) QueryWrapper<CycleRule> cycleRuleQueryWrapper);
}
