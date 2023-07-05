package com.hospital.attendance.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dromara.common.tenant.core.TenantEntity;

/**
 * 请假记录
 *
 * @author liguoxian
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("attendance_leave_record")
public class AttendanceLeaveRecord extends TenantEntity {

    /**
     * 主键
     */
    @TableId
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
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 备注
     */
    private String remark;

}

