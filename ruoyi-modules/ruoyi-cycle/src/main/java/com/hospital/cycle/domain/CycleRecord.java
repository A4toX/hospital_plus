package com.hospital.cycle.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户轮转记录对象 cycle_record
 *
 * @author yaoyingjie
 * @date 2023-06-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cycle_record")
public class CycleRecord extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户
     */
    private Long userId;

    /**
     * 科室id
     */
    private Long deptId;

    /**
     * 轮转状态
     */
    private String cycleStatus;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 规则id
     */
    private Long ruleId;

    /**
     * 专业id
     */
    private Long baseId;

    /**
     * 科室排序
     */
    private Integer cycleRecordIndex;

    /**
     * 规则组id
     */
    private Long groupId;

    /**
     * 科室来源类型
     */
    private String deptType;

    /**
     * 科室轮转单位
     */
    private Integer deptNum;

}
