package com.hospital.activity.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 教学活动对象 activity_setting
 *
 * @author Lion Li
 * @date 2023-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("activity_setting")
public class ActivitySetting extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 类型
     */
    private String activityType;

    /**
     * 级别1院级2科室
     */
    private String ActivityLevel;

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
    private String sign;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
