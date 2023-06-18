package com.hospital.attendance.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * 考勤组日期范围统计汇总
 * @author lgx
 */
@Data
public class AttendanceFlowCountByDateRangeVo {

    /**
     * 考勤组ID
     */
    private Long groupId;

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
     * 旷工数
     */
    private int noWorkNum;

    /**
     * 外勤数
     */
    private int outsideNum;

    /**
     * 出勤人员列表
     */
    private List<AttendanceFlowCountDetailByDateRangeVo> details;
}
