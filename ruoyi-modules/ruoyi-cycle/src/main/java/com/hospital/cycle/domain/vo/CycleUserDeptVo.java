package com.hospital.cycle.domain.vo;

import com.hospital.cycle.domain.CycleUserDept;
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
 * 轮转学生选课记录视图对象 cycle_user_dept
 *
 * @author yaoyingjie
 * @date 2023-06-27
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CycleUserDept.class)
public class CycleUserDeptVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 科室组id
     */
    @ExcelProperty(value = "科室组id")
    private Long groupDeptId;

    /**
     * 规则id
     */
    @ExcelProperty(value = "规则id")
    private Long ruleId;

    /**
     * 组id
     */
    @ExcelProperty(value = "组id")
    private Long groupId;

    /**
     * 科室id
     */
    @ExcelProperty(value = "科室id")
    private Long deptId;

    /**
     * 选修时长
     */
    @ExcelProperty(value = "选修时长")
    private Integer selectTime;


}
