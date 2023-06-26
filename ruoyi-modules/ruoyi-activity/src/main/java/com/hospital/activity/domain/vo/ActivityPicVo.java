package com.hospital.activity.domain.vo;

import com.hospital.activity.domain.ActivityPic;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.system.domain.vo.SysUserSimpleVo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 教学活动图片视图对象 activity_pic
 *
 * @author Lion Li
 * @date 2023-06-21
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ActivityPic.class)
public class ActivityPicVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 上传人姓名
     */
    @ExcelProperty(value = "上传人姓名")
    private String uploadUserName;

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
     * 图片文件
     */
    @ExcelProperty(value = "图片文件")
    private String pic;

    /**
     * 上传人
     */
    @ExcelProperty(value = "上传人")
    private Long uploadUser;

    /**
     * 上传时间
     */
    @ExcelProperty(value = "上传时间")
    private String uploadTime;


}
