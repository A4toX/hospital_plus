package com.hospital.attendance.domain;

import com.demo.hospital.common.base.entity.BaseEntity;
import com.hospital.attendance.domain.vo.AttendanceClassesVo;
import com.hospital.attendance.domain.vo.AttendanceGroupVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 考勤记录表 model
 *
 * @author liguoxian
 */
@ApiModel("考勤记录表")
@Data
public class AttendanceFlow extends BaseEntity<Integer> implements Serializable {

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
    private Long leaveId;

    /**
     * 是否外勤打卡 1是 2否
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
}

