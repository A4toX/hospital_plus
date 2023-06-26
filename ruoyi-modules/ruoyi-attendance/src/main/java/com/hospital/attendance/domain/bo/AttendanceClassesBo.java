package com.hospital.attendance.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.hospital.attendance.domain.AttendanceClasses;

/**
 * 班次设置表 参数对象
 *
 * @author liguoxian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AttendanceClasses.class, reverseConvertGenerate = false)
public class AttendanceClassesBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 班次名称
     */
    private String name;

    /**
     * 上班开始打卡时间
     */
    private String workTime;

    /**
     * 下班开始打卡时间
     */
    private String afterTime;

    /**
     * 晚多少分钟为迟到
     */
    private Integer workLateMin;

    /**
     * 是否开启严重迟到
     */
    private String isSeriousLate;

    /**
     * 晚多少分钟为严重迟到
     */
    private Integer workSeriousLateMin;

    /**
     * 晚多少分钟为上班缺卡
     */
    private Integer workAbsMin;

    /**
     * 是否开启下班自动打卡
     */
    private String isAutoAfter;

    /**
     * 早多少分钟为下班缺卡
     */
    private Integer afterAbsMin;

    /**
     * 早多少分钟为早退
     */
    private Integer afterLeaveEarly;

    /**
     * 备注
     */
    private String remark;

}

