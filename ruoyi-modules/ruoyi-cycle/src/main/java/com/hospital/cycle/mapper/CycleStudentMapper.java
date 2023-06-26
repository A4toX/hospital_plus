package com.hospital.cycle.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital.cycle.domain.CycleStudent;
import com.hospital.cycle.domain.vo.CycleStudentVo;
import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 学员规则关联Mapper接口
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
public interface CycleStudentMapper extends BaseMapperPlus<CycleStudent, CycleStudentVo> {

    Page<CycleStudentVo> selectPageWithStudentInfo(@Param("page") Page<Object> page, @Param(Constants.WRAPPER) QueryWrapper<CycleStudent> cycleStudentQueryWrapper);

//    List<CycleStudentVo> selectListWithStudentInfo(@Param(Constants.WRAPPER)QueryWrapper<CycleStudent> cycleStudentQueryWrapper);
}
