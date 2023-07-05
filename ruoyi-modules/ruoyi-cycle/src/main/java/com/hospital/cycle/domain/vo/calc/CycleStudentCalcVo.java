package com.hospital.cycle.domain.vo.calc;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CycleStudentCalcVo {
    /**
     * 学生id
     */
    private Long userId;

    /**
     * 科室列表
     */
    private List<CycleStudentDeptCalcVo> deptList;

    /**
     * 排序后的list集合
     */
    private List<Long> studentSortList;

    public CycleStudentCalcVo() {
        // 设置默认值
        this.studentSortList = new ArrayList<>();
    }

}
