package com.hospital.attendance.domain.vo;

import lombok.Data;

/**
 * 考勤组下的周次信息VO
 */
@Data
public class AttendanceGroupClassVO extends AttendanceClassesVo {


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

    /**
     * 考勤班次ID
     */
    private Long classesId;

    /**
     * 是否需要打卡
     */
    private String status;
}
