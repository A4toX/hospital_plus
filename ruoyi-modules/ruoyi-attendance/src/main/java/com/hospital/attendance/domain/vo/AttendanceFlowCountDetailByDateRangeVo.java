package com.hospital.attendance.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 出勤人员列表
 * @author lgx
 */
@Data
@Accessors(chain = true)
public class AttendanceFlowCountDetailByDateRangeVo {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 总工时
     */
    private double workHours;

    /**
     * 平均工时
     */
    private double averageWorkHours;

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
     * 需要打卡天数
     */
    private int needAttendDays;

    /**
     * 未打卡天数
     */
    private int noAttendDays;

    /**
     * 外勤数
     */
    private int outsideNum;
}
