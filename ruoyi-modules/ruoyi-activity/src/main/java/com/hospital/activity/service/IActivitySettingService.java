package com.hospital.activity.service;

import com.hospital.activity.domain.ActivitySetting;
import com.hospital.activity.domain.vo.ActivitySettingVo;
import com.hospital.activity.domain.bo.ActivitySettingBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 教学活动Service接口
 *
 * @author Lion Li
 * @date 2023-06-21
 */
public interface IActivitySettingService {

    /**
     * 查询教学活动
     */
    ActivitySettingVo queryById(Long id);

    /**
     * 查询教学活动列表
     */
    TableDataInfo<ActivitySettingVo> queryPageList(ActivitySettingBo bo, PageQuery pageQuery);

    /**
     * 查询教学活动列表
     */
    List<ActivitySettingVo> queryList(ActivitySettingBo bo);

    /**
     * 新增教学活动
     */
    Boolean insertByBo(ActivitySettingBo bo);

    /**
     * 修改教学活动
     */
    void updateByBo(ActivitySettingBo bo);

    /**
     * 校验并批量删除教学活动信息
     */
    void deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 校验状态修改
     */

    List<ActivitySettingVo> getExportList(ActivitySettingBo bo);

    void updateActivityStatus(Long id, String activityStatus);

    void updateActivitySign(Long id, String signStatus);
}
