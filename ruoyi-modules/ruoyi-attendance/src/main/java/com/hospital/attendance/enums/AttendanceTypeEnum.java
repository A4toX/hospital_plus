package com.hospital.attendance.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 考勤类型枚举类
 *
 * @author liguoxian
 */
@Getter
@AllArgsConstructor
public enum AttendanceTypeEnum {

    fixed_time("固定班次", 1),
    free_time("自由打卡", 2);

    String name;

    int type;
}
