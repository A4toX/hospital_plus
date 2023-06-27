package com.hospital.cycle.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 轮转学生选课记录对象 cycle_user_dept
 *
 * @author yaoyingjie
 * @date 2023-06-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cycle_user_dept")
public class CycleUserDept extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 科室组id
     */
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
     * 选修时长
     */
    private Integer selectTime;


}
