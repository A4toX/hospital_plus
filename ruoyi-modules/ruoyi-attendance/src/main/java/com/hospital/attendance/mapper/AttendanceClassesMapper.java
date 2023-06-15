package com.hospital.attendance.mapper;

import com.demo.hospital.attendance.model.AttendanceClasses;
import com.demo.hospital.attendance.model.AttendanceClassesExample;
import com.demo.hospital.common.base.dao.MyBatisBaseDao;

import java.util.List;

import static com.demo.hospital.common.constant.Constant.YES;

/**
 * 班次设置表(AttendanceClasses) DAO
 *
 * @author makejava
 * @since 2023-05-21 17:37:13
 */
public interface AttendanceClassesMapper extends MyBatisBaseDao<AttendanceClasses, Integer, AttendanceClassesExample> {

   default List<AttendanceClasses> listByHosId(Integer hosId){
         AttendanceClassesExample example = new AttendanceClassesExample();
         example.createCriteria().andHosIdEqualTo(hosId).andIsDeleteEqualTo(YES);
         return selectByExample(example);
   }

    default List<AttendanceClasses> selectAll() {
        AttendanceClassesExample example = new AttendanceClassesExample();
        example.createCriteria()
                .andIsDeleteEqualTo(YES);
        return selectByExample(example);
    }
}

