package com.hospital.attendance.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dromara.common.tenant.core.TenantEntity;

/**
 * 请假配置
 *
 * @author liguoxian
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("attendance_leave_config")
public class AttendanceLeaveConfig extends TenantEntity {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 请假名称
     */
    private String leaveName;

    /**
     * 时长单位 1天 2半天
     */
    private Integer leaveUnit;

    /**
     * 假期计算方式 1工作日 2自然日
     */
    private Integer leaveTimeType;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

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

