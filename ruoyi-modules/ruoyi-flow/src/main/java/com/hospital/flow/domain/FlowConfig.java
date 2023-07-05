package com.hospital.flow.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dromara.common.tenant.core.TenantEntity;

/**
 * 流程配置
 *
 * @author liguoxian
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("flow_config")
public class FlowConfig extends TenantEntity {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 流程名称
     */
    private String name;

    /**
     * 关键字
     */
    private String key;

    /**
     * 流程配置内容
     */
    private String bpmnConfig;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 是否最新版本
     */
    private String versionFlag;

    /**
     * 最新发布时间
     */
    private String publishTime;

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

