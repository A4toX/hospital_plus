package com.hospital.activity.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;

/**
 * 教学活动图片对象 activity_pic
 *
 * @author Lion Li
 * @date 2023-06-21
 */
@Data
@TableName("activity_pic")
public class ActivityPic{

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 图片文件
     */
    private String pic;

    /**
     * 上传人
     */
    private Long uploadUser;

    /**
     * 上传时间
     */
    private String uploadTime;


}
