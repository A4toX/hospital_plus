package com.hospital.attendance.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dromara.common.tenant.core.TenantEntity;

/**
 * 排班班次表
 *
 * @author liguoxian
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("attendance_group_classes")
public class AttendanceGroupClasses extends TenantEntity {

    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 考勤组id
     */
    private Long groupId;

    /**
     * 班次id
     */
    private Long classesId;

    /**
     * 周次(1-7)
     */
    private Integer weekly;

    /**
     * 是否需要打卡
     */
    private Integer status;

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

