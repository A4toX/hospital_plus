package org.dromara.system.domain.bo;

import org.dromara.system.domain.SysUserStudent;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

import java.util.Set;

/**
 * 学员业务对象 sys_user_student
 *
 * @author Lion Li
 * @date 2023-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysUserStudent.class, reverseConvertGenerate = false)
public class SysUserStudentBo extends BaseEntity {

    /**
     * 用户信息
     */
    private SysUserBo sysUser;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户ids
     */
    private Set<Long> userIds;

    /**
     * 人员类型
     */
    @NotBlank(message = "人员类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String personType;

    /**
     * 学员类型
     */
    @NotBlank(message = "学员类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String studentType;

    /**
     * 招录年份
     */
    @NotBlank(message = "招录年份不能为空", groups = { AddGroup.class, EditGroup.class })
    private String residentYear;

    /**
     * 培训专业id
     */
    private Long baseId;


}
