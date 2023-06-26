package com.hospital.cycle.domain.vo;

import com.hospital.cycle.domain.CycleRuleBase;
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
 * 规则专业关联视图对象 cycle_rule_base
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CycleRuleBase.class)
public class CycleRuleBaseVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long ruleBaseId;

    /**
     * 规则id
     */
    @ExcelProperty(value = "规则id")
    private Long ruleId;

    /**
     * 专业id
     */
    @ExcelProperty(value = "专业id")
    private Long baseId;

    /**
     * 专业名称
     */
    @ExcelProperty(value = "专业名称")
    private String baseName;

}
