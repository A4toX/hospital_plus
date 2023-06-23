package com.hospital.activity.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 教学活动参与用户对象 activity_user
 *
 * @author Lion Li
 * @date 2023-06-22
 */
@Data
@TableName("activity_user")
public class ActivityUser{

    /**
     * id
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 是否已经签到
     */
    private String signFlag;

    /**
     * 签到时间
     */
    private String signTime;
}
