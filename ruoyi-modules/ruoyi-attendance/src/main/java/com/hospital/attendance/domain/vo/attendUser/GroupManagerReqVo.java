package com.hospital.attendance.domain.vo.attendUser;

import com.hospital.attendance.api.dto.TeacherDto;
import lombok.Data;

/**
 * 管理考勤组下的管理人员查询条件
 */
@Data
public class GroupManagerReqVo extends TeacherDto {


    /**
     * 考勤组id
     */
    private Long groupId;
}
