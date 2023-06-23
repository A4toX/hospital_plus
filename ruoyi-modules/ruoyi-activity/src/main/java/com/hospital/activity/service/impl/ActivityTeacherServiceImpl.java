package com.hospital.activity.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.hospital.activity.domain.ActivitySetting;
import com.hospital.activity.domain.ActivityUser;
import com.hospital.activity.domain.bo.ActivityUserBo;
import com.hospital.activity.mapper.ActivitySettingMapper;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hospital.activity.domain.bo.ActivityTeacherBo;
import com.hospital.activity.domain.vo.ActivityTeacherVo;
import com.hospital.activity.domain.ActivityTeacher;
import com.hospital.activity.mapper.ActivityTeacherMapper;
import com.hospital.activity.service.IActivityTeacherService;

import java.util.*;
import java.util.stream.Collectors;

import static com.hospital.activity.constant.ActivityConstant.ACTIVITY_STAFF;

/**
 * 教学活动教师Service业务层处理
 *
 * @author Lion Li
 * @date 2023-06-21
 */
@RequiredArgsConstructor
@Service
public class ActivityTeacherServiceImpl implements IActivityTeacherService {

    private final ActivityTeacherMapper baseMapper;
    private final ActivitySettingMapper settingMapper;

    /**
     * 查询教学活动教师
     */
    @Override
    public ActivityTeacherVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询教学活动教师列表
     */
    @Override
    public TableDataInfo<ActivityTeacherVo> queryPageList(ActivityTeacherBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ActivityTeacher> lqw = buildQueryWrapper(bo);
        Page<ActivityTeacherVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询教学活动教师列表
     */
    @Override
    public List<ActivityTeacherVo> queryList(ActivityTeacherBo bo) {
        LambdaQueryWrapper<ActivityTeacher> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ActivityTeacher> buildQueryWrapper(ActivityTeacherBo bo) {
        LambdaQueryWrapper<ActivityTeacher> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getActivityId() != null, ActivityTeacher::getActivityId, bo.getActivityId());
        lqw.eq(bo.getUserId() != null, ActivityTeacher::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getTeacherType()), ActivityTeacher::getTeacherType, bo.getTeacherType());
        return lqw;
    }

    /**
     * 新增教学活动教师
     */
    @Override
    public Boolean insertByBo(List<ActivityTeacherBo> bos) {
        //去重
        bos = bos.stream().distinct().collect(Collectors.toList());
        //获取当前活动中是否已经有人
        Long activityId = bos.get(0).getActivityId();
        validEntityBeforeSave(activityId);
        List<ActivityTeacher> activityTeachers = baseMapper.selectList(Wrappers.<ActivityTeacher>lambdaQuery().eq(ActivityTeacher::getActivityId, activityId));
        if (activityTeachers.isEmpty()){//如果没有直接新增
            bos.forEach(bo -> {
                ActivityTeacher add = MapstructUtils.convert(bo, ActivityTeacher.class);
                baseMapper.insert(add);
            });
            return true;
        }
        List<ActivityTeacher> nowTeachers = MapstructUtils.convert(bos, ActivityTeacher.class);
        //新增人员
        List<ActivityTeacher> add = nowTeachers.stream()
            .filter(teacher -> activityTeachers.stream()
                .noneMatch(activityTeacher -> activityTeacher.getTeacherType().equals(teacher.getTeacherType())
                    && ObjectUtil.equals(activityTeacher.getUserId(), teacher.getUserId())
                    && ObjectUtil.equals(activityTeacher.getActivityId(), teacher.getActivityId())
                )
            )
            .collect(Collectors.toList());

        //删除人员
        List<ActivityTeacher> del = activityTeachers.stream()
            .filter(activityTeacher -> nowTeachers.stream()
                .noneMatch(nowTeacher ->
                    nowTeacher.getTeacherType().equals(activityTeacher.getTeacherType())
                    && ObjectUtil.equals(activityTeacher.getUserId(), nowTeacher.getUserId())
                    && ObjectUtil.equals(activityTeacher.getActivityId(), nowTeacher.getActivityId())
                )
            )
            .collect(Collectors.toList());
        //新增
        if (!add.isEmpty()){
            baseMapper.insertBatch(add);
        }
        //删除
        if (!del.isEmpty()){
            baseMapper.deleteBatchIds(del.stream().map(ActivityTeacher::getId).collect(Collectors.toList()));
        }
        return true;
    }

    /**
     * 修改教学活动教师
     */
    @Override
    public Boolean updateByBo(ActivityTeacherBo bo) {
        ActivityTeacher update = MapstructUtils.convert(bo, ActivityTeacher.class);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Long activityId) {
        ActivitySetting setting = settingMapper.selectById(activityId);
        if (setting == null){
            throw new RuntimeException("活动不存在");
        }
        if(!ACTIVITY_STAFF.equals(setting.getActivityStatus())){
            throw new RuntimeException("只有草稿状态的活动可以修改");
        }
    }


    /**
     * 批量删除教学活动教师
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
