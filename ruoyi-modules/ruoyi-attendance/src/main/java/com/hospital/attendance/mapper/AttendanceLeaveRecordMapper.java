package com.hospital.attendance.mapper;

import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import com.hospital.attendance.domain.AttendanceLeaveRecord;
import com.hospital.attendance.domain.vo.AttendanceLeaveRecordVo;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;

/**
 * 请假记录 数据层
 *
 * @author liguoxian
 */
public interface AttendanceLeaveRecordMapper extends BaseMapperPlus<AttendanceLeaveRecord, AttendanceLeaveRecordVo> {

    default long getRecordNumByConfig(Long leaveConfigId) {
        return selectCount(new LambdaQueryWrapperX<AttendanceLeaveRecord>()
            .eq(AttendanceLeaveRecord::getLeaveConfigId, leaveConfigId));
    }
}

