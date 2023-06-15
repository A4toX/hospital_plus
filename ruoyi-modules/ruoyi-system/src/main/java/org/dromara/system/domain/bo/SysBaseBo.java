package org.dromara.system.domain.bo;

import org.dromara.system.domain.SysBase;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 专业基地业务对象 sys_base
 *
 * @author yaoyingjie
 * @date 2023-06-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysBase.class, reverseConvertGenerate = false)
public class SysBaseBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 专业名称
     */
    @NotBlank(message = "专业名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String baseName;

    /**
     * 专业代码
     */
    @NotBlank(message = "专业代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String baseCode;

    /**
     * 专业负责人联系电话
     */
    @NotBlank(message = "专业负责人联系电话不能为空", groups = { AddGroup.class, EditGroup.class })
    private String baseLeaderPhone;

    /**
     * 专业负责人名称
     */
    @NotBlank(message = "专业负责人名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String baseLeaderName;

    /**
     * 备注
     */
    private String remark;


}
