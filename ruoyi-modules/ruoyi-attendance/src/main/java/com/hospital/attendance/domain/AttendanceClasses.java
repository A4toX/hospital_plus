package com.hospital.attendance.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dromara.common.tenant.core.TenantEntity;

/**
 * 班次设置表
 *
 * @author liguoxian
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("attendance_classes")
public class AttendanceClasses extends TenantEntity {

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
     * 班次名称
     */
    private String name;

    /**
     * 上班开始打卡时间
     */
    private String workTime;

    /**
     * 下班开始打卡时间
     */
    private String afterTime;

    /**
     * 晚多少分钟为迟到
     */
    private Integer workLateMin;

    /**
     * 是否开启严重迟到
     */
    private Integer isSeriousLate;

    /**
     * 晚多少分钟为严重迟到
     */
    private Integer workSeriousLateMin;

    /**
     * 晚多少分钟为上班缺卡
     */
    private Integer workAbsMin;

    /**
     * 是否开启下班自动打卡
     */
    private Integer isAutoAfter;

    /**
     * 早多少分钟为下班缺卡
     */
    private Integer afterAbsMin;

    /**
     * 早多少分钟为早退
     */
    private Integer afterLeaveEarly;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delDelete;

    /**
     * 备注
     */
    private String remark;

}

