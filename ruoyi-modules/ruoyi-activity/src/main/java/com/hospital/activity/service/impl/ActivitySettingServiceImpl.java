package com.hospital.activity.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hospital.activity.domain.ActivityTeacher;
import com.hospital.activity.domain.ActivityUser;
import com.hospital.activity.mapper.ActivityTeacherMapper;
import com.hospital.activity.mapper.ActivityUserMapper;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.system.domain.SysUser;
import org.dromara.system.domain.vo.SysUserSimpleVo;
import org.dromara.system.mapper.SysDeptMapper;
import org.dromara.system.mapper.SysUserMapper;
import org.dromara.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import com.hospital.activity.domain.bo.ActivitySettingBo;
import com.hospital.activity.domain.vo.ActivitySettingVo;
import com.hospital.activity.domain.ActivitySetting;
import com.hospital.activity.mapper.ActivitySettingMapper;
import com.hospital.activity.service.IActivitySettingService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hospital.activity.constant.ActivityConstant.*;

/**
 * 教学活动Service业务层处理
 *
 * @author Lion Li
 * @date 2023-06-21
 */
@RequiredArgsConstructor
@Service
public class ActivitySettingServiceImpl implements IActivitySettingService {

    private final ActivitySettingMapper baseMapper;
    private final ActivityUserMapper activityUserMapper;
    private final ActivityTeacherMapper teacherMapper;
    private final SysUserMapper userMapper;
    private final SysDeptMapper deptMapper;

    /**
     * 查询教学活动
     */
    @Override
    public ActivitySettingVo queryById(Long id) {
        ActivitySettingVo vo = baseMapper.selectVoById(id);
        //获取所有的参与人
        List<ActivityUser> activityUserList = activityUserMapper.selectList(Wrappers.<ActivityUser>lambdaQuery()
            .eq(ActivityUser::getActivityId, id));
        if (!activityUserList.isEmpty()) {
            Set<Long> userIds = activityUserList.stream().map(ActivityUser::getUserId).collect(Collectors.toSet());
            List<SysUser> sysUsers = userMapper.selectBatchIds(userIds);
            vo.setJoinList(MapstructUtils.convert(sysUsers, SysUserSimpleVo.class));
        }
        //获取所有的讲师
        List<ActivityTeacher> teacherList = teacherMapper.selectList(Wrappers.<ActivityTeacher>lambdaQuery()
            .eq(ActivityTeacher::getActivityId, id));
        if (!teacherList.isEmpty()) {
            Set<Long> userIds = teacherList.stream().map(ActivityTeacher::getUserId).collect(Collectors.toSet());
            List<SysUser> sysUsers = userMapper.selectBatchIds(userIds);
            vo.setTeacherList(MapstructUtils.convert(sysUsers, SysUserSimpleVo.class));
        }

        return vo;
    }

    /**
     * 查询教学活动列表
     */
    @Override
    public TableDataInfo<ActivitySettingVo> queryPageList(ActivitySettingBo bo, PageQuery pageQuery) {
        Page<ActivitySettingVo> result = baseMapper.selectactivityPage(pageQuery.build(), this.buildQueryWrapper(bo));
        return TableDataInfo.build(result);
    }

