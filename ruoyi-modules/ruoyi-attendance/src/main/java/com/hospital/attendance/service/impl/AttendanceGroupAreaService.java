package com.hospital.attendance.service.impl;

import com.demo.hospital.attendance.dao.AttendanceGroupAreaDao;
import com.demo.hospital.attendance.model.AttendanceGroupArea;
import com.demo.hospital.attendance.utils.AttendanceUtils;
import com.demo.hospital.common.base.controller.Result;
import com.demo.hospital.common.security.UserUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.demo.hospital.common.constant.Constant.DELETE_FLAG;

/**
 * 考勤地点表 Service
 *
 * @author yaoyingjie
 */
@Service
public class AttendanceGroupAreaService {

    @Resource
    private AttendanceGroupAreaDao attendanceGroupAreaDao;

    public AttendanceGroupArea get(Integer id) {
        return attendanceGroupAreaDao.selectByPrimaryKey(id);
    }


    public Result insert(AttendanceGroupArea entity) {
        entity.setCreateTime();
        entity.setCreateBy(UserUtil.getCurrentUserId());
        attendanceGroupAreaDao.insertSelective(entity);
        //清除缓存
        AttendanceUtils.removeGroupAreasCache(entity.getGroupId());
        return Result.success(entity.getId());
    }

    public Result update(AttendanceGroupArea entity) {
        entity.setUpdateTime();
        entity.setUpdateBy(UserUtil.getCurrentUserId());
        //清除缓存
        AttendanceUtils.removeGroupAreasCache(entity.getGroupId());
        return Result.of(attendanceGroupAreaDao.updateByPrimaryKeySelective(entity));
    }

    public Result delete(Integer id) {
        //逻辑删除
        AttendanceGroupArea entity = new AttendanceGroupArea();
        entity.setId(id);
        entity.setIsDelete(DELETE_FLAG);
        update(entity);
        //清除缓存
        AttendanceUtils.removeGroupAreasCache(entity.getGroupId());
        return Result.success();
    }

    public List<AttendanceGroupArea> getHistoryByHosId(Integer hosId) {
        List<AttendanceGroupArea> areaList = attendanceGroupAreaDao.listByHosId(hosId);
        //根据地点中的经纬度去重
        List<AttendanceGroupArea> distinctList = areaList.stream()
                .distinct()
                .collect(Collectors.toList());
        return distinctList;
    }
}
