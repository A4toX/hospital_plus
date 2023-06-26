package com.hospital.activity.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital.activity.domain.ActivitySetting;
import com.hospital.activity.domain.vo.ActivitySettingVo;
import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 教学活动Mapper接口
 *
 * @author Lion Li
 * @date 2023-06-21
 */
public interface ActivitySettingMapper extends BaseMapperPlus<ActivitySetting, ActivitySettingVo> {

    Page<ActivitySettingVo> selectactivityPage(@Param("page") Page<Object> page, @Param(Constants.WRAPPER)QueryWrapper<ActivitySetting> queryWrapper);

    List<ActivitySettingVo> selectactivityList(@Param(Constants.WRAPPER)QueryWrapper<ActivitySetting> activitySettingQueryWrapper);
}
