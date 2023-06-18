package com.hospital.attendance.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dromara.common.tenant.core.TenantEntity;

/**
 * 考勤地点表
 *
 * @author liguoxian
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("attendance_group_area")
public class AttendanceGroupArea extends TenantEntity {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 医院id
     */
    private Long hosId;

    /**
     * 考勤组id
     */
    private Long groupId;

    /**
     * 地点名称
     */
    private String areaName;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 考勤范围
     */
    private Double attendRange;

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

