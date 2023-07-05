package com.hospital.flow.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.hospital.flow.domain.FlowNode;

/**
 * 流程节点 参数对象
 *
 * @author liguoxian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FlowNode.class, reverseConvertGenerate = false)
public class FlowNodeBo extends BaseEntity {

    /**
     * 主键
     */
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
     * 备注
     */
    private String remark;

}

