package com.hospital.cycle.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 轮转规则组关联科室对象 cycle_group_dept
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cycle_group_dept")
public class CycleGroupDept extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "group_dept_id")
    private Long groupDeptId;

    /**
     * 规则id
     */
    private Long ruleId;

    /**
     * 组id
     */
    private Long groupId;

    /**
     * 科室id
     */
    private Long deptId;

    /**
     * 科室轮转时长
     */
    private Integer deptUnitNum;


}
