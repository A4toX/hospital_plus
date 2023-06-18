package com.hospital.attendance.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 类型枚举类
 *
 * @author liguoxian
 */
@Getter
@AllArgsConstructor
public enum AttendanceKindEnum {

    start_work("上班", "1"),
    end_work("下班", "2");

    String name;

    String type;

    public static String getReverseType(String type) {
        if(type.equals(start_work.type)) {
            return end_work.type;
        }
        return start_work.type;
    }
}
