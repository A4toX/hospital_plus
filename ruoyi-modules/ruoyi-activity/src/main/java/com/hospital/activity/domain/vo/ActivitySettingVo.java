package com.hospital.activity.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.hospital.activity.domain.ActivitySetting;

import java.io.Serial;
import java.io.Serializable;

/**
 * 教学活动主表(ActivitySetting) 视图对象
 *
 * @author makejava
 * @since 2023-06-21 14:17:47
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ActivitySetting.class)
public class ActivitySettingVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 类型
     */
    @ExcelProperty(value = "类型")
    private String type;

    /**
     * 活动科室id
     */
    @ExcelProperty(value = "活动科室id")
    private Long deptId;

    /**
     * 活动开始时间
     */
    @ExcelProperty(value = "活动开始时间")
    private String startTime;

    /**
     * 活动结束时间
     */
    @ExcelProperty(value = "活动结束时间")
    private String endTime;

    /**
     * 活动封面
     */
    @ExcelProperty(value = "活动封面")
    private String activityImg;

    /**
     * 富文本
     */
    @ExcelProperty(value = "富文本")
    private String activityContent;

    /**
     * 活动状态 1草稿 2已经发布 3已结束 4已下线
     */
    @ExcelProperty(value = "活动状态 1草稿 2已经发布 3已结束 4已下线")
    private String activityStatus;

    /**
     * 活动地点
     */
    @ExcelProperty(value = "活动地点")
    private String activityArea;

    /**
     * 是否开启签到
     */
    @ExcelProperty(value = "是否开启签到")
    private String isSign;

}

