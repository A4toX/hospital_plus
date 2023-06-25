package com.hospital.cycle.domain.vo;

import com.hospital.cycle.domain.CycleStudent;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.system.domain.vo.SysUserStudentVo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 学员规则关联视图对象 cycle_student
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CycleStudent.class)
public class CycleStudentVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long cycleStudentId;


    /**
     * 学员信息
     */
    private SysUserStudentVo sysUserStudentVo;

    /**
     * 学员id
     */
    @ExcelProperty(value = "学员id")
    private Long userId;


    /**
     * 轮转规则id
     */
    @ExcelProperty(value = "轮转规则id")
    private Long ruleId;

    /**
     * 轮转状态
     */
    @ExcelProperty(value = "轮转状态")
    private String studentCycleStatus;


}
