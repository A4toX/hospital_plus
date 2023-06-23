package com.hospital.activity.domain.vo;

import com.hospital.activity.domain.ActivityUser;
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
 * 教学活动参与用户视图对象 activity_user
 *
 * @author Lion Li
 * @date 2023-06-22
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ActivityUser.class)
public class ActivityUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户信息
     */
    private SysUserSimpleVo userInfo;

    /**
     * 主键id
     */
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
     * 是否已经签到
     */
    @ExcelProperty(value = "是否已签到", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_yes_no")
    private String signFlag;

    /**
     * 签到时间
     */
    @ExcelProperty(value = "签到时间")
    private String signTime;

}
