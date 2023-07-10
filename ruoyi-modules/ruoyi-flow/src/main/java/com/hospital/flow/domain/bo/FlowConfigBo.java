package com.hospital.flow.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.hospital.flow.domain.FlowConfig;

/**
 * 流程配置 参数对象
 *
 * @author liguoxian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FlowConfig.class, reverseConvertGenerate = false)
public class FlowConfigBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 流程名称
     */
    private String flowName;

    /**
     * 关键字
     */
    private String flowKey;

    /**
     * 流程配置内容
     */
    private String bpmnConfig;

    /**
     * 版本
     */
    private Integer flowVersion;

    /**
     * 是否最新版本
     */
    private String versionFlag;

    /**
     * 最新发布时间
     */
    private String publishTime;

    /**
     * 备注
     */
    private String remark;

}

