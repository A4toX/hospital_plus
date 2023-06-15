package com.hospital.attendance.mapper;

import com.demo.hospital.attendance.model.AttendanceManagementUser;
import com.demo.hospital.attendance.model.AttendanceManagementUserExample;
import com.demo.hospital.attendance.model.vo.attendUser.AttendanceMUserReqVO;
import com.demo.hospital.attendance.model.vo.attendUser.AttendanceMUserRespVO;
import com.demo.hospital.common.base.dao.MyBatisBaseDao;

import java.util.List;

/**
 * 考勤组负责人(AttendanceManagementUser) DAO
 *
 * @author makejava
 * @since 2023-05-21 19:29:23
 */
public interface AttendanceManagementUserMapper extends MyBatisBaseDao<AttendanceManagementUser, Integer, AttendanceManagementUserExample> {

    List<AttendanceMUserRespVO> listByGroupId(AttendanceMUserReqVO reqVO);

    default List<AttendanceManagementUser> selectByUserId(Integer userId) {
        AttendanceManagementUserExample example = new AttendanceManagementUserExample();
        example.createCriteria().andUserIdEqualTo(userId).andIsDeleteEqualTo("1");
        return selectByExample(example);
    }

    List<AttendanceMUserRespVO> listAllStaffByHosId(AttendanceMUserReqVO reqVO);
}

