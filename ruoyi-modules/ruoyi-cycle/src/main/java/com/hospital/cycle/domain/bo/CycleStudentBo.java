package com.hospital.cycle.domain.bo;

import com.hospital.cycle.domain.CycleStudent;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import org.dromara.common.mybatis.core.domain.OnlySeachBaseEntity;

/**
 * 学员规则关联业务对象 cycle_student
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CycleStudent.class, reverseConvertGenerate = false)
public class CycleStudentBo extends OnlySeachBaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long cycleStudentId;

    /**
     * 学员id
     */
    @NotNull(message = "学员id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 轮转规则id
     */
    @NotNull(message = "轮转规则id不能为空", groups = {EditGroup.class })
    private Long ruleId;

    /**
     * 轮转状态
     */
    @NotBlank(message = "轮转状态不能为空", groups = { EditGroup.class })
    private String studentCycleStatus;


}
