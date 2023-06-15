package com.hospital.attendance.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.hospital.attendance.domain.AttendanceClasses;

/**
 * 班次设置表 视图对象
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
    private Integer id;

    /**
     * 医院id
     */
    private Integer hosId;

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
     * 是否开启严重迟到(1是2否)
     */
    private Integer isSeriousLate;

    /**
     * 晚多少分钟为严重迟到
     */
    private Integer workSeriousLateMin;

    /**
     * 晚多少分钟为上班缺卡
     */
    private Integer workAbsMin;

    /**
     * 是否开启下班自动打卡(1是2否)
     */
    private Integer isAutoAfter;

    /**
     * 早多少分钟为下班缺卡
     */
    private Integer afterAbsMin;

    /**
     * 早多少分钟为早退
     */
    private Integer afterLeaveEarly;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delDelete;

    /**
     * 备注
     */
    private String remark;

}

