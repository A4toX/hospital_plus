package com.hospital.cycle.domain.bo;

import com.hospital.cycle.domain.CycleUserDept;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 轮转学生选课记录业务对象 cycle_user_dept
 *
 * @author yaoyingjie
 * @date 2023-06-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CycleUserDept.class, reverseConvertGenerate = false)
public class CycleUserDeptBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 科室组id
     */
    @NotNull(message = "科室组id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long groupDeptId;

    /**
     * 规则id
     */
    @NotNull(message = "规则id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long ruleId;

    /**
     * 组id
     */
    @NotNull(message = "组id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long groupId;

    /**
     * 科室id
     */
    @NotNull(message = "科室id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deptId;

    /**
     * 选修时长
     */
    @NotNull(message = "选修时长不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer selectTime;


}
