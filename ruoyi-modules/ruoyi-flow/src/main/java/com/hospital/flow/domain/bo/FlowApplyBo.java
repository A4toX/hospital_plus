package com.hospital.flow.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.hospital.flow.domain.FlowApply;

/**
 * 流程申请 参数对象
 *
 * @author liguoxian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FlowApply.class, reverseConvertGenerate = false)
public class FlowApplyBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 流程ID
     */
    private Long configId;

    /**
     * 流程关键字
     */
    private String flowKey;

    /**
     * 审核状态(1进行中，2完成，3终止，4撤销)
     */
    private String status;

    /**
     * 关联业务ID
     */
    private Long businessId;

    /**
     * 备注
     */
    private String remark;

}

