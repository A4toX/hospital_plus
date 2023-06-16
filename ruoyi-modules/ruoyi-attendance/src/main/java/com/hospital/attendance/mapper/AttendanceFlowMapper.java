package com.hospital.attendance.mapper;

import com.hospital.attendance.domain.AttendanceFlow;
import com.hospital.attendance.domain.vo.AttendanceFlowVo;
import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;

import java.util.List;

/**
 * 考勤记录表 DAO
 *
 * @author liguoxian
 */
public interface AttendanceFlowMapper extends BaseMapperPlus<AttendanceFlow, AttendanceFlowVo> {

    default List<AttendanceFlowVo> selectByDate(Long groupId, Long userId, String date) {
        return selectVoList(new LambdaQueryWrapperX<AttendanceFlow>()
            .eq(AttendanceFlow::getAttendGroupId, groupId)
            .eq(AttendanceFlow::getAttendDate, date)
            .eqIfPresent(AttendanceFlow::getUserId, userId));
    }

    default List<AttendanceFlow> selectByDateRange(Long groupId, Integer userId, String startDate, String endDate) {
        return selectList(new LambdaQueryWrapperX<AttendanceFlow>()
            .eqIfPresent(AttendanceFlow::getUserId, userId)
            .eq(AttendanceFlow::getAttendGroupId, groupId)
            .ge(AttendanceFlow::getAttendDate, startDate)
            .le(AttendanceFlow::getAttendDate, endDate)
            .orderByAsc(AttendanceFlow::getUserId, AttendanceFlow::getAttendDate, AttendanceFlow::getAttendNumber, AttendanceFlow::getAttendKind));
    }

    default List<AttendanceFlowVo> selectByUserIdAndMonth(Long groupId, Long userId, String month) {
        return selectVoList(new LambdaQueryWrapperX<AttendanceFlow>()
            .eqIfPresent(AttendanceFlow::getUserId, userId)
            .eq(AttendanceFlow::getAttendGroupId, groupId)
            .like(AttendanceFlow::getAttendDate, month + "%"));
    }

    void handleNoAttendanceFlow(@Param("groupClassesId") Long groupClassesId, @Param("date")String date, @Param("attendNumber")Integer attendNumber, @Param("attendKind")String attendKind);

    void handleAutoAttendanceFlow(@Param("groupClassesId") Long groupClassesId, @Param("date")String date, @Param("attendNumber")Integer attendNumber, @Param("attendKind")String attendKind);
}

