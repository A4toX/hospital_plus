package com.hospital.cycle.domain.vo;

import com.hospital.cycle.domain.CycleRecord;
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
 * 用户轮转记录视图对象 cycle_record
 *
 * @author yaoyingjie
 * @date 2023-06-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CycleRecord.class)
public class CycleRecordVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 用户
     */
    @ExcelProperty(value = "用户")
    private Long userId;

    /**
     * 科室id
     */
    @ExcelProperty(value = "科室id")
    private Long deptId;

    /**
     * 轮转状态
     */
    @ExcelProperty(value = "轮转状态")
    private String cycleStatus;

    /**
     * 开始时间
     */
    @ExcelProperty(value = "开始时间")
    private String startTime;

    /**
     * 结束时间
     */
    @ExcelProperty(value = "结束时间")
    private String endTime;

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
     * 科室排序
     */
    @ExcelProperty(value = "科室排序")
    private Long cycleRecordIndex;


}
