package com.hospital.attendance.mapper;

import com.demo.hospital.attendance.model.AttendanceFlow;
import com.demo.hospital.attendance.model.AttendanceFlowExample;
import com.demo.hospital.common.base.dao.MyBatisBaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 考勤记录表 DAO
 *
 * @author liguoxian
 */
public interface AttendanceFlowMapper extends MyBatisBaseDao<AttendanceFlow, Integer, AttendanceFlowExample> {

    default List<AttendanceFlow> selectByDate(Integer groupId, Integer userId, String date) {
        AttendanceFlowExample example = new AttendanceFlowExample();
        AttendanceFlowExample.Criteria criteria = example.createCriteria();
        criteria.andAttendGroupIdEqualTo(groupId)
                .andAttendDateEqualTo(date);
        if(userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        example.setOrderByClause(" user_id, attend_number, attend_kind");
        return selectByExample(example);
    }

    default List<AttendanceFlow> selectByDateRange(Integer groupId, Integer userId, String startDate, String endDate) {
        AttendanceFlowExample example = new AttendanceFlowExample();
        AttendanceFlowExample.Criteria criteria = example.createCriteria();
        criteria.andAttendGroupIdEqualTo(groupId)
                .andAttendDateGreaterThanOrEqualTo(startDate)
                .andAttendDateLessThanOrEqualTo(endDate);
        if(userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        example.setOrderByClause(" user_id, attend_date, attend_number, attend_kind");
        return selectByExample(example);
    }

    default List<AttendanceFlow> selectByUserIdAndMonth(Integer groupId, Integer userId, String month) {
        AttendanceFlowExample example = new AttendanceFlowExample();
        example.createCriteria()
                .andAttendGroupIdEqualTo(groupId)
                .andUserIdEqualTo(userId)
                .andAttendDateLike(month + "%");
        return selectByExample(example);
    }

    void handleNoAttendanceFlow(@Param("groupClassesId") Integer groupClassesId, @Param("date")String date, @Param("attendNumber")Integer attendNumber, @Param("attendKind")String attendKind);

    void handleAutoAttendanceFlow(@Param("groupClassesId") Integer groupClassesId, @Param("date")String date, @Param("attendNumber")Integer attendNumber, @Param("attendKind")String attendKind);
}

