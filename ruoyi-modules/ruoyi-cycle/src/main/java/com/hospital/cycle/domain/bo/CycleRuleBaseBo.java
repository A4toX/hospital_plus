package com.hospital.cycle.domain.bo;

import com.hospital.cycle.domain.CycleRuleBase;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import org.dromara.common.mybatis.core.domain.OnlySeachBaseEntity;

/**
 * 规则专业关联业务对象 cycle_rule_base
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CycleRuleBase.class, reverseConvertGenerate = false)
public class CycleRuleBaseBo extends OnlySeachBaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long ruleBaseId;

    /**
     * 规则id
     */
    @NotNull(message = "规则id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long ruleId;

    /**
     * 专业id
     */
    @NotNull(message = "专业id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long baseId;


}
