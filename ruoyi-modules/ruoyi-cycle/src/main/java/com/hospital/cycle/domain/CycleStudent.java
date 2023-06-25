package com.hospital.cycle.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 学员规则关联对象 cycle_student
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@Data
@TableName("cycle_student")
public class CycleStudent{

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "cycle_student_id")
    private Long cycleStudentId;

    /**
     * 学员id
     */
    private Long userId;

    /**
     * 轮转规则id
     */
    private Long ruleId;

    /**
     * 轮转状态
     */
    private String studentCycleStatus;


}
