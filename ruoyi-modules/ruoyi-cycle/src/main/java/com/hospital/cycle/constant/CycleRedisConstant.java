package com.hospital.cycle.constant;

public interface CycleRedisConstant {
    String CYCLE_RULE_PREFIX = "cycle:rule:";//轮转规则

    String CYCLE_BASE_PREFIX = "cycle:rule:base:";//轮转规则
    String CYCLE_GROUP_PREFIX = "cycle:rule:group:";//轮转规则组
    String CYCLE_GROUP_DEPT_PREFIX = "cycle:rule:group:dept:";//轮转规则组科室
    String CYCLE_STUDENT_PREFIX = "cycle:student:";//轮转学生
    String CYCLE_CALC_STUDENT_PREFIX = "cycle:calc:student:";//轮转学生-计算中
    String CYCLE_CALC_DEPT_PREFIX = "cycle:calc:dept:";//轮转科室-计算中
}
