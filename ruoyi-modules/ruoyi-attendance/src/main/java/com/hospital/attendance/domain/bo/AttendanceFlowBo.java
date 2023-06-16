package com.hospital.attendance.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.hospital.attendance.domain.AttendanceFlow;

/**
 * 考勤记录表 视图对象
 *
 * @author liguoxian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AttendanceFlow.class, reverseConvertGenerate = false)
public class AttendanceFlowBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 医院id
     */
    private Long hosId;

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
     * 签到类型
     */
    private Integer attendType;

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
    private Integer areaOutside;

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
    private String delDelete;

    /**
     * 备注
     */
    private String remark;

}

