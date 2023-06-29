package com.hospital.cycle.domain.bo;

import com.hospital.cycle.domain.CycleGroup;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 轮转规则组业务对象 cycle_group
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CycleGroup.class, reverseConvertGenerate = false)
public class CycleGroupBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long groupId;

    /**
     * 规则id
     */
    @NotNull(message = "规则id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long ruleId;

    /**
     * 组名
     */
    @NotBlank(message = "组名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String groupName;

    /**
     * 专业id
     */
    private Long baseId;

    /**
     * 1必修2选修
     */
    @NotBlank(message = "1必修2选修不能为空", groups = { AddGroup.class, EditGroup.class })
    private String groupType;

    /**
     * 规则方法1必选2任选其几3满足时长，不限数(选轮规则)
     */
    @NotBlank(message = "规则方法不能为空", groups = { AddGroup.class, EditGroup.class })
    private String groupMethod;

    /**
     * 组轮转时长(月或周)
     */
//    @NotNull(message = "组轮转时长(月或周)不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer groupUnitNum;

    /**
     * 任选科室数
     */
    private Integer methodNumber;


}
