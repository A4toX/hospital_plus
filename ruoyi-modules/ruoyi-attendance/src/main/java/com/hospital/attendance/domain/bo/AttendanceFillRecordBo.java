package com.hospital.attendance.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.hospital.attendance.domain.AttendanceFillRecord;

/**
 * 补卡记录 参数对象
 *
 * @author liguoxian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AttendanceFillRecord.class, reverseConvertGenerate = false)
public class AttendanceFillRecordBo extends BaseEntity {

    /**
     * 主键
     */
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
     * 备注
     */
    private String remark;

}

