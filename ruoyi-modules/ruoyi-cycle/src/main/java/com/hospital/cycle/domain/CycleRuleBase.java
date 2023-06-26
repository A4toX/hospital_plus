package com.hospital.cycle.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 规则专业关联对象 cycle_rule_base
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@Data
@TableName("cycle_rule_base")
public class CycleRuleBase {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "rule_base_id")
    private Long ruleBaseId;

    /**
     * 规则id
     */
    private Long ruleId;

    /**
     * 专业id
     */
    private Long baseId;


}
