package com.hospital.activity.domain.bo;

import com.hospital.activity.domain.ActivityTeacher;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import org.dromara.common.mybatis.core.domain.OnlySeachBaseEntity;

/**
 * 教学活动教师业务对象 activity_teacher
 *
 * @author Lion Li
 * @date 2023-06-21
 */
@Data
@AutoMapper(target = ActivityTeacher.class, reverseConvertGenerate = false)
public class ActivityTeacherBo extends OnlySeachBaseEntity {

    /**
     * id
     */
    @NotNull(message = "id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 活动id
     */
    @NotNull(message = "活动id不能为空", groups = { AddGroup.class})
    private Long activityId;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class})
    private Long userId;

    /**
     * 类型 1主讲老师 2助理老师
     */
    @NotBlank(message = "类型 1主讲老师 2助理老师不能为空", groups = { AddGroup.class})
    private String teacherType;


}
