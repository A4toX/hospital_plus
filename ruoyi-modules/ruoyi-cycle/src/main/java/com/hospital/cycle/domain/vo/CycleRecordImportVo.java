package com.hospital.cycle.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class CycleRecordImportVo implements Serializable {

    /**
     * 科室名称
     */
    @ExcelProperty(index = 0)
    private String deptName;

    /**
     * 时间和学生
     */
    private List<String> userNameList;
}
