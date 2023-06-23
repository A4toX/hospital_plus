package com.hospital.activity.domain.vo;

import com.hospital.activity.domain.ActivityTeacher;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 教学活动教师视图对象 activity_teacher
 *
 * @author Lion Li
 * @date 2023-06-21
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ActivityTeacher.class)
public class ActivityTeacherVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 活动id
     */
    @ExcelProperty(value = "活动id")
    private Long activityId;

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 类型 1主讲老师 2助理老师
     */
    @ExcelProperty(value = "类型 1主讲老师 2助理老师")
    private String teacherType;


}
