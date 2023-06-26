package com.hospital.activity.domain.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.hospital.activity.domain.ActivityPic;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import org.dromara.common.mybatis.core.domain.OnlySeachBaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 教学活动图片业务对象 activity_pic
 *
 * @author Lion Li
 * @date 2023-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ActivityPic.class, reverseConvertGenerate = false)
public class ActivityPicBo extends OnlySeachBaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 活动id
     */
    @NotNull(message = "活动id不能为空", groups = { AddGroup.class})
    private Long activityId;

    /**
     * 图片文件
     */
    @NotBlank(message = "图片文件不能为空", groups = { AddGroup.class})
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
