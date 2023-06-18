package com.hospital.attendance.domain.vo.attendUser;

import lombok.Data;

import java.util.List;

@Data
public class AddAttendanceUserVo {

    /**
     * 考勤组id
     */
    private Long groupId;

    /**
     * 用户id集合
     */
    private List<Long> addUserIds;
}
