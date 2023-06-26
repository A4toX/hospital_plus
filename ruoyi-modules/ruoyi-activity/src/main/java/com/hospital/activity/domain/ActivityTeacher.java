package com.hospital.activity.domain;

import jakarta.validation.constraints.NotBlank;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.mybatis.core.domain.OnlySeachBaseEntity;
import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 教学活动教师对象 activity_teacher
 *
 * @author Lion Li
 * @date 2023-06-21
 */
@Data
@TableName("activity_teacher")
public class ActivityTeacher{

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
     * 类型 1主讲老师 2助理老师
     */
    private String teacherType;

}
