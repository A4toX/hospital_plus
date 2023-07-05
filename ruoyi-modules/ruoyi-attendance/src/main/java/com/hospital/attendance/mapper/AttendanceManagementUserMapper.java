package com.hospital.attendance.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hospital.attendance.domain.AttendanceManagementUser;
import com.hospital.attendance.domain.vo.AttendanceManagementUserVo;
import com.hospital.attendance.domain.vo.attendUser.GroupManagerReqVo;
import com.hospital.attendance.domain.vo.attendUser.AttendanceMUserRespVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 考勤组负责人(AttendanceManagementUser) DAO
 *
 * @author makejava
 * @since 2023-05-21 19:29:23
 */
public interface AttendanceManagementUserMapper extends BaseMapperPlus<AttendanceManagementUser, AttendanceManagementUserVo> {

    default List<AttendanceManagementUser> selectByUserId(Long userId) {
        return selectList(new LambdaQueryWrapper<AttendanceManagementUser>().eq(AttendanceManagementUser::getUserId, userId));
    }

    default List<AttendanceManagementUser> selectByGroupId(Long groupId) {
        return selectList(new LambdaQueryWrapper<AttendanceManagementUser>().eq(AttendanceManagementUser::getGroupId, groupId));
    }
}

