package com.hospital.cycle.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 轮转计算过程记录对象 cycle_calc_record
 *
 * @author yaoyingjie
 * @date 2023-07-02
 */
@Data
@TableName("cycle_calc_record")
public class CycleCalcRecord{

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "calc_record_id")
    private Long calcRecordId;

    /**
     * 规则id
     */
    private Long ruleId;

    /**
     * 科室id
     */
    private Long deptId;

    /**
     * 科室序列
     */
    private Integer deptIndex;

    /**
     * 科室总容量
     */
    private Integer deptCapacity;

    /**
     * 科室剩余容量
     */
    private Integer unDeptCapacity;

    /**
     * 学员id列表
     */
    private String userIds;

    /**
     * 规则组id
     */
    private Long groupId;
    /**
     * 规则组方法
     */
    private String groupMethod;

    /**
     * 空闲率
     */
    private Double margin;

}
