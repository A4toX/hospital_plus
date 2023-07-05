package com.hospital.cycle.domain.vo.calc;

import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * 学生的初始科室信息
 */
@Data
public class CycleStudentDeptCalcVo {
    /**
     * 科室id
     */
    private Long deptId;
    /**
     * 科室轮转时长
     */
    private Integer studentDeptNum;

    /**
     * 是否已经排完
     *
     */
    private Boolean isComplete;

    // 构造函数
    public CycleStudentDeptCalcVo() {
        // 设置默认值
        this.isComplete = false; // 将 isComplete 的默认值设置为 false
        this.studentDeptNum=0;
    }
}
