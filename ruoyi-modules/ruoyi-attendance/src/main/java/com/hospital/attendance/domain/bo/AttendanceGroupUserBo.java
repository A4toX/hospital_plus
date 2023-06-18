package com.hospital.attendance.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.hospital.attendance.domain.AttendanceGroupUser;

/**
 * 考勤参与人员 视图对象
 *
 * @author liguoxian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AttendanceGroupUser.class, reverseConvertGenerate = false)
public class AttendanceGroupUserBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 考勤组id
     */
    private Long groupId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 备注
     */
    private String remark;

}

