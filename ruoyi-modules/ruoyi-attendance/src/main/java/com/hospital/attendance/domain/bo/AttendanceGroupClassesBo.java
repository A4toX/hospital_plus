package com.hospital.attendance.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.hospital.attendance.domain.AttendanceGroupClasses;

/**
 * 排班班次表 参数对象
 *
 * @author liguoxian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AttendanceGroupClasses.class, reverseConvertGenerate = false)
public class AttendanceGroupClassesBo extends BaseEntity {

    /**
     * 主键
     */
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
    private String status;

    /**
     * 备注
     */
    private String remark;

}

