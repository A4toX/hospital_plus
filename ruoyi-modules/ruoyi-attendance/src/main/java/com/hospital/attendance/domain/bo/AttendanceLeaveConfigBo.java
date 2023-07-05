package com.hospital.attendance.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.hospital.attendance.domain.AttendanceLeaveConfig;

/**
 * 请假配置 参数对象
 *
 * @author liguoxian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AttendanceLeaveConfig.class, reverseConvertGenerate = false)
public class AttendanceLeaveConfigBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 请假名称
     */
    private String leaveName;

    /**
     * 时长单位 1天 2半天
     */
    private Integer leaveUnit;

    /**
     * 假期计算方式 1工作日 2自然日
     */
    private Integer leaveTimeType;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

}

