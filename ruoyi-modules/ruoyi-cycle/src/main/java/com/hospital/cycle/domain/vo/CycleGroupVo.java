package com.hospital.cycle.domain.vo;

import com.hospital.cycle.domain.CycleGroup;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 轮转规则组视图对象 cycle_group
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CycleGroup.class)
public class CycleGroupVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 规则下科室列表
     */
    private List<CycleGroupDeptVo> cycleGroupDeptList;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long groupId;

    /**
     * 规则id
     */
    @ExcelProperty(value = "规则id")
    private Long ruleId;

    /**
     * 组名
     */
    @ExcelProperty(value = "组名")
    private String groupName;

    /**
     * 专业id
     */
    @ExcelProperty(value = "专业id")
    private Long baseId;

    /**
     * 1必修2选修
     */
    @ExcelProperty(value = "1必修2选修")
    private String groupType;

    /**
     * 规则方法1必选2任选其几3满足时长，不限数(选轮规则)
     */
    @ExcelProperty(value = "规则方法1必选2任选其几3满足时长，不限数(选轮规则)")
    private String groupMethod;

    /**
     * 组轮转时长(月或周)
     */
    @ExcelProperty(value = "组轮转时长(月或周)")
    private Integer groupUnitNum;

    /**
     * 任选科室数
     */
    @ExcelProperty(value = "任选科室数")
    private Integer methodNumber;


}
