package com.hospital.activity.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hospital.activity.domain.ActivityUser;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import org.dromara.common.mybatis.core.domain.OnlySeachBaseEntity;
import org.dromara.system.domain.vo.SysUserSimpleVo;

import java.util.HashMap;
import java.util.Map;

/**
 * 教学活动参与用户业务对象 activity_user
 *
 * @author Lion Li
 * @date 2023-06-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ActivityUser.class, reverseConvertGenerate = false)
public class ActivityUserBo extends OnlySeachBaseEntity{

    /**
     * id
     */
    @NotNull(message = "id不能为空", groups = { EditGroup.class })
    private Long id;
    /**
     * 活动id
     */
    @NotNull(message = "活动id不能为空", groups = { EditGroup.class })
    private Long activityId;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class})
    private Long userId;

    /**
     * 是否已经签到
     */
    private String signFlag;

    /**
     * 签到时间
     */
    private String signTime;

}
