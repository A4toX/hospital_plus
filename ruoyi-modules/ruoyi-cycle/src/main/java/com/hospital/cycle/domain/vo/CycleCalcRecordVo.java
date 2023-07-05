package com.hospital.cycle.domain.vo;

import com.hospital.cycle.domain.CycleCalcRecord;
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
 * 轮转计算过程记录视图对象 cycle_calc_record
 *
 * @author yaoyingjie
 * @date 2023-07-02
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CycleCalcRecord.class)
public class CycleCalcRecordVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long calcRecordId;

    /**
     * 规则id
     */
    @ExcelProperty(value = "规则id")
    private Long ruleId;

    /**
     * 科室id
     */
    @ExcelProperty(value = "科室id")
    private Long deptId;

    /**
     * 科室序列
     */
    @ExcelProperty(value = "科室序列")
    private Integer deptIndex;

    /**
     * 科室总容量
     */
    @ExcelProperty(value = "科室总容量")
    private Integer deptCapacity;

    /**
     * 科室剩余容量
     */
    @ExcelProperty(value = "科室剩余容量")
    private Integer unDeptCapacity;

    /**
     * 学员id列表
     */
    @ExcelProperty(value = "学员id列表")
    private String userIds;

    /**
     * 规则组id
     */
    @ExcelProperty(value = "规则组id")
    private Long groupId;
    /**
     * 规则组方法
     */
    @ExcelProperty(value = "规则组方法")
    private String groupMethod;


}
