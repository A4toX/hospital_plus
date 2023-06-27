package com.hospital.cycle.domain.vo;

import com.hospital.cycle.domain.CycleRule;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.hospital.cycle.domain.CycleRuleBase;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.system.domain.SysBase;
import org.dromara.system.domain.vo.SysBaseVo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 轮转规则视图对象 cycle_rule
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CycleRule.class)
public class CycleRuleVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 子阶段
     */
    private List<CycleRuleVo> childList;


    /**
     * 专业列表
     */
    private List<CycleRuleBaseVo> BaseList;

    /**
     * 组信息(不区分专业的规则会直接返回，区分专业的规则会返回空，需要单独调用接口获取
     */
    private List<CycleGroupVo> cycleGroupList;


    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long ruleId;

    /**
     * 是否开启专业
     */
    @ExcelProperty(value = "是否开启专业", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_yes_no")
    private String baseFlag;

    /**
     * 上一阶段id
     */
    @ExcelProperty(value = "上一阶段id")
    private Long parentId;

    /**
     * 规则名称
     */
    @ExcelProperty(value = "规则名称")
    private String ruleName;

    /**
     * 单位1月2周
     */
    @ExcelProperty(value = "单位1月2周")
    private String ruleUnit;

    /**
     * 是否跳过法定节假日
     */
    @ExcelProperty(value = "是否跳过法定节假日", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_yes_no")
    private String holidays;

    /**
     * 当前阶段数
     */
    @ExcelProperty(value = "当前阶段数")
    private Long stageIndex;

    /**
     * 年份
     */
    @ExcelProperty(value = "年份")
    private String ruleYear;

    /**
     * 轮转开始时间
     */
    @ExcelProperty(value = "轮转开始时间")
    private String startDate;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 是否通过校验
     */
    @ExcelProperty(value = "是否通过校验", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_yes_no")
    private String ruleValid;

    /**
     * 规则状态1未排2完成
     */
    @ExcelProperty(value = "规则状态1未排2完成")
    private String ruleStatus;

    /**
     * 是否开启科室选择
     */
    @ExcelProperty(value = "是否开启选课", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_yes_no")
    private String deptSelectFlag;


}
