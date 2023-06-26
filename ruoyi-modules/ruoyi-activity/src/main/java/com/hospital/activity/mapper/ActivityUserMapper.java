package com.hospital.activity.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital.activity.domain.ActivityUser;
import com.hospital.activity.domain.vo.ActivityUserVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

/**
 * 教学活动参与用户Mapper接口
 *
 * @author yyj
 * @date 2023-06-21
 */
public interface ActivityUserMapper extends BaseMapperPlus<ActivityUser, ActivityUserVo> {

    Page<ActivityUserVo> selectUserSign(@Param("page") Page<Object> page, @Param(Constants.WRAPPER)QueryWrapper<ActivityUser> activityUserQueryWrapper);
}
