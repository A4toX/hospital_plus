package com.hospital.attendance.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.hospital.attendance.domain.AttendanceLeaveRecord;

/**
 * 请假记录 参数对象
 *
 * @author liguoxian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AttendanceLeaveRecord.class, reverseConvertGenerate = false)
public class AttendanceLeaveRecordBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 请假配置id
     */
    private Long leaveConfigId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 请假开始日期
     */
    private String leaveStartDate;

    /**
     * 请假结束日期
     */
    private String leaveEndDate;

    /**
     * 请假开始时间
     */
    private String leaveStartTime;

    /**
     * 请假结束时间
     */
    private String leaveEndTime;

    /**
     * 请假时长(日)
     */
    private Double leaveLength;

    /**
     * 请假事由
     */
    private String leaveThings;

    /**
     * 附件集合,多个逗号隔开
     */
    private String files;

    /**
     * 流程实例编号
     */
    private String processInstanceId;

    /**
     * 审核结果
     */
    private String result;

    /**
     * 备注
     */
    private String remark;

}

