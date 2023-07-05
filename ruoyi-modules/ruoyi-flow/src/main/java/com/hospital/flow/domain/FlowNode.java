package com.hospital.flow.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dromara.common.tenant.core.TenantEntity;

/**
 * 流程节点
 *
 * @author liguoxian
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("flow_node")
public class FlowNode extends TenantEntity {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 类型
     */
    private String type;

    /**
     * 配置ID
     */
    private Long configId;

    /**
     * 多实例类型（0无，1 会签，2或签）
     */
    private String multiType;

    /**
     * 审核人类型
     */
    private String assigneeType;

    /**
     * 审核人选项
     */
    private String assigneeValue;

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

