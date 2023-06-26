package com.hospital.cycle.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
public class CycleRecordImportVo implements Serializable {

    /**
     * 科室id
     */
    private Long deptId;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 学生列表
     */
    private List<String> studentList;

}
