package com.hospital.activity.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital.activity.domain.ActivityPic;
import com.hospital.activity.domain.vo.ActivityPicVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

/**
 * 教学活动图片Mapper接口
 *
 * @author Lion Li
 * @date 2023-06-21
 */
public interface ActivityPicMapper extends BaseMapperPlus<ActivityPic, ActivityPicVo> {

    Page<ActivityPicVo> selectWithUserInfo(@Param("page")Page<Object> page, @Param(Constants.WRAPPER)QueryWrapper<ActivityPic> queryWrapper);
}
