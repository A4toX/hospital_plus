package com.hospital.activity.service;

import com.hospital.activity.domain.ActivityPic;
import com.hospital.activity.domain.vo.ActivityPicVo;
import com.hospital.activity.domain.bo.ActivityPicBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 教学活动图片Service接口
 *
 * @author Lion Li
 * @date 2023-06-21
 */
public interface IActivityPicService {

    /**
     * 查询教学活动图片
     */
    ActivityPicVo queryById(Long id);

    /**
     * 查询教学活动图片列表
     */
    TableDataInfo<ActivityPicVo> queryPageList(ActivityPicBo bo, PageQuery pageQuery);

    /**
     * 查询教学活动图片列表
     */
    List<ActivityPicVo> queryList(ActivityPicBo bo);

    /**
     * 新增教学活动图片
     */
    void insertByBo(List<ActivityPicBo> bos);

    /**
     * 修改教学活动图片
     */
    Boolean updateByBo(ActivityPicBo bo);

    /**
     * 校验并批量删除教学活动图片信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
