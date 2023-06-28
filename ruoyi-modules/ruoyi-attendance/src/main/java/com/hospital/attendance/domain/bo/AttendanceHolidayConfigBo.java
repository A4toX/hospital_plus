package com.hospital.attendance.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.hospital.attendance.domain.AttendanceHolidayConfig;

/**
 * 节假日配置 参数对象
 *
 * @author liguoxian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AttendanceHolidayConfig.class, reverseConvertGenerate = false)
public class AttendanceHolidayConfigBo extends BaseEntity {

    /**
     * 主键
     */
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
     * 备注
     */
    private String remark;

}

