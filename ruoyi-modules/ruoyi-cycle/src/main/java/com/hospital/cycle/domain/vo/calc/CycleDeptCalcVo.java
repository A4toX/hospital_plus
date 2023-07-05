package com.hospital.cycle.domain.vo.calc;

import com.hospital.cycle.domain.CycleCalcRecord;
import com.hospital.cycle.domain.vo.CycleCalcRecordVo;
import lombok.Data;

import java.util.List;

/**
 * 科室排班vo
 */
@Data
public class CycleDeptCalcVo {
    /**
         * 科室id
         */
    private Long deptId;
    /**
     * 科室类型
     */
    private String deptMethod;

    /**
     * 科室轮转时长
     */
    private Integer deptUnit;

    /**
     * 科室容量list
     */
    private List<CycleCalcRecord> capacityList;



}
