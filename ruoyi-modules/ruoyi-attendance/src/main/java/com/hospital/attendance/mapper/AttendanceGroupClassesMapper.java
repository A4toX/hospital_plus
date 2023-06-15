package com.hospital.attendance.mapper;

import com.demo.hospital.attendance.model.AttendanceGroupClasses;
import com.demo.hospital.attendance.model.AttendanceGroupClassesExample;
import com.demo.hospital.attendance.model.vo.AttendanceGroupClassVO;
import com.demo.hospital.attendance.model.vo.AttendanceGroupClassesSimpleRespVO;
import com.demo.hospital.common.base.dao.MyBatisBaseDao;

import java.util.List;

/**
 * 排班班次表(AttendanceGroupClasses) DAO
 *
 * @author makejava
 * @since 2023-05-21 17:56:38
 */
public interface AttendanceGroupClassesMapper extends MyBatisBaseDao<AttendanceGroupClasses, Integer, AttendanceGroupClassesExample> {

    List<AttendanceGroupClassVO> selectByGroupId(Integer id);

    default List<AttendanceGroupClasses> exsitGroupByWeek(Integer groupId, Integer weekly){
        AttendanceGroupClassesExample example = new AttendanceGroupClassesExample();
        example.createCriteria().andGroupIdEqualTo(groupId).andWeeklyEqualTo(weekly);
        return selectByExample(example);
    }

    default List<AttendanceGroupClasses> getGroupIds(Integer classesId, Integer weekly) {
        AttendanceGroupClassesExample example = new AttendanceGroupClassesExample();
        example.createCriteria().andClassesIdEqualTo(classesId).andWeeklyEqualTo(weekly);
        return selectByExample(example);
    }

    List<AttendanceGroupClassesSimpleRespVO> selectByGroupIdSimple(Integer id);
}

