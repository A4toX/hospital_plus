package com.hospital.cycle.domain.vo.calc;

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
     * 科室容量list
     */
    private List<CycleDeptCapacityVo> capacityList;



}
