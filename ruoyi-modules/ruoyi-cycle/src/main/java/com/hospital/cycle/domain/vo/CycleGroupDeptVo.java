package com.hospital.cycle.domain.vo;

import com.hospital.cycle.domain.CycleGroupDept;
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
 * 轮转规则组关联科室视图对象 cycle_group_dept
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CycleGroupDept.class)
public class CycleGroupDeptVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
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
     * 科室名称
     */
    @ExcelProperty(value = "科室名称")
    private String deptName;


    /**
     * 科室轮转时长
     */
    @ExcelProperty(value = "科室轮转时长")
    private Integer deptUnitNum;


}
