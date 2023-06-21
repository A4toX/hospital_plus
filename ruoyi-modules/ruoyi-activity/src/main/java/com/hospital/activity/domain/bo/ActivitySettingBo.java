package com.hospital.activity.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.hospital.activity.domain.ActivitySetting;

/**
 * 教学活动主表(ActivitySetting) 参数对象
 *
 * @author makejava
 * @since 2023-06-21 14:17:47
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ActivitySetting.class, reverseConvertGenerate = false)
public class ActivitySettingBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 类型
     */
    private String type;

    /**
     * 活动科室id
     */
    private Long deptId;

    /**
     * 活动开始时间
     */
    private String startTime;

    /**
     * 活动结束时间
     */
    private String endTime;

    /**
     * 活动封面
     */
    private String activityImg;

    /**
     * 富文本
     */
    private String activityContent;

    /**
     * 活动状态 1草稿 2已经发布 3已结束 4已下线
     */
    private String activityStatus;

    /**
     * 活动地点
     */
    private String activityArea;

    /**
     * 是否开启签到
     */
    private String isSign;

}

