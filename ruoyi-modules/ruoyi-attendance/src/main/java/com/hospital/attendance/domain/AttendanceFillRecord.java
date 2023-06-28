package com.hospital.attendance.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dromara.common.tenant.core.TenantEntity;

/**
 * 补卡记录
 *
 * @author liguoxian
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("attendance_fill_record")
public class AttendanceFillRecord extends TenantEntity {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 对应的考勤记录id
     */
    private Long flowId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 补卡日期
     */
    private String fillDate;

    /**
     * 补卡时间
     */
    private String fillTime;

    /**
     * 原打卡时间
     */
    private String originalTime;

    /**
     * 补卡原因
     */
    private String fillReason;

    /**
     * 补卡图片
     */
    private String fillImg;

    /**
     * 审核结果
     */
    private String result;

    /**
     * 流程实例编号
     */
    private String processInstanceId;

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

