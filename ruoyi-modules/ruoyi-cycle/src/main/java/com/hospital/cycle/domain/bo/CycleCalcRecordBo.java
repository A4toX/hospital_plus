package com.hospital.cycle.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.hospital.cycle.domain.CycleCalcRecord;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 轮转计算过程记录业务对象 cycle_calc_record
 *
 * @author yaoyingjie
 * @date 2023-07-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CycleCalcRecord.class, reverseConvertGenerate = false)
public class CycleCalcRecordBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long calcRecordId;

    /**
     * 规则id
     */
    @NotNull(message = "规则id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long ruleId;

    /**
     * 科室id
     */
    @NotNull(message = "科室id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deptId;

    /**
     * 科室序列
     */
    @NotNull(message = "科室序列不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer deptIndex;

    /**
     * 科室总容量
     */
    @NotNull(message = "科室总容量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer deptCapacity;

    /**
     * 科室剩余容量
     */
    @NotNull(message = "科室剩余容量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer unDeptCapacity;

    /**
     * 学员id列表
     */
    @NotBlank(message = "学员id列表不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userIds;

    /**
     * 规则组id
     */
    @NotBlank(message = "规则组id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long groupId;
    /**
     * 规则组方法
     */
    @NotBlank(message = "规则组方法不能为空", groups = { AddGroup.class, EditGroup.class })
    private String groupMethod;

}
