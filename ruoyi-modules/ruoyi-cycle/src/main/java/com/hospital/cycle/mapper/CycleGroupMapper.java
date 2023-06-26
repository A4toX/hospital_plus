package com.hospital.cycle.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.hospital.cycle.domain.CycleGroup;
import com.hospital.cycle.domain.vo.CycleGroupVo;
import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 轮转规则组Mapper接口
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
public interface CycleGroupMapper extends BaseMapperPlus<CycleGroup, CycleGroupVo> {

    List<CycleGroupVo> selectListWithDept(@Param(Constants.WRAPPER) QueryWrapper<CycleGroup> cycleGroupQueryWrapper);
}
