package com.hospital.attendance.service;

import com.demo.hospital.attendance.dao.AttendanceClassesDao;
import com.demo.hospital.attendance.dao.AttendanceGroupClassesDao;
import com.demo.hospital.attendance.model.AttendanceClasses;
import com.demo.hospital.attendance.model.AttendanceClassesPageReq;
import com.demo.hospital.attendance.model.AttendanceGroupClasses;
import com.demo.hospital.attendance.model.AttendanceGroupClassesExample;
import com.demo.hospital.attendance.utils.AttendanceUtils;
import com.demo.hospital.common.base.controller.Result;
import com.demo.hospital.common.exceptions.BizException;
import com.demo.hospital.common.page.Page;
import com.demo.hospital.common.security.UserUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.demo.hospital.common.constant.Constant.DELETE_FLAG;

/**
 * 班次设置表 Service
 *
 * @author yaoyingjie
 */
@Service
public class AttendanceClassesService {

    @Resource
    private AttendanceClassesDao attendanceClassesDao;
    @Resource
    private AttendanceGroupClassesDao attendanceGroupClassesDao;

    public AttendanceClasses get(Integer id) {
        return attendanceClassesDao.selectByPrimaryKey(id);
    }

    public Page<AttendanceClasses> findPage(AttendanceClassesPageReq pageReq) {
        Page<AttendanceClassesPageReq> pageParam = new Page<>(pageReq.getPageNo(), pageReq.getPageSize());
        pageParam.setParamObj(pageReq);
        List<AttendanceClasses> list = attendanceClassesDao.findPage(pageParam);
        return new Page<>(pageParam, list);
    }

    public Result insert(AttendanceClasses entity) {
        entity.setCreateTime();
        entity.setCreateBy(UserUtil.getCurrentUserId());
        attendanceClassesDao.insertSelective(entity);
        //重新加载缓存
        AttendanceUtils.initClassesCache();
        return Result.success(entity.getId());
    }

    public Result update(AttendanceClasses entity) {
        entity.setUpdateTime();
        entity.setUpdateBy(UserUtil.getCurrentUserId());
        //重新加载缓存
        AttendanceUtils.initClassesCache();
        return Result.of(attendanceClassesDao.updateByPrimaryKeySelective(entity));
    }

    public Result delete(Integer id) {
        //查看班次是否被考勤组使用
        AttendanceGroupClassesExample example = new AttendanceGroupClassesExample();
        example.createCriteria().andClassesIdEqualTo(id);
        List<AttendanceGroupClasses> list = attendanceGroupClassesDao.selectByExample(example);
        if (list.size() > 0) {
            throw new BizException("该班次已被考勤组使用，不能删除");
        }
        //逻辑删除，更新删除标识
        AttendanceClasses entity = new AttendanceClasses();
        entity.setId(id);
        entity.setIsDelete(DELETE_FLAG);
        entity.setUpdateTime();
        entity.setUpdateBy(UserUtil.getCurrentUserId());
        attendanceClassesDao.updateByPrimaryKeySelective(entity);
        //重新加载缓存
        AttendanceUtils.initClassesCache();
        return Result.success();
    }

    public List<AttendanceClasses> listByHosId(Integer hosId) {
        return attendanceClassesDao.listByHosId(hosId);
    }
}
