package com.hospital.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.service.BaseServiceImpl;
import com.hospital.activity.domain.ActivitySetting;
import com.hospital.activity.domain.bo.ActivitySettingBo;
import com.hospital.activity.domain.vo.ActivitySettingVo;
import com.hospital.activity.mapper.ActivitySettingMapper;
import com.hospital.activity.service.ActivitySettingService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 教学活动主表(ActivitySetting) 服务实现
 *
 * @author makejava
 * @since 2023-06-21 14:17:47
 */
@RequiredArgsConstructor
@Service
public class ActivitySettingServiceImpl extends BaseServiceImpl<ActivitySettingMapper, ActivitySetting, ActivitySettingVo, ActivitySettingBo> implements ActivitySettingService {

    @Override
    public TableDataInfo<ActivitySettingVo> selectPageList(ActivitySettingBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ActivitySetting> lqw = buildQueryWrapper(bo);
        Page<ActivitySettingVo> page = mapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    @Override
    public List<ActivitySettingVo> selectList(ActivitySettingBo bo) {
        LambdaQueryWrapper<ActivitySetting> lqw = buildQueryWrapper(bo);
        return mapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ActivitySetting> buildQueryWrapper(ActivitySettingBo bo) {
        return new LambdaQueryWrapperX<ActivitySetting>()
                .eqIfPresent(ActivitySetting::getType, bo.getType())
                .eqIfPresent(ActivitySetting::getDeptId, bo.getDeptId())
                .eqIfPresent(ActivitySetting::getStartTime, bo.getStartTime())
                .eqIfPresent(ActivitySetting::getEndTime, bo.getEndTime())
                .eqIfPresent(ActivitySetting::getActivityImg, bo.getActivityImg())
                .eqIfPresent(ActivitySetting::getActivityContent, bo.getActivityContent())
                .eqIfPresent(ActivitySetting::getActivityStatus, bo.getActivityStatus())
                .eqIfPresent(ActivitySetting::getActivityArea, bo.getActivityArea())
                .eqIfPresent(ActivitySetting::getIsSign, bo.getIsSign())
                .eqIfPresent(ActivitySetting::getCreateDept, bo.getCreateDept())
        ;
    }
}
