package com.hospital.attendance.service.impl;

import com.demo.hospital.attendance.dao.AttendanceClassesDao;
import com.demo.hospital.attendance.dao.AttendanceGroupClassesDao;
import com.demo.hospital.attendance.dao.AttendanceGroupDao;
import com.demo.hospital.attendance.model.AttendanceGroupClasses;
import com.demo.hospital.attendance.utils.AttendanceUtils;
import com.demo.hospital.common.base.controller.Result;
import com.demo.hospital.common.exceptions.BizException;
import com.demo.hospital.common.security.UserUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 排班班次表(AttendanceGroupClasses) Service
 *
 * @author makejava
 * @since 2023-05-21 17:56:38
 */
@Service
public class AttendanceGroupClassesService {

    @Resource
    private AttendanceGroupClassesDao attendanceGroupClassesDao;
    @Resource
    private AttendanceGroupDao attendanceGroupDao;

    @Resource
    private AttendanceClassesDao attendanceClassesDao;




    public Result insert(AttendanceGroupClasses entity) {
        //校验考勤组和班次是否存在
        validateExist(entity.getGroupId(),entity.getClassesId());
        //校验考勤组的周次是否已经有关联关系
        List<AttendanceGroupClasses> groupClassesList = attendanceGroupClassesDao.exsitGroupByWeek(entity.getGroupId(), entity.getWeekly());
        if (!groupClassesList.isEmpty()){
            throw new BizException("周次已经有关联关系，不能添加");
        }
        entity.setCreateTime();
        entity.setCreateBy(UserUtil.getCurrentUserId());
        attendanceGroupClassesDao.insertSelective(entity);
        //清除缓存
        AttendanceUtils.removeGroupClassesCache(entity.getGroupId());
        return Result.success(entity.getId());
    }

    private void validateExist(Integer groupId, Integer classesId) {
        //校验考勤组是否存在
        if(attendanceGroupDao.selectByPrimaryKey(groupId)==null){
            throw new BizException("考勤组不存在");
        }
        //校验班次是否存在
        if(attendanceClassesDao.selectByPrimaryKey(classesId)==null){
            throw new BizException("班次不存在");
        }
    }

    public Result update(AttendanceGroupClasses entity) {
        entity.setUpdateTime();
        entity.setUpdateBy(UserUtil.getCurrentUserId());
        //清除缓存
        AttendanceUtils.removeGroupClassesCache(entity.getGroupId());
        return Result.of(attendanceGroupClassesDao.updateByPrimaryKeySelective(entity));
    }

}
