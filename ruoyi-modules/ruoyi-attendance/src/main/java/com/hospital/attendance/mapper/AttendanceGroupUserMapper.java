package com.hospital.attendance.mapper;

import com.demo.hospital.attendance.model.AttendanceGroupUser;
import com.demo.hospital.attendance.model.AttendanceGroupUserExample;
import com.demo.hospital.attendance.model.vo.attendUser.AttendanceUserReqVO;
import com.demo.hospital.attendance.model.vo.attendUser.AttendanceUserRespVO;
import com.demo.hospital.common.base.dao.MyBatisBaseDao;

import java.util.List;

/**
 * 考勤参与人员 DAO
 *
 * @author yaoyingjie
 */
public interface AttendanceGroupUserMapper extends MyBatisBaseDao<AttendanceGroupUser, Integer, AttendanceGroupUserExample> {

    default List<AttendanceGroupUser> selectByUserId(Integer userId) {
        AttendanceGroupUserExample example = new AttendanceGroupUserExample();
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andIsDeleteEqualTo("1");
        return selectByExample(example);
    }

    List<AttendanceUserRespVO> listByGroupId(AttendanceUserReqVO reqVO);

    default List<AttendanceGroupUser> selectAll() {
        AttendanceGroupUserExample example = new AttendanceGroupUserExample();
        example.createCriteria()
                .andIsDeleteEqualTo("1");
        return selectByExample(example);
    }
}

