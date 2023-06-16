package com.hospital.attendance.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.hospital.attendance.domain.AttendanceManagementUser;

/**
 * 考勤组负责人 视图对象
 *
 * @author liguoxian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AttendanceManagementUser.class, reverseConvertGenerate = false)
public class AttendanceManagementUserBo extends BaseEntity {

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

