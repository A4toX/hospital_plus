package com.hospital.flow.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dromara.common.tenant.core.TenantEntity;

/**
 * 流程申请
 *
 * @author liguoxian
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("flow_apply")
public class FlowApply extends TenantEntity {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 流程ID
     */
    private Long configId;

    /**
     * 流程关键字
     */
    private String key;

    /**
     * 审核状态(1进行中，2完成，3终止，4撤销)
     */
    private String status;

    /**
     * 关联业务ID
     */
    private Long businessId;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 备注
     */
    private String remark;

}

