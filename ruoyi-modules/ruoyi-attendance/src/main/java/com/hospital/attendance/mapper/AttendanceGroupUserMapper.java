package com.hospital.attendance.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hospital.attendance.domain.AttendanceGroupUser;
import com.hospital.attendance.domain.vo.AttendanceGroupUserVo;
import com.hospital.attendance.domain.vo.attendUser.AttendanceUserReqVO;
import com.hospital.attendance.domain.vo.attendUser.AttendanceUserRespVO;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 考勤参与人员 DAO
 *
 * @author yaoyingjie
 */
public interface AttendanceGroupUserMapper extends BaseMapperPlus<AttendanceGroupUser, AttendanceGroupUserVo> {

    default List<AttendanceGroupUser> selectByUserId(Long userId) {
        return selectList(new LambdaQueryWrapper<AttendanceGroupUser>().eq(AttendanceGroupUser::getUserId, userId));
    }

    List<AttendanceUserRespVO> listByGroupId(AttendanceUserReqVO reqVO);
}

