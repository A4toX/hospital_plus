package com.hospital.cycle.domain.bo;

import com.hospital.cycle.domain.CycleGroupDept;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import org.dromara.common.mybatis.core.domain.OnlySeachBaseEntity;

/**
 * 轮转规则组关联科室业务对象 cycle_group_dept
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CycleGroupDept.class, reverseConvertGenerate = false)
public class CycleGroupDeptBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
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
     * 科室轮转时长
     */
    @NotNull(message = "科室轮转时长不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer deptUnitNum;


    @NotNull(message = "规则组方法不能为空", groups = { AddGroup.class, EditGroup.class })
    private String groupMethod;

    /**
     * 已分配人数
     */
    private Integer assignedNum = 0;




}
