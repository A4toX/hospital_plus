package com.hospital.flow.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.hospital.flow.domain.FlowEdge;

/**
 * 流程边线 参数对象
 *
 * @author liguoxian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FlowEdge.class, reverseConvertGenerate = false)
public class FlowEdgeBo extends BaseEntity {

    /**
     * 主键
     */
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
     * 备注
     */
    private String remark;

}

