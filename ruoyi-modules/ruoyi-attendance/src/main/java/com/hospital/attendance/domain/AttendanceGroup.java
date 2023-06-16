package com.hospital.attendance.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dromara.common.tenant.core.TenantEntity;

/**
 * 考勤组信息表
 *
 * @author liguoxian
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("attendance_group")
public class AttendanceGroup extends TenantEntity {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 医院id
     */
    private Long hosId;

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
     * 是否允许外勤打卡
     */
    private String areaOutside;

    /**
     * 是否使用动态二维码
     */
    private Integer groupCode;

    /**
     * 二维码刷新时间
     */
    private Integer codeFreshTime;

    /**
     * 法定节日是否自动排休
     */
    private String holidayLeave;

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

