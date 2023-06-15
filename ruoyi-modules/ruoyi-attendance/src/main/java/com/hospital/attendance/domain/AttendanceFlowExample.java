package com.hospital.attendance.domain;

import java.util.ArrayList;
import java.util.List;

public class AttendanceFlowExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public AttendanceFlowExample() {
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andAttendGroupIdIsNull() {
            addCriterion("attend_group_id is null");
            return (Criteria) this;
        }

        public Criteria andAttendGroupIdIsNotNull() {
            addCriterion("attend_group_id is not null");
            return (Criteria) this;
        }

        public Criteria andAttendGroupIdEqualTo(Integer value) {
            addCriterion("attend_group_id =", value, "attendGroupId");
            return (Criteria) this;
        }

        public Criteria andAttendGroupIdNotEqualTo(Integer value) {
            addCriterion("attend_group_id <>", value, "attendGroupId");
            return (Criteria) this;
        }

        public Criteria andAttendGroupIdGreaterThan(Integer value) {
            addCriterion("attend_group_id >", value, "attendGroupId");
            return (Criteria) this;
        }

        public Criteria andAttendGroupIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("attend_group_id >=", value, "attendGroupId");
            return (Criteria) this;
        }

        public Criteria andAttendGroupIdLessThan(Integer value) {
            addCriterion("attend_group_id <", value, "attendGroupId");
            return (Criteria) this;
        }

        public Criteria andAttendGroupIdLessThanOrEqualTo(Integer value) {
            addCriterion("attend_group_id <=", value, "attendGroupId");
            return (Criteria) this;
        }

        public Criteria andAttendGroupIdIn(List<Integer> values) {
            addCriterion("attend_group_id in", values, "attendGroupId");
            return (Criteria) this;
        }

        public Criteria andAttendGroupIdNotIn(List<Integer> values) {
            addCriterion("attend_group_id not in", values, "attendGroupId");
            return (Criteria) this;
        }

        public Criteria andAttendGroupIdBetween(Integer value1, Integer value2) {
            addCriterion("attend_group_id between", value1, value2, "attendGroupId");
            return (Criteria) this;
        }

        public Criteria andAttendGroupIdNotBetween(Integer value1, Integer value2) {
            addCriterion("attend_group_id not between", value1, value2, "attendGroupId");
            return (Criteria) this;
        }

        public Criteria andAttendClassesIdIsNull() {
            addCriterion("attend_classes_id is null");
            return (Criteria) this;
        }

        public Criteria andAttendClassesIdIsNotNull() {
            addCriterion("attend_classes_id is not null");
            return (Criteria) this;
        }

        public Criteria andAttendClassesIdEqualTo(Integer value) {
            addCriterion("attend_classes_id =", value, "attendClassesId");
            return (Criteria) this;
        }

        public Criteria andAttendClassesIdNotEqualTo(Integer value) {
            addCriterion("attend_classes_id <>", value, "attendClassesId");
            return (Criteria) this;
        }

        public Criteria andAttendClassesIdGreaterThan(Integer value) {
            addCriterion("attend_classes_id >", value, "attendClassesId");
            return (Criteria) this;
        }

        public Criteria andAttendClassesIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("attend_classes_id >=", value, "attendClassesId");
            return (Criteria) this;
        }

        public Criteria andAttendClassesIdLessThan(Integer value) {
            addCriterion("attend_classes_id <", value, "attendClassesId");
            return (Criteria) this;
        }

        public Criteria andAttendClassesIdLessThanOrEqualTo(Integer value) {
            addCriterion("attend_classes_id <=", value, "attendClassesId");
            return (Criteria) this;
        }

        public Criteria andAttendClassesIdIn(List<Integer> values) {
            addCriterion("attend_classes_id in", values, "attendClassesId");
            return (Criteria) this;
        }

        public Criteria andAttendClassesIdNotIn(List<Integer> values) {
            addCriterion("attend_classes_id not in", values, "attendClassesId");
            return (Criteria) this;
        }

        public Criteria andAttendClassesIdBetween(Integer value1, Integer value2) {
            addCriterion("attend_classes_id between", value1, value2, "attendClassesId");
            return (Criteria) this;
        }

        public Criteria andAttendClassesIdNotBetween(Integer value1, Integer value2) {
            addCriterion("attend_classes_id not between", value1, value2, "attendClassesId");
            return (Criteria) this;
        }

        public Criteria andAttendTypeIsNull() {
            addCriterion("attend_type is null");
            return (Criteria) this;
        }

        public Criteria andAttendTypeIsNotNull() {
            addCriterion("attend_type is not null");
            return (Criteria) this;
        }

        public Criteria andAttendTypeEqualTo(Integer value) {
            addCriterion("attend_type =", value, "attendType");
            return (Criteria) this;
        }

        public Criteria andAttendTypeNotEqualTo(Integer value) {
            addCriterion("attend_type <>", value, "attendType");
            return (Criteria) this;
        }

        public Criteria andAttendTypeGreaterThan(Integer value) {
            addCriterion("attend_type >", value, "attendType");
            return (Criteria) this;
        }

        public Criteria andAttendTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("attend_type >=", value, "attendType");
            return (Criteria) this;
        }

        public Criteria andAttendTypeLessThan(Integer value) {
            addCriterion("attend_type <", value, "attendType");
            return (Criteria) this;
        }

        public Criteria andAttendTypeLessThanOrEqualTo(Integer value) {
            addCriterion("attend_type <=", value, "attendType");
            return (Criteria) this;
        }

        public Criteria andAttendTypeIn(List<Integer> values) {
            addCriterion("attend_type in", values, "attendType");
            return (Criteria) this;
        }

        public Criteria andAttendTypeNotIn(List<Integer> values) {
            addCriterion("attend_type not in", values, "attendType");
            return (Criteria) this;
        }

        public Criteria andAttendTypeBetween(Integer value1, Integer value2) {
            addCriterion("attend_type between", value1, value2, "attendType");
            return (Criteria) this;
        }

        public Criteria andAttendTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("attend_type not between", value1, value2, "attendType");
            return (Criteria) this;
        }

        public Criteria andAttendStatusIsNull() {
            addCriterion("attend_status is null");
            return (Criteria) this;
        }

        public Criteria andAttendStatusIsNotNull() {
            addCriterion("attend_status is not null");
            return (Criteria) this;
        }

        public Criteria andAttendStatusEqualTo(Integer value) {
            addCriterion("attend_status =", value, "attendStatus");
            return (Criteria) this;
        }

        public Criteria andAttendStatusNotEqualTo(Integer value) {
            addCriterion("attend_status <>", value, "attendStatus");
            return (Criteria) this;
        }

        public Criteria andAttendStatusGreaterThan(Integer value) {
            addCriterion("attend_status >", value, "attendStatus");
            return (Criteria) this;
        }

        public Criteria andAttendStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("attend_status >=", value, "attendStatus");
            return (Criteria) this;
        }

        public Criteria andAttendStatusLessThan(Integer value) {
            addCriterion("attend_status <", value, "attendStatus");
            return (Criteria) this;
        }

        public Criteria andAttendStatusLessThanOrEqualTo(Integer value) {
            addCriterion("attend_status <=", value, "attendStatus");
            return (Criteria) this;
        }

        public Criteria andAttendStatusIn(List<Integer> values) {
            addCriterion("attend_status in", values, "attendStatus");
            return (Criteria) this;
        }

        public Criteria andAttendStatusNotIn(List<Integer> values) {
            addCriterion("attend_status not in", values, "attendStatus");
            return (Criteria) this;
        }

        public Criteria andAttendStatusBetween(Integer value1, Integer value2) {
            addCriterion("attend_status between", value1, value2, "attendStatus");
            return (Criteria) this;
        }

        public Criteria andAttendStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("attend_status not between", value1, value2, "attendStatus");
            return (Criteria) this;
        }

        public Criteria andAttendDateIsNull() {
            addCriterion("attend_date is null");
            return (Criteria) this;
        }

        public Criteria andAttendDateIsNotNull() {
            addCriterion("attend_date is not null");
            return (Criteria) this;
        }

        public Criteria andAttendDateEqualTo(String value) {
            addCriterion("attend_date =", value, "attendDate");
            return (Criteria) this;
        }

        public Criteria andAttendDateNotEqualTo(String value) {
            addCriterion("attend_date <>", value, "attendDate");
            return (Criteria) this;
        }

        public Criteria andAttendDateGreaterThan(String value) {
            addCriterion("attend_date >", value, "attendDate");
            return (Criteria) this;
        }

        public Criteria andAttendDateGreaterThanOrEqualTo(String value) {
            addCriterion("attend_date >=", value, "attendDate");
            return (Criteria) this;
        }

        public Criteria andAttendDateLessThan(String value) {
            addCriterion("attend_date <", value, "attendDate");
            return (Criteria) this;
        }

        public Criteria andAttendDateLessThanOrEqualTo(String value) {
            addCriterion("attend_date <=", value, "attendDate");
            return (Criteria) this;
        }

        public Criteria andAttendDateLike(String value) {
            addCriterion("attend_date like", value, "attendDate");
            return (Criteria) this;
        }

        public Criteria andAttendDateNotLike(String value) {
            addCriterion("attend_date not like", value, "attendDate");
            return (Criteria) this;
        }

        public Criteria andAttendDateIn(List<String> values) {
            addCriterion("attend_date in", values, "attendDate");
            return (Criteria) this;
        }

        public Criteria andAttendDateNotIn(List<String> values) {
            addCriterion("attend_date not in", values, "attendDate");
            return (Criteria) this;
        }

        public Criteria andAttendDateBetween(String value1, String value2) {
            addCriterion("attend_date between", value1, value2, "attendDate");
            return (Criteria) this;
        }

        public Criteria andAttendDateNotBetween(String value1, String value2) {
            addCriterion("attend_date not between", value1, value2, "attendDate");
            return (Criteria) this;
        }

        public Criteria andAttendTimeIsNull() {
            addCriterion("attend_time is null");
            return (Criteria) this;
        }

        public Criteria andAttendTimeIsNotNull() {
            addCriterion("attend_time is not null");
            return (Criteria) this;
        }

        public Criteria andAttendTimeEqualTo(String value) {
            addCriterion("attend_time =", value, "attendTime");
            return (Criteria) this;
        }

        public Criteria andAttendTimeNotEqualTo(String value) {
            addCriterion("attend_time <>", value, "attendTime");
            return (Criteria) this;
        }

        public Criteria andAttendTimeGreaterThan(String value) {
            addCriterion("attend_time >", value, "attendTime");
            return (Criteria) this;
        }

        public Criteria andAttendTimeGreaterThanOrEqualTo(String value) {
            addCriterion("attend_time >=", value, "attendTime");
            return (Criteria) this;
        }

        public Criteria andAttendTimeLessThan(String value) {
            addCriterion("attend_time <", value, "attendTime");
            return (Criteria) this;
        }

        public Criteria andAttendTimeLessThanOrEqualTo(String value) {
            addCriterion("attend_time <=", value, "attendTime");
            return (Criteria) this;
        }

        public Criteria andAttendTimeLike(String value) {
            addCriterion("attend_time like", value, "attendTime");
            return (Criteria) this;
        }

        public Criteria andAttendTimeNotLike(String value) {
            addCriterion("attend_time not like", value, "attendTime");
            return (Criteria) this;
        }

        public Criteria andAttendTimeIn(List<String> values) {
            addCriterion("attend_time in", values, "attendTime");
            return (Criteria) this;
        }

        public Criteria andAttendTimeNotIn(List<String> values) {
            addCriterion("attend_time not in", values, "attendTime");
            return (Criteria) this;
        }

        public Criteria andAttendTimeBetween(String value1, String value2) {
            addCriterion("attend_time between", value1, value2, "attendTime");
            return (Criteria) this;
        }

        public Criteria andAttendTimeNotBetween(String value1, String value2) {
            addCriterion("attend_time not between", value1, value2, "attendTime");
            return (Criteria) this;
        }

        public Criteria andAttendLongitudeIsNull() {
            addCriterion("attend_longitude is null");
            return (Criteria) this;
        }

        public Criteria andAttendLongitudeIsNotNull() {
            addCriterion("attend_longitude is not null");
            return (Criteria) this;
        }

        public Criteria andAttendLongitudeEqualTo(Double value) {
            addCriterion("attend_longitude =", value, "attendLongitude");
            return (Criteria) this;
        }

        public Criteria andAttendLongitudeNotEqualTo(Double value) {
            addCriterion("attend_longitude <>", value, "attendLongitude");
            return (Criteria) this;
        }

        public Criteria andAttendLongitudeGreaterThan(Double value) {
            addCriterion("attend_longitude >", value, "attendLongitude");
            return (Criteria) this;
        }

        public Criteria andAttendLongitudeGreaterThanOrEqualTo(Double value) {
            addCriterion("attend_longitude >=", value, "attendLongitude");
            return (Criteria) this;
        }

        public Criteria andAttendLongitudeLessThan(Double value) {
            addCriterion("attend_longitude <", value, "attendLongitude");
            return (Criteria) this;
        }

        public Criteria andAttendLongitudeLessThanOrEqualTo(Double value) {
            addCriterion("attend_longitude <=", value, "attendLongitude");
            return (Criteria) this;
        }

        public Criteria andAttendLongitudeIn(List<Double> values) {
            addCriterion("attend_longitude in", values, "attendLongitude");
            return (Criteria) this;
        }

        public Criteria andAttendLongitudeNotIn(List<Double> values) {
            addCriterion("attend_longitude not in", values, "attendLongitude");
            return (Criteria) this;
        }

        public Criteria andAttendLongitudeBetween(Double value1, Double value2) {
            addCriterion("attend_longitude between", value1, value2, "attendLongitude");
            return (Criteria) this;
        }

        public Criteria andAttendLongitudeNotBetween(Double value1, Double value2) {
            addCriterion("attend_longitude not between", value1, value2, "attendLongitude");
            return (Criteria) this;
        }

        public Criteria andAttendLatitudeIsNull() {
            addCriterion("attend_latitude is null");
            return (Criteria) this;
        }

        public Criteria andAttendLatitudeIsNotNull() {
            addCriterion("attend_latitude is not null");
            return (Criteria) this;
        }

        public Criteria andAttendLatitudeEqualTo(Double value) {
            addCriterion("attend_latitude =", value, "attendLatitude");
            return (Criteria) this;
        }

        public Criteria andAttendLatitudeNotEqualTo(Double value) {
            addCriterion("attend_latitude <>", value, "attendLatitude");
            return (Criteria) this;
        }

        public Criteria andAttendLatitudeGreaterThan(Double value) {
            addCriterion("attend_latitude >", value, "attendLatitude");
            return (Criteria) this;
        }

        public Criteria andAttendLatitudeGreaterThanOrEqualTo(Double value) {
            addCriterion("attend_latitude >=", value, "attendLatitude");
            return (Criteria) this;
        }

        public Criteria andAttendLatitudeLessThan(Double value) {
            addCriterion("attend_latitude <", value, "attendLatitude");
            return (Criteria) this;
        }

        public Criteria andAttendLatitudeLessThanOrEqualTo(Double value) {
            addCriterion("attend_latitude <=", value, "attendLatitude");
            return (Criteria) this;
        }

        public Criteria andAttendLatitudeIn(List<Double> values) {
            addCriterion("attend_latitude in", values, "attendLatitude");
            return (Criteria) this;
        }

        public Criteria andAttendLatitudeNotIn(List<Double> values) {
            addCriterion("attend_latitude not in", values, "attendLatitude");
            return (Criteria) this;
        }

        public Criteria andAttendLatitudeBetween(Double value1, Double value2) {
            addCriterion("attend_latitude between", value1, value2, "attendLatitude");
            return (Criteria) this;
        }

        public Criteria andAttendLatitudeNotBetween(Double value1, Double value2) {
            addCriterion("attend_latitude not between", value1, value2, "attendLatitude");
            return (Criteria) this;
        }

        public Criteria andAttendAreaNameIsNull() {
            addCriterion("attend_area_name is null");
            return (Criteria) this;
        }

        public Criteria andAttendAreaNameIsNotNull() {
            addCriterion("attend_area_name is not null");
            return (Criteria) this;
        }

        public Criteria andAttendAreaNameEqualTo(String value) {
            addCriterion("attend_area_name =", value, "attendAreaName");
            return (Criteria) this;
        }

        public Criteria andAttendAreaNameNotEqualTo(String value) {
            addCriterion("attend_area_name <>", value, "attendAreaName");
            return (Criteria) this;
        }

        public Criteria andAttendAreaNameGreaterThan(String value) {
            addCriterion("attend_area_name >", value, "attendAreaName");
            return (Criteria) this;
        }

        public Criteria andAttendAreaNameGreaterThanOrEqualTo(String value) {
            addCriterion("attend_area_name >=", value, "attendAreaName");
            return (Criteria) this;
        }

        public Criteria andAttendAreaNameLessThan(String value) {
            addCriterion("attend_area_name <", value, "attendAreaName");
            return (Criteria) this;
        }

        public Criteria andAttendAreaNameLessThanOrEqualTo(String value) {
            addCriterion("attend_area_name <=", value, "attendAreaName");
            return (Criteria) this;
        }

        public Criteria andAttendAreaNameLike(String value) {
            addCriterion("attend_area_name like", value, "attendAreaName");
            return (Criteria) this;
        }

        public Criteria andAttendAreaNameNotLike(String value) {
            addCriterion("attend_area_name not like", value, "attendAreaName");
            return (Criteria) this;
        }

        public Criteria andAttendAreaNameIn(List<String> values) {
            addCriterion("attend_area_name in", values, "attendAreaName");
            return (Criteria) this;
        }

        public Criteria andAttendAreaNameNotIn(List<String> values) {
            addCriterion("attend_area_name not in", values, "attendAreaName");
            return (Criteria) this;
        }

        public Criteria andAttendAreaNameBetween(String value1, String value2) {
            addCriterion("attend_area_name between", value1, value2, "attendAreaName");
            return (Criteria) this;
        }

        public Criteria andAttendAreaNameNotBetween(String value1, String value2) {
            addCriterion("attend_area_name not between", value1, value2, "attendAreaName");
            return (Criteria) this;
        }

        public Criteria andAttendNumberIsNull() {
            addCriterion("attend_number is null");
            return (Criteria) this;
        }

        public Criteria andAttendNumberIsNotNull() {
            addCriterion("attend_number is not null");
            return (Criteria) this;
        }

        public Criteria andAttendNumberEqualTo(Integer value) {
            addCriterion("attend_number =", value, "attendNumber");
            return (Criteria) this;
        }

        public Criteria andAttendNumberNotEqualTo(Integer value) {
            addCriterion("attend_number <>", value, "attendNumber");
            return (Criteria) this;
        }

        public Criteria andAttendNumberGreaterThan(Integer value) {
            addCriterion("attend_number >", value, "attendNumber");
            return (Criteria) this;
        }

        public Criteria andAttendNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("attend_number >=", value, "attendNumber");
            return (Criteria) this;
        }

        public Criteria andAttendNumberLessThan(Integer value) {
            addCriterion("attend_number <", value, "attendNumber");
            return (Criteria) this;
        }

        public Criteria andAttendNumberLessThanOrEqualTo(Integer value) {
            addCriterion("attend_number <=", value, "attendNumber");
            return (Criteria) this;
        }

        public Criteria andAttendNumberIn(List<Integer> values) {
            addCriterion("attend_number in", values, "attendNumber");
            return (Criteria) this;
        }

        public Criteria andAttendNumberNotIn(List<Integer> values) {
            addCriterion("attend_number not in", values, "attendNumber");
            return (Criteria) this;
        }

        public Criteria andAttendNumberBetween(Integer value1, Integer value2) {
            addCriterion("attend_number between", value1, value2, "attendNumber");
            return (Criteria) this;
        }

        public Criteria andAttendNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("attend_number not between", value1, value2, "attendNumber");
            return (Criteria) this;
        }

        public Criteria andAttendKindIsNull() {
            addCriterion("attend_kind is null");
            return (Criteria) this;
        }

        public Criteria andAttendKindIsNotNull() {
            addCriterion("attend_kind is not null");
            return (Criteria) this;
        }

        public Criteria andAttendKindEqualTo(String value) {
            addCriterion("attend_kind =", value, "attendKind");
            return (Criteria) this;
        }

        public Criteria andAttendKindNotEqualTo(String value) {
            addCriterion("attend_kind <>", value, "attendKind");
            return (Criteria) this;
        }

        public Criteria andAttendKindGreaterThan(String value) {
            addCriterion("attend_kind >", value, "attendKind");
            return (Criteria) this;
        }

        public Criteria andAttendKindGreaterThanOrEqualTo(String value) {
            addCriterion("attend_kind >=", value, "attendKind");
            return (Criteria) this;
        }

        public Criteria andAttendKindLessThan(String value) {
            addCriterion("attend_kind <", value, "attendKind");
            return (Criteria) this;
        }

        public Criteria andAttendKindLessThanOrEqualTo(String value) {
            addCriterion("attend_kind <=", value, "attendKind");
            return (Criteria) this;
        }

        public Criteria andAttendKindLike(String value) {
            addCriterion("attend_kind like", value, "attendKind");
            return (Criteria) this;
        }

        public Criteria andAttendKindNotLike(String value) {
            addCriterion("attend_kind not like", value, "attendKind");
            return (Criteria) this;
        }

        public Criteria andAttendKindIn(List<String> values) {
            addCriterion("attend_kind in", values, "attendKind");
            return (Criteria) this;
        }

        public Criteria andAttendKindNotIn(List<String> values) {
            addCriterion("attend_kind not in", values, "attendKind");
            return (Criteria) this;
        }

        public Criteria andAttendKindBetween(String value1, String value2) {
            addCriterion("attend_kind between", value1, value2, "attendKind");
            return (Criteria) this;
        }

        public Criteria andAttendKindNotBetween(String value1, String value2) {
            addCriterion("attend_kind not between", value1, value2, "attendKind");
            return (Criteria) this;
        }

        public Criteria andLeaveIdIsNull() {
            addCriterion("leave_id is null");
            return (Criteria) this;
        }

        public Criteria andLeaveIdIsNotNull() {
            addCriterion("leave_id is not null");
            return (Criteria) this;
        }

        public Criteria andLeaveIdEqualTo(Integer value) {
            addCriterion("leave_id =", value, "leaveId");
            return (Criteria) this;
        }

        public Criteria andLeaveIdNotEqualTo(Integer value) {
            addCriterion("leave_id <>", value, "leaveId");
            return (Criteria) this;
        }

        public Criteria andLeaveIdGreaterThan(Integer value) {
            addCriterion("leave_id >", value, "leaveId");
            return (Criteria) this;
        }

        public Criteria andLeaveIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("leave_id >=", value, "leaveId");
            return (Criteria) this;
        }

        public Criteria andLeaveIdLessThan(Integer value) {
            addCriterion("leave_id <", value, "leaveId");
            return (Criteria) this;
        }

        public Criteria andLeaveIdLessThanOrEqualTo(Integer value) {
            addCriterion("leave_id <=", value, "leaveId");
            return (Criteria) this;
        }

        public Criteria andLeaveIdIn(List<Integer> values) {
            addCriterion("leave_id in", values, "leaveId");
            return (Criteria) this;
        }

        public Criteria andLeaveIdNotIn(List<Integer> values) {
            addCriterion("leave_id not in", values, "leaveId");
            return (Criteria) this;
        }

        public Criteria andLeaveIdBetween(Integer value1, Integer value2) {
            addCriterion("leave_id between", value1, value2, "leaveId");
            return (Criteria) this;
        }

        public Criteria andLeaveIdNotBetween(Integer value1, Integer value2) {
            addCriterion("leave_id not between", value1, value2, "leaveId");
            return (Criteria) this;
        }

        public Criteria andAreaOutsideIsNull() {
            addCriterion("area_outside is null");
            return (Criteria) this;
        }

        public Criteria andAreaOutsideIsNotNull() {
            addCriterion("area_outside is not null");
            return (Criteria) this;
        }

        public Criteria andAreaOutsideEqualTo(Integer value) {
            addCriterion("area_outside =", value, "areaOutside");
            return (Criteria) this;
        }

        public Criteria andAreaOutsideNotEqualTo(Integer value) {
            addCriterion("area_outside <>", value, "areaOutside");
            return (Criteria) this;
        }

        public Criteria andAreaOutsideGreaterThan(Integer value) {
            addCriterion("area_outside >", value, "areaOutside");
            return (Criteria) this;
        }

        public Criteria andAreaOutsideGreaterThanOrEqualTo(Integer value) {
            addCriterion("area_outside >=", value, "areaOutside");
            return (Criteria) this;
        }

        public Criteria andAreaOutsideLessThan(Integer value) {
            addCriterion("area_outside <", value, "areaOutside");
            return (Criteria) this;
        }

        public Criteria andAreaOutsideLessThanOrEqualTo(Integer value) {
            addCriterion("area_outside <=", value, "areaOutside");
            return (Criteria) this;
        }

        public Criteria andAreaOutsideIn(List<Integer> values) {
            addCriterion("area_outside in", values, "areaOutside");
            return (Criteria) this;
        }

        public Criteria andAreaOutsideNotIn(List<Integer> values) {
            addCriterion("area_outside not in", values, "areaOutside");
            return (Criteria) this;
        }

        public Criteria andAreaOutsideBetween(Integer value1, Integer value2) {
            addCriterion("area_outside between", value1, value2, "areaOutside");
            return (Criteria) this;
        }

        public Criteria andAreaOutsideNotBetween(Integer value1, Integer value2) {
            addCriterion("area_outside not between", value1, value2, "areaOutside");
            return (Criteria) this;
        }

        public Criteria andAutomaticFlagIsNull() {
            addCriterion("automatic_flag is null");
            return (Criteria) this;
        }

        public Criteria andAutomaticFlagIsNotNull() {
            addCriterion("automatic_flag is not null");
            return (Criteria) this;
        }

        public Criteria andAutomaticFlagEqualTo(String value) {
            addCriterion("automatic_flag =", value, "automaticFlag");
            return (Criteria) this;
        }

        public Criteria andAutomaticFlagNotEqualTo(String value) {
            addCriterion("automatic_flag <>", value, "automaticFlag");
            return (Criteria) this;
        }

        public Criteria andAutomaticFlagGreaterThan(String value) {
            addCriterion("automatic_flag >", value, "automaticFlag");
            return (Criteria) this;
        }

        public Criteria andAutomaticFlagGreaterThanOrEqualTo(String value) {
            addCriterion("automatic_flag >=", value, "automaticFlag");
            return (Criteria) this;
        }

        public Criteria andAutomaticFlagLessThan(String value) {
            addCriterion("automatic_flag <", value, "automaticFlag");
            return (Criteria) this;
        }

        public Criteria andAutomaticFlagLessThanOrEqualTo(String value) {
            addCriterion("automatic_flag <=", value, "automaticFlag");
            return (Criteria) this;
        }

        public Criteria andAutomaticFlagLike(String value) {
            addCriterion("automatic_flag like", value, "automaticFlag");
            return (Criteria) this;
        }

        public Criteria andAutomaticFlagNotLike(String value) {
            addCriterion("automatic_flag not like", value, "automaticFlag");
            return (Criteria) this;
        }

        public Criteria andAutomaticFlagIn(List<String> values) {
            addCriterion("automatic_flag in", values, "automaticFlag");
            return (Criteria) this;
        }

        public Criteria andAutomaticFlagNotIn(List<String> values) {
            addCriterion("automatic_flag not in", values, "automaticFlag");
            return (Criteria) this;
        }

        public Criteria andAutomaticFlagBetween(String value1, String value2) {
            addCriterion("automatic_flag between", value1, value2, "automaticFlag");
            return (Criteria) this;
        }

        public Criteria andAutomaticFlagNotBetween(String value1, String value2) {
            addCriterion("automatic_flag not between", value1, value2, "automaticFlag");
            return (Criteria) this;
        }

        public Criteria andNeedAttendFlagIsNull() {
            addCriterion("need_attend_flag is null");
            return (Criteria) this;
        }

        public Criteria andNeedAttendFlagIsNotNull() {
            addCriterion("need_attend_flag is not null");
            return (Criteria) this;
        }

        public Criteria andNeedAttendFlagEqualTo(String value) {
            addCriterion("need_attend_flag =", value, "needAttendFlag");
            return (Criteria) this;
        }

        public Criteria andNeedAttendFlagNotEqualTo(String value) {
            addCriterion("need_attend_flag <>", value, "needAttendFlag");
            return (Criteria) this;
        }

        public Criteria andNeedAttendFlagGreaterThan(String value) {
            addCriterion("need_attend_flag >", value, "needAttendFlag");
            return (Criteria) this;
        }

        public Criteria andNeedAttendFlagGreaterThanOrEqualTo(String value) {
            addCriterion("need_attend_flag >=", value, "needAttendFlag");
            return (Criteria) this;
        }

        public Criteria andNeedAttendFlagLessThan(String value) {
            addCriterion("need_attend_flag <", value, "needAttendFlag");
            return (Criteria) this;
        }

        public Criteria andNeedAttendFlagLessThanOrEqualTo(String value) {
            addCriterion("need_attend_flag <=", value, "needAttendFlag");
            return (Criteria) this;
        }

        public Criteria andNeedAttendFlagLike(String value) {
            addCriterion("need_attend_flag like", value, "needAttendFlag");
            return (Criteria) this;
        }

        public Criteria andNeedAttendFlagNotLike(String value) {
            addCriterion("need_attend_flag not like", value, "needAttendFlag");
            return (Criteria) this;
        }

        public Criteria andNeedAttendFlagIn(List<String> values) {
            addCriterion("need_attend_flag in", values, "needAttendFlag");
            return (Criteria) this;
        }

        public Criteria andNeedAttendFlagNotIn(List<String> values) {
            addCriterion("need_attend_flag not in", values, "needAttendFlag");
            return (Criteria) this;
        }

        public Criteria andNeedAttendFlagBetween(String value1, String value2) {
            addCriterion("need_attend_flag between", value1, value2, "needAttendFlag");
            return (Criteria) this;
        }

        public Criteria andNeedAttendFlagNotBetween(String value1, String value2) {
            addCriterion("need_attend_flag not between", value1, value2, "needAttendFlag");
            return (Criteria) this;
        }

        public Criteria andErrMinutesIsNull() {
            addCriterion("err_minutes is null");
            return (Criteria) this;
        }

        public Criteria andErrMinutesIsNotNull() {
            addCriterion("err_minutes is not null");
            return (Criteria) this;
        }

        public Criteria andErrMinutesEqualTo(Integer value) {
            addCriterion("err_minutes =", value, "errMinutes");
            return (Criteria) this;
        }

        public Criteria andErrMinutesNotEqualTo(Integer value) {
            addCriterion("err_minutes <>", value, "errMinutes");
            return (Criteria) this;
        }

        public Criteria andErrMinutesGreaterThan(Integer value) {
            addCriterion("err_minutes >", value, "errMinutes");
            return (Criteria) this;
        }

        public Criteria andErrMinutesGreaterThanOrEqualTo(Integer value) {
            addCriterion("err_minutes >=", value, "errMinutes");
            return (Criteria) this;
        }

        public Criteria andErrMinutesLessThan(Integer value) {
            addCriterion("err_minutes <", value, "errMinutes");
            return (Criteria) this;
        }

        public Criteria andErrMinutesLessThanOrEqualTo(Integer value) {
            addCriterion("err_minutes <=", value, "errMinutes");
            return (Criteria) this;
        }

        public Criteria andErrMinutesIn(List<Integer> values) {
            addCriterion("err_minutes in", values, "errMinutes");
            return (Criteria) this;
        }

        public Criteria andErrMinutesNotIn(List<Integer> values) {
            addCriterion("err_minutes not in", values, "errMinutes");
            return (Criteria) this;
        }

        public Criteria andErrMinutesBetween(Integer value1, Integer value2) {
            addCriterion("err_minutes between", value1, value2, "errMinutes");
            return (Criteria) this;
        }

        public Criteria andErrMinutesNotBetween(Integer value1, Integer value2) {
            addCriterion("err_minutes not between", value1, value2, "errMinutes");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNull() {
            addCriterion("remarks is null");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNotNull() {
            addCriterion("remarks is not null");
            return (Criteria) this;
        }

        public Criteria andRemarksEqualTo(String value) {
            addCriterion("remarks =", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotEqualTo(String value) {
            addCriterion("remarks <>", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThan(String value) {
            addCriterion("remarks >", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("remarks >=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThan(String value) {
            addCriterion("remarks <", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThanOrEqualTo(String value) {
            addCriterion("remarks <=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLike(String value) {
            addCriterion("remarks like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotLike(String value) {
            addCriterion("remarks not like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksIn(List<String> values) {
            addCriterion("remarks in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotIn(List<String> values) {
            addCriterion("remarks not in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksBetween(String value1, String value2) {
            addCriterion("remarks between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotBetween(String value1, String value2) {
            addCriterion("remarks not between", value1, value2, "remarks");
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
