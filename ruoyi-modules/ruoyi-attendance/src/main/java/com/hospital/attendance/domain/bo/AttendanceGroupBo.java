package com.hospital.attendance.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.hospital.attendance.domain.AttendanceGroup;

/**
 * 考勤组信息表 视图对象
 *
 * @author liguoxian
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AttendanceGroup.class, reverseConvertGenerate = false)
public class AttendanceGroupBo extends BaseEntity {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 医院id
     */
    private Integer hosId;

    /**
     * 考勤组名称
     */
    private String groupName;

    /**
     * 考勤类型 1固定班次 2自由打卡
     */
    private Integer groupType;

    /**
     * 考勤方式 1位置 2签到考勤
     */
    private Integer groupMethod;

    /**
     * 是否允许外勤打卡 1是 2否
     */
    private Integer areaOutside;

    /**
     * 是否使用动态二维码1是2否
     */
    private Integer groupCode;

    /**
     * 二维码刷新时间
     */
    private Integer codeFreshTime;

    /**
     * 法定节日是否自动排休(1是2否)
     */
    private Integer holidayLeave;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delDelete;

    /**
     * 备注
     */
    private String remark;

}

