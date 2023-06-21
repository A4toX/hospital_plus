package com.hospital.activity.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dromara.common.tenant.core.TenantEntity;

/**
 * 教学活动主表(ActivitySetting)
 *
 * @author makejava
 * @since 2023-06-21 14:17:47
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("activity_setting")
public class ActivitySetting extends TenantEntity {

    /**
     * 主键
     */
    @TableId
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

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

}