    /**
     * 查询教学活动列表
     */
    @Override
    public List<ActivitySettingVo> queryList(ActivitySettingBo bo) {
        return baseMapper.selectactivityList(this.buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<ActivitySetting> buildQueryWrapperLqw(ActivitySettingBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ActivitySetting> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getActivityType()), ActivitySetting::getActivityType, bo.getActivityType());
        lqw.eq(bo.getDeptId() != null, ActivitySetting::getDeptId, bo.getDeptId());
        lqw.between(params.get("beginStartTime") != null && params.get("endStartTime") != null,
            ActivitySetting::getStartTime, params.get("beginStartTime"), params.get("endStartTime"));
        lqw.between(params.get("beginEndTime") != null && params.get("endEndTime") != null,
            ActivitySetting::getEndTime, params.get("beginEndTime"), params.get("endEndTime"));
        lqw.eq(StringUtils.isNotBlank(bo.getActivityImg()), ActivitySetting::getActivityImg, bo.getActivityImg());
        lqw.eq(StringUtils.isNotBlank(bo.getActivityContent()), ActivitySetting::getActivityContent, bo.getActivityContent());
        lqw.eq(StringUtils.isNotBlank(bo.getActivityStatus()), ActivitySetting::getActivityStatus, bo.getActivityStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getActivityArea()), ActivitySetting::getActivityArea, bo.getActivityArea());
        lqw.eq(StringUtils.isNotBlank(bo.getSign()), ActivitySetting::getSign, bo.getSign());
        lqw.like(StringUtils.isNotBlank(bo.getActivityName()), ActivitySetting::getActivityName, bo.getActivityName());
        return lqw;
    }


    private QueryWrapper<ActivitySetting> buildQueryWrapper(ActivitySettingBo bo) {
        Map<String, Object> params = bo.getParams();
        QueryWrapper<ActivitySetting> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(bo.getActivityName()), "acts.activity_name", bo.getActivityName())
            .eq(StringUtils.isNotBlank(bo.getActivityType()), "acts.activity_type", bo.getActivityType())
            .eq(StringUtils.isNotBlank(bo.getActivityLevel()), "acts.activity_level", bo.getActivityLevel())
            .eq(bo.getDeptId()!=null, "acts.dept_id", bo.getDeptId())
            .eq(StringUtils.isNotBlank(bo.getActivityStatus()), "acts.activity_status", bo.getActivityStatus())
            .eq(StringUtils.isNotBlank(bo.getSign()), "acts.is_sign", bo.getSign())
            .eq(ObjectUtil.isNotNull(params.get("isJoin"))&&!ObjectUtil.equals(params.get("isJoin"),""), "au.user_id", LoginHelper.getUserId())
            .and(ObjectUtil.isNotNull(params.get("isTeacher"))&&!ObjectUtil.equals(params.get("isTeacher"),""),
                queryWrapper -> queryWrapper
                    .eq("at.user_id", LoginHelper.getUserId())
                    .eq("at.teacher_type", params.get("isTeacher"))
                    .eq("acts.activity_status", ACTIVITY_START))
            .groupBy("acts.id")
        ;
        return wrapper;

    }


    /**
     * 新增教学活动
     */
    @Override
    public Boolean insertByBo(ActivitySettingBo bo) {
        ActivitySetting add = MapstructUtils.convert(bo, ActivitySetting.class);
        validEntityBeforeSave(add);
        add.setActivityStatus(ACTIVITY_STAFF);//默认草稿状态
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改教学活动
     */
    @Override
    public void updateByBo(ActivitySettingBo bo) {
        ActivitySetting update = MapstructUtils.convert(bo, ActivitySetting.class);
        validEntityBeforeSave(update);
        baseMapper.updateById(update);
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ActivitySetting entity) {

    }

    /**
     * 批量删除教学活动
     */
    @Override
    public void deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        baseMapper.deleteBatchIds(ids);
    }



    @Override
    public List<ActivitySettingVo> getExportList(ActivitySettingBo bo) {
        List<ActivitySettingVo> voList = this.queryList(bo);
        if (!voList.isEmpty()) {
            voList.forEach(vo -> {
                if (vo.getDeptId() != null) {
                    vo.setDeptName(deptMapper.selectById(vo.getDeptId()).getDeptName());
                }
            });
        }
        return voList;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateActivityStatus(Long id, String activityStatus){
        ActivitySetting activitySetting = baseMapper.selectById(id);
        if (activityStatus.equalsIgnoreCase(activitySetting.getActivityStatus())) {
            throw new ServiceException("与当前状态相同，无需修改");
        }
        if (ACTIVITY_START.equalsIgnoreCase(activityStatus)) {//发布
            if (!ACTIVITY_STAFF.equalsIgnoreCase(activitySetting.getActivityStatus())) {
                throw new ServiceException("只有草稿状态的活动才能发布");
            }
            List<ActivityUser> activityUserList = activityUserMapper.selectList(new LambdaQueryWrapper<ActivityUser>().eq(ActivityUser::getActivityId, id));
            if (YES.equalsIgnoreCase(activitySetting.getSign())) {
                if (activityUserList.isEmpty()){
                    throw new ServiceException("该活动没有参与人员");
                }
            activitySetting.setStartTime(DateUtil.now());
            activitySetting.setActivityStatus(activityStatus);
            baseMapper.updateById(activitySetting);
            //发布时判断是否需要签到，如果需要签到则将参与人员的签到状态改为未签到
            activityUserList.forEach(activityUser -> {//添加未签到状态
                    activityUser.setSignFlag(NO);
                    activityUserMapper.updateById(activityUser);
                });
            }
        }else if (ACTIVITY_END.equalsIgnoreCase(activityStatus)) {//结束
            if (!ACTIVITY_START.equalsIgnoreCase(activitySetting.getActivityStatus())) {
                throw new ServiceException("只有发布状态的活动才能结束");
            }
            activitySetting.setEndTime(DateUtil.now());
            activitySetting.setActivityStatus(activityStatus);
            baseMapper.updateById(activitySetting);
        }
    }
    @Override
    public void updateActivitySign(Long id, String signStatus){
        if (!ACTIVITY_STAFF.equalsIgnoreCase(baseMapper.selectById(id).getActivityStatus())) {
            throw new ServiceException("只有草稿状态的活动才能修改签到设置");
        }
        ActivitySetting activitySetting = new ActivitySetting();
        activitySetting.setId(id);
        activitySetting.setSign(signStatus);
        baseMapper.updateById(activitySetting);
    }






}
