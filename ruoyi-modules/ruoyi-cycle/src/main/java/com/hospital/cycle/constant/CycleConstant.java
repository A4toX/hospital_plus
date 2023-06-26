package com.hospital.cycle.constant;

public interface CycleConstant {
    /**
     * 轮转单位 月
     */
    String CYCLE_UNIT_MONTH = "1";
    /**
     * 轮转单位 周
     */
    String CYCLE_UNIT_WEEK = "2";


    /**
     * 轮状状态-未排
     */
    String CYCLE_STATUS_STAFF = "1";
    /**
     * 轮状状态-已排
     */
    String CYCLE_STATUS_COMPLETE= "2";

    /**
     * 是否已签到(是)
     */
    String YES = "Y";
    /**
     * 是否已签到（否）
     */
    String NO = "N";

    /**
     * 轮转规则组类型-必修
     */
    String CYCLE_GROUP_MUST = "1";
    /**
     * 轮转规则组类型-选修
     */
    String CYCLE_GROUP_ELECTIVE = "2";

    /**
     * 轮转规则组方法-必选
     */
    String CYCLE_GROUP_METHOD_MUST = "1";
    /**
     * 轮转规则组方法-任选其几
     */
    String CYCLE_GROUP_METHOD_ELECTIVE = "2";
    /**
     * 轮转规则组方法-满足时长，不限数
     */
    String CYCLE_GROUP_METHOD_TIME = "3";
}
