package com.hospital.attendance.service.impl;

import com.demo.hospital.attendance.dao.AttendanceManagementUserDao;
import com.demo.hospital.attendance.model.*;
import com.demo.hospital.attendance.model.vo.attendUser.AddAttendanceUserVO;
import com.demo.hospital.attendance.model.vo.attendUser.AttendanceMUserReqVO;
import com.demo.hospital.attendance.model.vo.attendUser.AttendanceMUserRespVO;
import com.demo.hospital.common.base.controller.Result;
import com.demo.hospital.common.page.Page;
import com.demo.hospital.common.security.UserUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 考勤组负责人(AttendanceManagementUser) Service
 *
 * @author makejava
 * @since 2023-05-21 19:29:23
 */
@Service
public class AttendanceManagementUserService {

    @Resource
    private AttendanceManagementUserDao attendanceManagementUserDao;


    public Page<AttendanceManagementUser> findPage(AttendanceManagementUserPageReq pageReq) {
        Page<AttendanceManagementUserPageReq> pageParam = new Page<>(pageReq.getPageNo(), pageReq.getPageSize());
        pageParam.setParamObj(pageReq);
        List<AttendanceManagementUser> list = attendanceManagementUserDao.findPage(pageParam);
        return new Page<>(pageParam, list);
    }

    public Result insert(AddAttendanceUserVO entity) {
        //先获取考勤组下的所有人员
        AttendanceManagementUserExample example = new AttendanceManagementUserExample();
        example.createCriteria().andGroupIdEqualTo(entity.getGroupId());
        List<AttendanceManagementUser> attendanceManagementUsers = attendanceManagementUserDao.selectByExample(example);
        //和前端传过来的人员进行比较，如果前端传过来的人员不在考勤组下，则删除
        List<Integer> userIds = entity.getAddUserIds();
        if (userIds == null || userIds.size() == 0) {
            return Result.error("请选择人员");
        }
        //userIds去重
        List<Integer> distinctUserIds = userIds.stream().distinct().collect(java.util.stream.Collectors.toList());
        //获取attendanceManagementUsers中的userId
        List<Integer> userIdList = attendanceManagementUsers.stream().map(AttendanceManagementUser::getUserId).collect(java.util.stream.Collectors.toList());
        //使用steam比对distinctUserIds和userIdList，获取需要添加的人员
        List<Integer> addUserIds = distinctUserIds.stream().filter(userId -> !userIdList.contains(userId)).collect(java.util.stream.Collectors.toList());
        //使用steam比对distinctUserIds和userIdList，获取需要删除的人员
        List<Integer> delUserIds = userIdList.stream().filter(userId -> !distinctUserIds.contains(userId)).collect(java.util.stream.Collectors.toList());
        //删除
        if (delUserIds != null && delUserIds.size() > 0) {
            AttendanceManagementUserExample delExample = new AttendanceManagementUserExample();
            delExample.createCriteria().andGroupIdEqualTo(entity.getGroupId()).andUserIdIn(delUserIds);
            attendanceManagementUserDao.deleteByExample(delExample);
        }
        //添加
        if (addUserIds != null && addUserIds.size() > 0) {
            addUserIds.forEach(userId -> {
                AttendanceManagementUser attendanceManagementUser = new AttendanceManagementUser();
                attendanceManagementUser.setGroupId(entity.getGroupId());
                attendanceManagementUser.setUserId(userId);
                attendanceManagementUser.setCreateBy(UserUtil.getCurrentUserId());
                attendanceManagementUser.setCreateTime();
                attendanceManagementUserDao.insertSelective(attendanceManagementUser);
            });
        }
        return Result.success();
    }

    public Result update(AttendanceManagementUser entity) {
        entity.setUpdateTime();
        entity.setUpdateBy(UserUtil.getCurrentUserId());
        return Result.of(attendanceManagementUserDao.updateByPrimaryKeySelective(entity));
    }


    public List<AttendanceMUserRespVO> listByGroupId(AttendanceMUserReqVO reqVO) {
        return attendanceManagementUserDao.listByGroupId(reqVO);
    }

    public List<AttendanceMUserRespVO> listAllStaffByHosId(AttendanceMUserReqVO reqVO) {
        return attendanceManagementUserDao.listAllStaffByHosId(reqVO);
    }
}
