package com.hospital.cycle.domain.bo;

import com.hospital.cycle.domain.CycleRule;
import com.hospital.cycle.validate.CycleStageGroup;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 轮转规则业务对象 cycle_rule
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CycleRule.class, reverseConvertGenerate = false)
public class CycleRuleBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long ruleId;

    /**
     * 是否开启专业
     */
    @NotBlank(message = "是否开启专业不能为空", groups = { AddGroup.class, CycleStageGroup.class})
    private String baseFlag;

    /**
     * 上一阶段id
     */
    @NotNull(message = "父id不能为空", groups = {CycleStageGroup.class})
    private Long parentId;

    /**
     * 规则名称
     */
    @NotBlank(message = "规则名称不能为空", groups = { AddGroup.class, EditGroup.class, CycleStageGroup.class})
    private String ruleName;

    /**
     * 单位1月2周
     */
    @NotBlank(message = "单位1月2周不能为空", groups = { AddGroup.class})
    private String ruleUnit;

    /**
     * 是否跳过法定节假日
     */
    @NotBlank(message = "是否跳过法定节假日不能为空", groups = { AddGroup.class, CycleStageGroup.class})
    private String holidays;

    /**
     * 当前阶段数
     */
    private Long stageIndex;

    /**
     * 年份
     */
    @NotBlank(message = "年份不能为空", groups = { AddGroup.class})
    private String ruleYear;

    /**
     * 轮转开始时间
     */
    @NotBlank(message = "轮转开始时间不能为空", groups = { EditGroup.class })
    private String startDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否通过校验
     */
    private String ruleValid;

    /**
     * 规则状态1未排2完成
     */
    private String ruleStatus;


}
