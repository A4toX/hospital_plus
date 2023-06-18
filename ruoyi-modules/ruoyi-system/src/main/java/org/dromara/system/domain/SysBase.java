package org.dromara.system.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 专业对象 sys_base
 *
 * @author yaoyingjie
 * @date 2023-06-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_base")
public class SysBase extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "base_id")
    private Long baseId;

    /**
     * 专业名称
     */
    private String baseName;

    /**
     * 专业代码
     */
    private String baseCode;


}
