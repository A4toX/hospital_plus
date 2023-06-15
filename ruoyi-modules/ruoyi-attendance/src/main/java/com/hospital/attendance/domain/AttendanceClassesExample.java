package com.hospital.attendance.domain;

import java.util.ArrayList;
import java.util.List;

public class AttendanceClassesExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public AttendanceClassesExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andHosIdIsNull() {
            addCriterion("hos_id is null");
            return (Criteria) this;
        }

        public Criteria andHosIdIsNotNull() {
            addCriterion("hos_id is not null");
            return (Criteria) this;
        }

        public Criteria andHosIdEqualTo(Integer value) {
            addCriterion("hos_id =", value, "hosId");
            return (Criteria) this;
        }

        public Criteria andHosIdNotEqualTo(Integer value) {
            addCriterion("hos_id <>", value, "hosId");
            return (Criteria) this;
        }

        public Criteria andHosIdGreaterThan(Integer value) {
            addCriterion("hos_id >", value, "hosId");
            return (Criteria) this;
        }

        public Criteria andHosIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("hos_id >=", value, "hosId");
            return (Criteria) this;
        }

        public Criteria andHosIdLessThan(Integer value) {
            addCriterion("hos_id <", value, "hosId");
            return (Criteria) this;
        }

        public Criteria andHosIdLessThanOrEqualTo(Integer value) {
            addCriterion("hos_id <=", value, "hosId");
            return (Criteria) this;
        }

        public Criteria andHosIdIn(List<Integer> values) {
            addCriterion("hos_id in", values, "hosId");
            return (Criteria) this;
        }

        public Criteria andHosIdNotIn(List<Integer> values) {
            addCriterion("hos_id not in", values, "hosId");
            return (Criteria) this;
        }

        public Criteria andHosIdBetween(Integer value1, Integer value2) {
            addCriterion("hos_id between", value1, value2, "hosId");
            return (Criteria) this;
        }

        public Criteria andHosIdNotBetween(Integer value1, Integer value2) {
            addCriterion("hos_id not between", value1, value2, "hosId");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andworkTimeIsNull() {
            addCriterion("work_time is null");
            return (Criteria) this;
        }

        public Criteria andworkTimeIsNotNull() {
            addCriterion("work_time is not null");
            return (Criteria) this;
        }

        public Criteria andworkTimeEqualTo(String value) {
            addCriterion("work_time =", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andworkTimeNotEqualTo(String value) {
            addCriterion("work_time <>", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andworkTimeGreaterThan(String value) {
            addCriterion("work_time >", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andworkTimeGreaterThanOrEqualTo(String value) {
            addCriterion("work_time >=", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andworkTimeLessThan(String value) {
            addCriterion("work_time <", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andworkTimeLessThanOrEqualTo(String value) {
            addCriterion("work_time <=", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andworkTimeLike(String value) {
            addCriterion("work_time like", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andworkTimeNotLike(String value) {
            addCriterion("work_time not like", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andworkTimeIn(List<String> values) {
            addCriterion("work_time in", values, "workTime");
            return (Criteria) this;
        }

        public Criteria andworkTimeNotIn(List<String> values) {
            addCriterion("work_time not in", values, "workTime");
            return (Criteria) this;
        }

        public Criteria andworkTimeBetween(String value1, String value2) {
            addCriterion("work_time between", value1, value2, "workTime");
            return (Criteria) this;
        }

        public Criteria andworkTimeNotBetween(String value1, String value2) {
            addCriterion("work_time not between", value1, value2, "workTime");
            return (Criteria) this;
        }

        public Criteria andAfterTimeIsNull() {
            addCriterion("after_time is null");
            return (Criteria) this;
        }

        public Criteria andAfterTimeIsNotNull() {
            addCriterion("after_time is not null");
            return (Criteria) this;
        }

        public Criteria andAfterTimeEqualTo(String value) {
            addCriterion("after_time =", value, "afterTime");
            return (Criteria) this;
        }

        public Criteria andAfterTimeNotEqualTo(String value) {
            addCriterion("after_time <>", value, "afterTime");
            return (Criteria) this;
        }

        public Criteria andAfterTimeGreaterThan(String value) {
            addCriterion("after_time >", value, "afterTime");
            return (Criteria) this;
        }

        public Criteria andAfterTimeGreaterThanOrEqualTo(String value) {
            addCriterion("after_time >=", value, "afterTime");
            return (Criteria) this;
        }

        public Criteria andAfterTimeLessThan(String value) {
            addCriterion("after_time <", value, "afterTime");
            return (Criteria) this;
        }

        public Criteria andAfterTimeLessThanOrEqualTo(String value) {
            addCriterion("after_time <=", value, "afterTime");
            return (Criteria) this;
        }

        public Criteria andAfterTimeLike(String value) {
            addCriterion("after_time like", value, "afterTime");
            return (Criteria) this;
        }

        public Criteria andAfterTimeNotLike(String value) {
            addCriterion("after_time not like", value, "afterTime");
            return (Criteria) this;
        }

        public Criteria andAfterTimeIn(List<String> values) {
            addCriterion("after_time in", values, "afterTime");
            return (Criteria) this;
        }

        public Criteria andAfterTimeNotIn(List<String> values) {
            addCriterion("after_time not in", values, "afterTime");
            return (Criteria) this;
        }

        public Criteria andAfterTimeBetween(String value1, String value2) {
            addCriterion("after_time between", value1, value2, "afterTime");
            return (Criteria) this;
        }

        public Criteria andAfterTimeNotBetween(String value1, String value2) {
            addCriterion("after_time not between", value1, value2, "afterTime");
            return (Criteria) this;
        }

        public Criteria andWorkLateMinIsNull() {
            addCriterion("work_late_min is null");
            return (Criteria) this;
        }

        public Criteria andWorkLateMinIsNotNull() {
            addCriterion("work_late_min is not null");
            return (Criteria) this;
        }

        public Criteria andWorkLateMinEqualTo(Integer value) {
            addCriterion("work_late_min =", value, "workLateMin");
            return (Criteria) this;
        }

        public Criteria andWorkLateMinNotEqualTo(Integer value) {
            addCriterion("work_late_min <>", value, "workLateMin");
            return (Criteria) this;
        }

        public Criteria andWorkLateMinGreaterThan(Integer value) {
            addCriterion("work_late_min >", value, "workLateMin");
            return (Criteria) this;
        }

        public Criteria andWorkLateMinGreaterThanOrEqualTo(Integer value) {
            addCriterion("work_late_min >=", value, "workLateMin");
            return (Criteria) this;
        }

        public Criteria andWorkLateMinLessThan(Integer value) {
            addCriterion("work_late_min <", value, "workLateMin");
            return (Criteria) this;
        }

        public Criteria andWorkLateMinLessThanOrEqualTo(Integer value) {
            addCriterion("work_late_min <=", value, "workLateMin");
            return (Criteria) this;
        }

        public Criteria andWorkLateMinIn(List<Integer> values) {
            addCriterion("work_late_min in", values, "workLateMin");
            return (Criteria) this;
        }

        public Criteria andWorkLateMinNotIn(List<Integer> values) {
            addCriterion("work_late_min not in", values, "workLateMin");
            return (Criteria) this;
        }

        public Criteria andWorkLateMinBetween(Integer value1, Integer value2) {
            addCriterion("work_late_min between", value1, value2, "workLateMin");
            return (Criteria) this;
        }

        public Criteria andWorkLateMinNotBetween(Integer value1, Integer value2) {
            addCriterion("work_late_min not between", value1, value2, "workLateMin");
            return (Criteria) this;
        }

        public Criteria andIsSeriousLateIsNull() {
            addCriterion("is_serious_late is null");
            return (Criteria) this;
        }

        public Criteria andIsSeriousLateIsNotNull() {
            addCriterion("is_serious_late is not null");
            return (Criteria) this;
        }

        public Criteria andIsSeriousLateEqualTo(Integer value) {
            addCriterion("is_serious_late =", value, "isSeriousLate");
            return (Criteria) this;
        }

        public Criteria andIsSeriousLateNotEqualTo(Integer value) {
            addCriterion("is_serious_late <>", value, "isSeriousLate");
            return (Criteria) this;
        }

        public Criteria andIsSeriousLateGreaterThan(Integer value) {
            addCriterion("is_serious_late >", value, "isSeriousLate");
            return (Criteria) this;
        }

        public Criteria andIsSeriousLateGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_serious_late >=", value, "isSeriousLate");
            return (Criteria) this;
        }

        public Criteria andIsSeriousLateLessThan(Integer value) {
            addCriterion("is_serious_late <", value, "isSeriousLate");
            return (Criteria) this;
        }

        public Criteria andIsSeriousLateLessThanOrEqualTo(Integer value) {
            addCriterion("is_serious_late <=", value, "isSeriousLate");
            return (Criteria) this;
        }

        public Criteria andIsSeriousLateIn(List<Integer> values) {
            addCriterion("is_serious_late in", values, "isSeriousLate");
            return (Criteria) this;
        }

        public Criteria andIsSeriousLateNotIn(List<Integer> values) {
            addCriterion("is_serious_late not in", values, "isSeriousLate");
            return (Criteria) this;
        }

        public Criteria andIsSeriousLateBetween(Integer value1, Integer value2) {
            addCriterion("is_serious_late between", value1, value2, "isSeriousLate");
            return (Criteria) this;
        }

        public Criteria andIsSeriousLateNotBetween(Integer value1, Integer value2) {
            addCriterion("is_serious_late not between", value1, value2, "isSeriousLate");
            return (Criteria) this;
        }

        public Criteria andWorkSeriousLateMinIsNull() {
            addCriterion("work_serious_late_min is null");
            return (Criteria) this;
        }

        public Criteria andWorkSeriousLateMinIsNotNull() {
            addCriterion("work_serious_late_min is not null");
            return (Criteria) this;
        }

        public Criteria andWorkSeriousLateMinEqualTo(Integer value) {
            addCriterion("work_serious_late_min =", value, "workSeriousLateMin");
            return (Criteria) this;
        }

        public Criteria andWorkSeriousLateMinNotEqualTo(Integer value) {
            addCriterion("work_serious_late_min <>", value, "workSeriousLateMin");
            return (Criteria) this;
        }

        public Criteria andWorkSeriousLateMinGreaterThan(Integer value) {
            addCriterion("work_serious_late_min >", value, "workSeriousLateMin");
            return (Criteria) this;
        }

        public Criteria andWorkSeriousLateMinGreaterThanOrEqualTo(Integer value) {
            addCriterion("work_serious_late_min >=", value, "workSeriousLateMin");
            return (Criteria) this;
        }

        public Criteria andWorkSeriousLateMinLessThan(Integer value) {
            addCriterion("work_serious_late_min <", value, "workSeriousLateMin");
            return (Criteria) this;
        }

        public Criteria andWorkSeriousLateMinLessThanOrEqualTo(Integer value) {
            addCriterion("work_serious_late_min <=", value, "workSeriousLateMin");
            return (Criteria) this;
        }

        public Criteria andWorkSeriousLateMinIn(List<Integer> values) {
            addCriterion("work_serious_late_min in", values, "workSeriousLateMin");
            return (Criteria) this;
        }

        public Criteria andWorkSeriousLateMinNotIn(List<Integer> values) {
            addCriterion("work_serious_late_min not in", values, "workSeriousLateMin");
            return (Criteria) this;
        }

        public Criteria andWorkSeriousLateMinBetween(Integer value1, Integer value2) {
            addCriterion("work_serious_late_min between", value1, value2, "workSeriousLateMin");
            return (Criteria) this;
        }

        public Criteria andWorkSeriousLateMinNotBetween(Integer value1, Integer value2) {
            addCriterion("work_serious_late_min not between", value1, value2, "workSeriousLateMin");
            return (Criteria) this;
        }

        public Criteria andWorkAbsMinIsNull() {
            addCriterion("work_abs_min is null");
            return (Criteria) this;
        }

        public Criteria andWorkAbsMinIsNotNull() {
            addCriterion("work_abs_min is not null");
            return (Criteria) this;
        }

        public Criteria andWorkAbsMinEqualTo(Integer value) {
            addCriterion("work_abs_min =", value, "workAbsMin");
            return (Criteria) this;
        }

        public Criteria andWorkAbsMinNotEqualTo(Integer value) {
            addCriterion("work_abs_min <>", value, "workAbsMin");
            return (Criteria) this;
        }

        public Criteria andWorkAbsMinGreaterThan(Integer value) {
            addCriterion("work_abs_min >", value, "workAbsMin");
            return (Criteria) this;
        }

        public Criteria andWorkAbsMinGreaterThanOrEqualTo(Integer value) {
            addCriterion("work_abs_min >=", value, "workAbsMin");
            return (Criteria) this;
        }

        public Criteria andWorkAbsMinLessThan(Integer value) {
            addCriterion("work_abs_min <", value, "workAbsMin");
            return (Criteria) this;
        }

        public Criteria andWorkAbsMinLessThanOrEqualTo(Integer value) {
            addCriterion("work_abs_min <=", value, "workAbsMin");
            return (Criteria) this;
        }

        public Criteria andWorkAbsMinIn(List<Integer> values) {
            addCriterion("work_abs_min in", values, "workAbsMin");
            return (Criteria) this;
        }

        public Criteria andWorkAbsMinNotIn(List<Integer> values) {
            addCriterion("work_abs_min not in", values, "workAbsMin");
            return (Criteria) this;
        }

        public Criteria andWorkAbsMinBetween(Integer value1, Integer value2) {
            addCriterion("work_abs_min between", value1, value2, "workAbsMin");
            return (Criteria) this;
        }

        public Criteria andWorkAbsMinNotBetween(Integer value1, Integer value2) {
            addCriterion("work_abs_min not between", value1, value2, "workAbsMin");
            return (Criteria) this;
        }

        public Criteria andIsAutoAfterIsNull() {
            addCriterion("is_auto_after is null");
            return (Criteria) this;
        }

        public Criteria andIsAutoAfterIsNotNull() {
            addCriterion("is_auto_after is not null");
            return (Criteria) this;
        }

        public Criteria andIsAutoAfterEqualTo(Integer value) {
            addCriterion("is_auto_after =", value, "isAutoAfter");
            return (Criteria) this;
        }

        public Criteria andIsAutoAfterNotEqualTo(Integer value) {
            addCriterion("is_auto_after <>", value, "isAutoAfter");
            return (Criteria) this;
        }

        public Criteria andIsAutoAfterGreaterThan(Integer value) {
            addCriterion("is_auto_after >", value, "isAutoAfter");
            return (Criteria) this;
        }

        public Criteria andIsAutoAfterGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_auto_after >=", value, "isAutoAfter");
            return (Criteria) this;
        }

        public Criteria andIsAutoAfterLessThan(Integer value) {
            addCriterion("is_auto_after <", value, "isAutoAfter");
            return (Criteria) this;
        }

        public Criteria andIsAutoAfterLessThanOrEqualTo(Integer value) {
            addCriterion("is_auto_after <=", value, "isAutoAfter");
            return (Criteria) this;
        }

        public Criteria andIsAutoAfterIn(List<Integer> values) {
            addCriterion("is_auto_after in", values, "isAutoAfter");
            return (Criteria) this;
        }

        public Criteria andIsAutoAfterNotIn(List<Integer> values) {
            addCriterion("is_auto_after not in", values, "isAutoAfter");
            return (Criteria) this;
        }

        public Criteria andIsAutoAfterBetween(Integer value1, Integer value2) {
            addCriterion("is_auto_after between", value1, value2, "isAutoAfter");
            return (Criteria) this;
        }

        public Criteria andIsAutoAfterNotBetween(Integer value1, Integer value2) {
            addCriterion("is_auto_after not between", value1, value2, "isAutoAfter");
            return (Criteria) this;
        }

        public Criteria andAfterAbsMinIsNull() {
            addCriterion("after_abs_min is null");
            return (Criteria) this;
        }

        public Criteria andAfterAbsMinIsNotNull() {
            addCriterion("after_abs_min is not null");
            return (Criteria) this;
        }

        public Criteria andAfterAbsMinEqualTo(Integer value) {
            addCriterion("after_abs_min =", value, "afterAbsMin");
            return (Criteria) this;
        }

        public Criteria andAfterAbsMinNotEqualTo(Integer value) {
            addCriterion("after_abs_min <>", value, "afterAbsMin");
            return (Criteria) this;
        }

        public Criteria andAfterAbsMinGreaterThan(Integer value) {
            addCriterion("after_abs_min >", value, "afterAbsMin");
            return (Criteria) this;
        }

        public Criteria andAfterAbsMinGreaterThanOrEqualTo(Integer value) {
            addCriterion("after_abs_min >=", value, "afterAbsMin");
            return (Criteria) this;
        }

        public Criteria andAfterAbsMinLessThan(Integer value) {
            addCriterion("after_abs_min <", value, "afterAbsMin");
            return (Criteria) this;
        }

        public Criteria andAfterAbsMinLessThanOrEqualTo(Integer value) {
            addCriterion("after_abs_min <=", value, "afterAbsMin");
            return (Criteria) this;
        }

        public Criteria andAfterAbsMinIn(List<Integer> values) {
            addCriterion("after_abs_min in", values, "afterAbsMin");
            return (Criteria) this;
        }

        public Criteria andAfterAbsMinNotIn(List<Integer> values) {
            addCriterion("after_abs_min not in", values, "afterAbsMin");
            return (Criteria) this;
        }

        public Criteria andAfterAbsMinBetween(Integer value1, Integer value2) {
            addCriterion("after_abs_min between", value1, value2, "afterAbsMin");
            return (Criteria) this;
        }

        public Criteria andAfterAbsMinNotBetween(Integer value1, Integer value2) {
            addCriterion("after_abs_min not between", value1, value2, "afterAbsMin");
            return (Criteria) this;
        }

        public Criteria andAfterLeaveEarlyIsNull() {
            addCriterion("after_Leave_early is null");
            return (Criteria) this;
        }

        public Criteria andAfterLeaveEarlyIsNotNull() {
            addCriterion("after_Leave_early is not null");
            return (Criteria) this;
        }

        public Criteria andAfterLeaveEarlyEqualTo(Integer value) {
            addCriterion("after_Leave_early =", value, "afterLeaveEarly");
            return (Criteria) this;
        }

        public Criteria andAfterLeaveEarlyNotEqualTo(Integer value) {
            addCriterion("after_Leave_early <>", value, "afterLeaveEarly");
            return (Criteria) this;
        }

        public Criteria andAfterLeaveEarlyGreaterThan(Integer value) {
            addCriterion("after_Leave_early >", value, "afterLeaveEarly");
            return (Criteria) this;
        }

        public Criteria andAfterLeaveEarlyGreaterThanOrEqualTo(Integer value) {
            addCriterion("after_Leave_early >=", value, "afterLeaveEarly");
            return (Criteria) this;
        }

        public Criteria andAfterLeaveEarlyLessThan(Integer value) {
            addCriterion("after_Leave_early <", value, "afterLeaveEarly");
            return (Criteria) this;
        }

        public Criteria andAfterLeaveEarlyLessThanOrEqualTo(Integer value) {
            addCriterion("after_Leave_early <=", value, "afterLeaveEarly");
            return (Criteria) this;
        }

        public Criteria andAfterLeaveEarlyIn(List<Integer> values) {
            addCriterion("after_Leave_early in", values, "afterLeaveEarly");
            return (Criteria) this;
        }

        public Criteria andAfterLeaveEarlyNotIn(List<Integer> values) {
            addCriterion("after_Leave_early not in", values, "afterLeaveEarly");
            return (Criteria) this;
        }

        public Criteria andAfterLeaveEarlyBetween(Integer value1, Integer value2) {
            addCriterion("after_Leave_early between", value1, value2, "afterLeaveEarly");
            return (Criteria) this;
        }

        public Criteria andAfterLeaveEarlyNotBetween(Integer value1, Integer value2) {
            addCriterion("after_Leave_early not between", value1, value2, "afterLeaveEarly");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNull() {
            addCriterion("is_delete is null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNotNull() {
            addCriterion("is_delete is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteEqualTo(String value) {
            addCriterion("is_delete =", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotEqualTo(String value) {
            addCriterion("is_delete <>", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThan(String value) {
            addCriterion("is_delete >", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThanOrEqualTo(String value) {
            addCriterion("is_delete >=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThan(String value) {
            addCriterion("is_delete <", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThanOrEqualTo(String value) {
            addCriterion("is_delete <=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLike(String value) {
            addCriterion("is_delete like", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotLike(String value) {
            addCriterion("is_delete not like", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIn(List<String> values) {
            addCriterion("is_delete in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotIn(List<String> values) {
            addCriterion("is_delete not in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteBetween(String value1, String value2) {
            addCriterion("is_delete between", value1, value2, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotBetween(String value1, String value2) {
            addCriterion("is_delete not between", value1, value2, "isDelete");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(Integer value) {
            addCriterion("create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(Integer value) {
            addCriterion("create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(Integer value) {
            addCriterion("create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(Integer value) {
            addCriterion("create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(Integer value) {
            addCriterion("create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<Integer> values) {
            addCriterion("create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<Integer> values) {
            addCriterion("create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(Integer value1, Integer value2) {
            addCriterion("create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(Integer value1, Integer value2) {
            addCriterion("create_by not between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(String value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(String value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(String value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(String value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(String value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLike(String value) {
            addCriterion("create_time like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotLike(String value) {
            addCriterion("create_time not like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<String> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<String> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(String value1, String value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(String value1, String value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNull() {
            addCriterion("update_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNotNull() {
            addCriterion("update_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateByEqualTo(Integer value) {
            addCriterion("update_by =", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotEqualTo(Integer value) {
            addCriterion("update_by <>", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThan(Integer value) {
            addCriterion("update_by >", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_by >=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThan(Integer value) {
            addCriterion("update_by <", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThanOrEqualTo(Integer value) {
            addCriterion("update_by <=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIn(List<Integer> values) {
            addCriterion("update_by in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotIn(List<Integer> values) {
            addCriterion("update_by not in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByBetween(Integer value1, Integer value2) {
            addCriterion("update_by between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotBetween(Integer value1, Integer value2) {
            addCriterion("update_by not between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(String value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(String value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(String value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(String value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(String value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLike(String value) {
            addCriterion("update_time like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotLike(String value) {
            addCriterion("update_time not like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<String> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<String> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(String value1, String value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(String value1, String value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
