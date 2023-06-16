package com.hospital.attendance.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * 考勤组日统计汇总
 * @author lgx
 */
@Data
public class AttendanceFlowCountByDayVO {

    /**
     * 考勤组ID
     */
    private Long groupId;

    /**
     * 考勤日期
     */
    private String date;

    /**
     * 出勤人数
     */
    private int attendNum;

    /**
     * 应出勤人数
     */
    private int expectedAttendNum;

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
     * 外勤数
     */
    private int outsideNum;

    /**
     * 出勤人员列表
     */
    private List<AttendanceFlowCountDetailByDayVO> details;
}
