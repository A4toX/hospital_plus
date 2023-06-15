package com.hospital.attendance.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Collectors;

/**
 * 类型枚举类
 *
 * @author liguoxian
 */
@Getter
@AllArgsConstructor
public enum AttendanceStatusEnum {

    normal("正常", 1),
    late("迟到", 2),
    serious_late("严重迟到", 2),
    early("早退", 3),
    away_work("缺卡", 4),
    leave("请假", 5),
    outside("外勤", 9);

    String name;
    int status;

    public static Integer getStatusByName(String name) {
        AttendanceStatusEnum[] statusEnums = AttendanceStatusEnum.values();
        for (AttendanceStatusEnum statusEnum : statusEnums) {
            if (statusEnum.getName().equals(name)) {
                return statusEnum.getStatus();
            }
        }
        return 0;
    }

    public static String getNameByStatus(int status) {
        AttendanceStatusEnum[] statusEnums = AttendanceStatusEnum.values();
        for (AttendanceStatusEnum statusEnum : statusEnums) {
            if (statusEnum.getStatus() == status) {
                return statusEnum.getName();
            }
        }
        return "";
    }

    public static String getNameByStatuses(String statuses) {
        return StrUtil.split(statuses, ',').stream()
                .distinct()
                .map(Integer::parseInt)
                .map(AttendanceStatusEnum::getNameByStatus)
                .collect(Collectors.joining(","));
    }
}
