package com.hospital.cycle.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.hospital.cycle.domain.CycleGroupDept;
import com.hospital.cycle.domain.vo.CycleGroupDeptVo;
import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 轮转规则组关联科室Mapper接口
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
public interface CycleGroupDeptMapper extends BaseMapperPlus<CycleGroupDept, CycleGroupDeptVo> {

    List<CycleGroupDeptVo> selectWithDeptName(@Param(Constants.WRAPPER) QueryWrapper<CycleGroupDept> cycleGroupDeptQueryWrapper);
}
