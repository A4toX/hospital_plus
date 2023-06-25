package com.hospital.cycle.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 轮转规则组对象 cycle_group
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cycle_group")
public class CycleGroup extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "group_id")
    private Long groupId;

    /**
     * 规则id
     */
    private Long ruleId;

    /**
     * 组名
     */
    private String groupName;

    /**
     * 专业id
     */
    private Long baseId;

    /**
     * 1必修2选修
     */
    private String groupType;

    /**
     * 规则方法1必选2任选其几3满足时长，不限数(选轮规则)
     */
    private String groupMethod;

    /**
     * 组轮转时长(月或周)
     */
    private Integer groupUnitNum;

    /**
     * 任选科室数
     */
    private Integer methodNumber;


}
