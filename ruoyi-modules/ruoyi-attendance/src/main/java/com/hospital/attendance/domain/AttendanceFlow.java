package com.hospital.attendance.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dromara.common.tenant.core.TenantEntity;

/**
 * 考勤记录表
 *
 * @author liguoxian
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("attendance_flow")
public class AttendanceFlow extends TenantEntity {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 考勤组id
     */
    private Long attendGroupId;

    /**
     * 考勤组班次id
     */
    private Long attendClassesId;

    /**
     * 签到方式
     */
    private Integer attendMethod;

    /**
     * 签到状态
     */
    private Integer attendStatus;

    /**
     * 签到日期
     */
    private String attendDate;

    /**
     * 签到时间
     */
    private String attendTime;

    /**
     * 签到经度
     */
    private Double attendLongitude;

    /**
     * 签到纬度
     */
    private Double attendLatitude;

    /**
     * 考勤地址名称
     */
    private String attendAreaName;

    /**
     * 考勤次数
     */
    private Integer attendNumber;

    /**
     * 签到种类 1上班 2下班
     */
    private String attendKind;

    /**
     * 请假流水id
     */
    private Integer leaveId;

    /**
     * 是否外勤打卡
     */
    private String areaOutside;

    /**
     * 是否系统自动处理
     */
    private String automaticFlag;

    /**
     * 是否需要打卡
     */
    private String needAttendFlag;

    /**
     * 迟到早退分钟数
     */
    private Integer errMinutes;

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

