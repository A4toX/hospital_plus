package com.hospital.cycle.domain.bo;

import com.hospital.cycle.domain.CycleRecord;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 用户轮转记录业务对象 cycle_record
 *
 * @author yaoyingjie
 * @date 2023-06-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CycleRecord.class, reverseConvertGenerate = false)
public class CycleRecordBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 用户
     */
    @NotNull(message = "用户不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 科室id
     */
    @NotNull(message = "科室id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deptId;

    /**
     * 轮转状态
     */
    @NotBlank(message = "轮转状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cycleStatus;

    /**
     * 开始时间
     */
    @NotBlank(message = "开始时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private String startTime;

    /**
     * 结束时间
     */
    @NotBlank(message = "结束时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private String endTime;

    /**
     * 规则id
     */
    @NotNull(message = "规则id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long ruleId;

    /**
     * 专业id
     */
    @NotNull(message = "专业id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long baseId;

    /**
     * 科室排序
     */
    @NotNull(message = "科室排序不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long cycleRecordIndex;


}
