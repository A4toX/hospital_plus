package com.hospital.attendance.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 考勤方式枚举类
 *
 * @author liguoxian
 */
@Getter
@AllArgsConstructor
public enum AttendanceMethodEnum {

    position("定位打卡", 1),
    scan("扫码打卡", 2);

    String name;

    int type;
}
