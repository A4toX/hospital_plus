package com.hospital.attendance.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.hospital.attendance.domain.AttendanceGroupArea;

/**
 * 考勤地点表 视图对象
 *
 * @author liguoxian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AttendanceGroupArea.class, reverseConvertGenerate = false)
public class AttendanceGroupAreaBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 考勤组id
     */
    private Long groupId;

    /**
     * 地点名称
     */
    private String areaName;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 考勤范围
     */
    private Double attendRange;

    /**
     * 备注
     */
    private String remark;

}

