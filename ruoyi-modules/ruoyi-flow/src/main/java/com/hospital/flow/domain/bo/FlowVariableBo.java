package com.hospital.flow.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.hospital.flow.domain.FlowVariable;

/**
 * 流程当前参数 参数对象
 *
 * @author liguoxian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FlowVariable.class, reverseConvertGenerate = false)
public class FlowVariableBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 申请ID
     */
    private Long applyId;

    /**
     * 参数类型
     */
    private String paramType;

    /**
     * 参数名称
     */
    private String paramName;

    /**
     * 参数值
     */
    private String paramValue;

    /**
     * 备注
     */
    private String remark;

}

