package org.dromara.system.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 学员对象 sys_user_student
 *
 * @author Lion Li
 * @date 2023-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_student")
public class SysUserStudent extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(type = IdType.INPUT)
    private Long userId;

    /**
     * 人员类型
     */
    private String personType;

    /**
     * 学员类型
     */
    private String studentType;

    /**
     * 招录年份
     */
    private String residentYear;

    /**
     * 培训专业id
     */
    private Long baseId;


}
