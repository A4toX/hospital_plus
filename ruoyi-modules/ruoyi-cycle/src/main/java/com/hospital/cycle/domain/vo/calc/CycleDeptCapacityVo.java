package com.hospital.cycle.domain.vo.calc;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CycleDeptCapacityVo {

    /**
     * 科室id
     */
    private Long deptId;
    /**
     * 序号
     */
    private Integer dateIndex;

    /**
     * 科室总容量
     */
    private Integer deptCapacity;

    /**
     * 科室剩余容量
     */
    private Integer unDeptCapacity;

    /**
     * 学生list
     */
    private List<Long> userIds;




    public CycleDeptCapacityVo() {
        // 设置默认值
        this.userIds = new ArrayList<>();
    }

}
