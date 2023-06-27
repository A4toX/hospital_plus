package com.hospital.cycle.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 轮转规则对象 cycle_rule
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cycle_rule")
public class CycleRule extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "rule_id")
    private Long ruleId;

    /**
     * 是否开启专业
     */
    private String baseFlag;

    /**
     * 上一阶段id
     */
    private Long parentId;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 单位1月2周
     */
    private String ruleUnit;

    /**
     * 是否跳过法定节假日
     */
    private String holidays;

    /**
     * 当前阶段数
     */
    private Long stageIndex;

    /**
     * 年份
     */
    private String ruleYear;

    /**
     * 轮转开始时间
     */
    private String startDate;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

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

    /**
     * 阶段列表
     */
    private String ancestors;

    /**
     * 是否开启科室选择
     */
    private String deptSelectFlag;


}
