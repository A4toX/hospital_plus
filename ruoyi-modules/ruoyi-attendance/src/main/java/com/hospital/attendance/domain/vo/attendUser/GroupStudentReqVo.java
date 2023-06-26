package com.hospital.attendance.domain.vo.attendUser;

import com.hospital.attendance.api.dto.StudentDto;
import lombok.Data;

/**
 * 管理考勤组下的人员查询条件
 */
@Data
public class GroupStudentReqVo extends StudentDto {

    /**
     * 考勤组id
     */
    private Long groupId;
}
