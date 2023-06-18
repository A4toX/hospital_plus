package com.hospital.attendance.domain.vo;

import lombok.Data;

/**
 * 考勤组周次关联信息返回
 */
@Data
public class AttendanceGroupClassesSimpleRespVO {
    /**
     * 班次id
     */
    private Long classesId;

    /**
     * 班次名称
     */
    private String classesName;

    /**
     * 上班开始打卡时间
     */
    private String workTime;

    /**
     * 下班开始打卡时间
     */
    private String afterTime;

    /**
     * 考勤组班次关联id
     */
    private Long groupClassesId;

    /**
     * 考勤组id
     */
    private Long groupId;

    /**
     * 周次(1-7)
     */
    private Integer weekly;
}
