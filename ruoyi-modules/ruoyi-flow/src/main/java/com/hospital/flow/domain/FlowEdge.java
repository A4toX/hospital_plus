package com.hospital.flow.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dromara.common.tenant.core.TenantEntity;

/**
 * 流程边线
 *
 * @author liguoxian
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("flow_edge")
public class FlowEdge extends TenantEntity {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 配置ID
     */
    private Long configId;

    /**
     * 节点类型（1默认流转，2条件流转）
     */
    private String type;

    /**
     * 源节点ID
     */
    private Long sourceNodeId;

    /**
     * 目标节点ID
     */
    private Long targetNodeId;

    /**
     * 条件表达式
     */
    private String conditionExpre;

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

