package com.hospital.activity.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hospital.activity.domain.ActivitySetting;
import com.hospital.activity.domain.ActivityTeacher;
import com.hospital.activity.domain.bo.ActivityTeacherBo;
import com.hospital.activity.mapper.ActivitySettingMapper;
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
import org.springframework.stereotype.Service;
import com.hospital.activity.domain.bo.ActivityUserBo;
import com.hospital.activity.domain.vo.ActivityUserVo;
import com.hospital.activity.domain.ActivityUser;
import com.hospital.activity.mapper.ActivityUserMapper;
import com.hospital.activity.service.IActivityUserService;

import java.rmi.ServerException;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hospital.activity.constant.ActivityConstant.*;

/**
 * 教学活动参与用户Service业务层处理
 *
 * @author yyj
 * @date 2023-06-21
 */
@RequiredArgsConstructor
@Service
public class ActivityUserServiceImpl implements IActivityUserService {

    private final ActivityUserMapper baseMapper;
    private final ActivitySettingMapper settingMapper;

    /**
     * 查询教学活动参与用户
     */
    @Override
    public ActivityUserVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询教学活动参与用户列表
     */
    @Override
    public TableDataInfo<ActivityUserVo> queryPageList(ActivityUserBo bo, PageQuery pageQuery) {
        Page<ActivityUserVo> result = baseMapper.selectUserSign(pageQuery.build(), this.buildQueryWrapper(bo));
        return TableDataInfo.build(result);
    }

    /**
     * 查询教学活动参与用户列表
     */
    @Override
    public List<ActivityUserVo> queryList(ActivityUserBo bo) {
        return baseMapper.selectVoList(this.buildQueryWrapper(bo));
    }

   /* private LambdaQueryWrapper<ActivityUser> buildQueryWrapper(ActivityUserBo bo) {
        LambdaQueryWrapper<ActivityUser> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, ActivityUser::getUserId, bo.getUserId());
        lqw.eq(bo.getActivityId() != null, ActivityUser::getActivityId, bo.getActivityId());
        lqw.eq(StringUtils.isNotBlank(bo.getSignFlag()), ActivityUser::getSignFlag, bo.getSignFlag());
        return lqw;
    }*/

    private QueryWrapper<ActivityUser> buildQueryWrapper(ActivityUserBo bo) {
        Map<String, Object> params = bo.getParams();
        QueryWrapper<ActivityUser> wrapper = new QueryWrapper<>();
        wrapper.eq(bo.getActivityId()!=null,"au.activity_id",bo.getActivityId())
            .eq(bo.getUserId()!=null,"au.user_id",bo.getUserId())
            .eq(StringUtils.isNotBlank(bo.getSignFlag()),"au.sign_flag",bo.getSignFlag())
            .like(ObjectUtil.isNotNull(params.get("realName"))&&!ObjectUtil.equals("",params.get("realName")),"su.real_name",params.get("realName"));
        return wrapper;
    }

    /**
     * 新增教学活动参与用户
     */
    @Override
    public Boolean insertByBo(List<ActivityUserBo> bos) {
        //去重
        bos = bos.stream().distinct().collect(Collectors.toList());
        //获取当前活动中是否已经有人
        Long activityId = bos.get(0).getActivityId();
        validEntityBeforeSave(activityId);
        List<ActivityUser> activityUsers = baseMapper.selectList(Wrappers.<ActivityUser>lambdaQuery().eq(ActivityUser::getActivityId, activityId));
        if (activityUsers.isEmpty()){//如果没有直接新增
            bos.forEach(bo -> {
                ActivityUser add = MapstructUtils.convert(bo, ActivityUser.class);
                baseMapper.insert(add);
            });
            return true;
        }
        List<ActivityUser> nowUser  = MapstructUtils.convert(bos, ActivityUser.class);
        //新增人员
        List<ActivityUser> add = nowUser.stream()
            .filter(user -> activityUsers.stream()
                .noneMatch(activityUser -> ObjectUtil.equals(activityUser.getUserId(), user.getUserId())
                    && ObjectUtil.equals(activityUser.getActivityId(), user.getActivityId())
                )
            )
            .collect(Collectors.toList());
        System.out.println("新增"+add);
        //删除人员
        List<ActivityUser> del = activityUsers.stream()
            .filter(activityUser -> nowUser.stream()
                .noneMatch(user -> ObjectUtil.equals(activityUser.getUserId(), user.getUserId())
                    && ObjectUtil.equals(activityUser.getActivityId(), user.getActivityId())
                )
            )
            .collect(Collectors.toList());
        System.out.println("删除"+del);
        //新增
        if (!add.isEmpty()){
            baseMapper.insertBatch(add);

        }
        //删除
        if (!del.isEmpty()){
            baseMapper.deleteBatchIds(del.stream().map(ActivityUser::getId).collect(Collectors.toList()));

        }
        return true;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Long activityId) {
        ActivitySetting setting = settingMapper.selectById(activityId);
        if (setting == null){
            throw new ServiceException("活动不存在");
        }
        if(!ACTIVITY_STAFF.equals(setting.getActivityStatus())){
            throw new ServiceException("只有草稿状态的活动可以修改");
        }
    }

    /**
     * 修改教学活活动参与人员
     */
    @Override
    public Boolean updateByBo(ActivityUserBo bo) {
        validEntityBeforeSave(bo.getActivityId());
        ActivityUser update = MapstructUtils.convert(bo, ActivityUser.class);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 批量删除教学活动参与用户
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public void usersign(Long acvitityId){
        ActivitySetting setting = settingMapper.selectById(acvitityId);
        if (setting == null){
            throw new ServiceException("活动不存在");
        }
        if (NO.equalsIgnoreCase(setting.getSign())){
            throw new ServiceException("此活动无需签到");
        }
        if(!ACTIVITY_START.equals(setting.getActivityStatus())){
            throw new ServiceException("活动未在开启状态，不可签到");
        }
        if (setting.getStartTime().compareTo(DateUtil.now()) > 0||setting.getEndTime().compareTo(DateUtil.now()) < 0){
            throw new ServiceException("未在签到时间");
        }
        ActivityUser activityUser = baseMapper.selectOne(Wrappers.<ActivityUser>lambdaQuery().eq(ActivityUser::getActivityId, acvitityId).eq(ActivityUser::getUserId, LoginHelper.getUserId()));
        if (activityUser == null){
            throw new ServiceException("用户未参与该活动");
        }
        if (YES.equalsIgnoreCase(activityUser.getSignFlag())){
            throw new ServiceException("用户已签到");
        }
        activityUser.setSignFlag(YES);
        activityUser.setSignTime(DateUtil.now());
        baseMapper.updateById(activityUser);
    }
}
