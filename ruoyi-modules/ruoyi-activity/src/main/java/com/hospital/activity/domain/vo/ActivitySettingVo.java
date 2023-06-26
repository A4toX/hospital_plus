package com.hospital.activity.domain.vo;

import com.hospital.activity.domain.ActivitySetting;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.ReverseAutoMapping;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.system.domain.vo.SysUserSimpleVo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 教学活动视图对象 activity_setting
 *
 * @author Lion Li
 * @date 2023-06-21
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ActivitySetting.class)
public class ActivitySettingVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 教师
     */
    private List<SysUserSimpleVo> teacherList;
    /**
     * 参与人员
     */
    private List<SysUserSimpleVo> joinList;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 活动名称
     */
    @ExcelProperty(value = "活动名称")
    private String activityName;

    /**
     * 类型
     */
    @ExcelProperty(value = "类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "teaching_activity_type")
    private String activityType;


    /**
     * 部门名称
     */
    @ExcelProperty(value = "部门名称")
    private String deptName;

    /**
     * 级别1院级2科室
     */
    @ExcelProperty(value = "活动级别", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "1=院级活动,2=科室活动")
    private String ActivityLevel;

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
    @ExcelProperty(value = "活动内容")
    private String activityContent;

    /**
     * 活动状态 1草稿 2已经发布 3已结束 4已下线
     */
    @ExcelProperty(value = "活动状态 1草稿 2已经发布 3已结束 4已下线", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "1=草稿,2=已经发布,3=已结束,4=已下线")
    private String activityStatus;

    /**
     * 活动地点
     */
    @ExcelProperty(value = "活动地点")
    private String activityArea;

    /**
     * 是否开启签到
     */
    @ExcelProperty(value = "是否开启签到", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_yes_no")
    private String sign;



}
