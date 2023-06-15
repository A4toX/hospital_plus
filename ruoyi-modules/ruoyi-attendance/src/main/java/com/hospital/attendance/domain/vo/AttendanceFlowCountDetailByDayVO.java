package com.hospital.attendance.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 出勤人员列表
 * @author lgx
 */
@Data
@Accessors(chain = true)
public class AttendanceFlowCountDetailByDayVO {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 是否出勤
     */
    private String attendFlag;

    /**
     * 考勤状态
     */
    private String attendStatus;

    /**
     * 工时
     */
    private double workHours;

    /**
     * 迟到数
     */
    private int lateNum;

    /**
     * 严重迟到数
     */
    private int seriousLateNum;

    /**
     * 早退数
     */
    private int earlyNum;

    /**
     * 缺卡数
     */
    private int noAttendNum;

    /**
     * 外勤数
     */
    private int outsideNum;
}
