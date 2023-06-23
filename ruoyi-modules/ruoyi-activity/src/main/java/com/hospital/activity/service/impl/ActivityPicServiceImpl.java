package com.hospital.activity.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import org.dromara.system.mapper.SysUserMapper;
import org.springframework.stereotype.Service;
import com.hospital.activity.domain.bo.ActivityPicBo;
import com.hospital.activity.domain.vo.ActivityPicVo;
import com.hospital.activity.domain.ActivityPic;
import com.hospital.activity.mapper.ActivityPicMapper;
import com.hospital.activity.service.IActivityPicService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 教学活动图片Service业务层处理
 *
 * @author Lion Li
 * @date 2023-06-21
 */
@RequiredArgsConstructor
@Service
public class ActivityPicServiceImpl implements IActivityPicService {

    private final ActivityPicMapper baseMapper;

    /**
     * 查询教学活动图片
     */
    @Override
    public ActivityPicVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询教学活动图片列表
     */
    @Override
    public TableDataInfo<ActivityPicVo> queryPageList(ActivityPicBo bo, PageQuery pageQuery) {
        Page<ActivityPicVo> result = baseMapper.selectWithUserInfo(pageQuery.build(), this.buildQueryWrapper(bo));
        return TableDataInfo.build(result);
    }

    /**
     * 查询教学活动图片列表
     */
    @Override
    public List<ActivityPicVo> queryList(ActivityPicBo bo) {
        return baseMapper.selectVoList(this.buildQueryWrapper(bo));
    }

   /* private LambdaQueryWrapper<ActivityPic> buildQueryWrapper(ActivityPicBo bo) {
        LambdaQueryWrapper<ActivityPic> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getActivityId() != null, ActivityPic::getActivityId, bo.getActivityId());
        return lqw;
    }
*/
    private QueryWrapper<ActivityPic> buildQueryWrapper(ActivityPicBo bo) {
        Map<String, Object> params = bo.getParams();
        QueryWrapper<ActivityPic> wrapper = new QueryWrapper<>();
        wrapper.eq(bo.getActivityId() != null, "ap.activity_id", bo.getActivityId());
        return wrapper;
    }

    /**
     * 新增教学活动图片
     */
    @Override
    public void insertByBo(List<ActivityPicBo> bos) {
        List<ActivityPic> picList = MapstructUtils.convert(bos, ActivityPic.class);
        picList.forEach(pic -> {
            validEntityBeforeSave(pic);
            pic.setUploadUser(LoginHelper.getUserId());
            pic.setUploadTime(DateUtil.now());
        });
        baseMapper.insertBatch(picList);
    }

    /**
     * 修改教学活动图片
     */
    @Override
    public Boolean updateByBo(ActivityPicBo bo) {
        ActivityPic update = MapstructUtils.convert(bo, ActivityPic.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ActivityPic entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除教学活动图片
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
