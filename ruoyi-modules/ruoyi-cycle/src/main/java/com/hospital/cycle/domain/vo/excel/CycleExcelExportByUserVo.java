package com.hospital.cycle.domain.vo.excel;

import lombok.Data;

/**
 * 轮转数据学生维度导出类
 */
@Data
public class CycleExcelExportByUserVo {
    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 学生名称列表
     */
    private String studentNames;
}
