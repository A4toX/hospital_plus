package com.hospital.activity.domain.bo;

import com.hospital.activity.domain.ActivitySetting;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 教学活动业务对象 activity_setting
 *
 * @author Lion Li
 * @date 2023-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ActivitySetting.class, reverseConvertGenerate = false)
public class ActivitySettingBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 活动名称
     */
    @NotBlank(message = "活动名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String activityName;

    /**
     * 类型
     */
    @NotBlank(message = "类型不能为空", groups = { AddGroup.class})
    private String activityType;

    /**
     * 活动级别
     */
    @NotBlank(message = "活动级别不能为空", groups = { AddGroup.class})
    private String activityLevel;

    /**
     * 活动科室id
     */
    private Long deptId;

    /**
     * 活动开始时间
     */
    @NotBlank(message = "活动开始时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private String startTime;

    /**
     * 活动结束时间
     */
    @NotBlank(message = "活动结束时间不能为空", groups = { AddGroup.class, EditGroup.class })
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
    @NotBlank(message = "是否开启签到不能为空", groups = { AddGroup.class})
    private String sign;




}
