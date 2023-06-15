package com.hospital.attendance.mapper;

import com.demo.hospital.attendance.model.AttendanceGroupArea;
import com.demo.hospital.attendance.model.AttendanceGroupAreaExample;
import com.demo.hospital.common.base.dao.MyBatisBaseDao;

import java.util.List;

/**
 * 考勤地点表 DAO
 *
 * @author yaoyingjie
 */
public interface AttendanceGroupAreaMapper extends MyBatisBaseDao<AttendanceGroupArea, Integer, AttendanceGroupAreaExample> {

    default List<AttendanceGroupArea> selectByGroupId(Integer groupId) {
        AttendanceGroupAreaExample example = new AttendanceGroupAreaExample();
        example.createCriteria().andGroupIdEqualTo(groupId).andIsDeleteEqualTo("1");
        return selectByExample(example);
    }

    default List<AttendanceGroupArea> listByHosId(Integer hosId){
        AttendanceGroupAreaExample example = new AttendanceGroupAreaExample();
        example.createCriteria().andHosIdEqualTo(hosId);
        return selectByExample(example);
    }

}

