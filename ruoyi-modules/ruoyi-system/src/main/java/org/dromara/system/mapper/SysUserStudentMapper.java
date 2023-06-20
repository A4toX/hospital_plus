package org.dromara.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.dromara.system.domain.SysUser;
import org.dromara.system.domain.SysUserStudent;
import org.dromara.system.domain.vo.SysUserStudentVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 学员Mapper接口
 *
 * @author Lion Li
 * @date 2023-06-19
 */
public interface SysUserStudentMapper extends BaseMapperPlus<SysUserStudent, SysUserStudentVo> {

    Page<SysUserStudentVo> pageStudentList(Page<SysUserStudent> page, @Param(Constants.WRAPPER) Wrapper<SysUserStudent> queryWrapper);

    List<SysUserStudentVo> selectStudentList(@Param(Constants.WRAPPER) Wrapper<SysUserStudent> queryWrapper);
}
