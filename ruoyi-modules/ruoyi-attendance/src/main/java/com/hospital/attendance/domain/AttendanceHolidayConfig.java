package com.hospital.attendance.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dromara.common.tenant.core.TenantEntity;

/**
 * 节假日配置
 *
 * @author liguoxian
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("attendance_holiday_config")
public class AttendanceHolidayConfig extends TenantEntity {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 所属年份
     */
    private String belongYear;

    /**
     * 节假日的开始时间
     */
    private String beginDate;

    /**
     * 节假日的结束时间
     */
    private String endDate;

    /**
     * 节假日名称
     */
    private String holiday;

    /**
     * 节假日描述
     */
    private String holidayRemark;

    /**
     * 调修日列表
     */
    private String inverseDays;

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

