package com.hospital.cycle.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.hospital.cycle.domain.CycleRuleBase;
import com.hospital.cycle.domain.vo.CycleRuleBaseVo;
import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 规则专业关联Mapper接口
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
public interface CycleRuleBaseMapper extends BaseMapperPlus<CycleRuleBase, CycleRuleBaseVo> {

    List<CycleRuleBaseVo> selectWithBaseName(@Param(Constants.WRAPPER) QueryWrapper<CycleRuleBase> lqw);
}
