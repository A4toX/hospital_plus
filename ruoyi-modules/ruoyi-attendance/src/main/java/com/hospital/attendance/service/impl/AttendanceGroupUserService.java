package com.hospital.attendance.service.impl;

import com.demo.hospital.attendance.dao.AttendanceGroupUserDao;
import com.demo.hospital.attendance.model.AttendanceGroupUser;
import com.demo.hospital.attendance.model.AttendanceGroupUserExample;
import com.demo.hospital.attendance.model.AttendanceGroupUserPageReq;
import com.demo.hospital.attendance.model.vo.attendUser.AddAttendanceUserVO;
import com.demo.hospital.attendance.model.vo.attendUser.AttendanceUserReqVO;
import com.demo.hospital.attendance.model.vo.attendUser.AttendanceUserRespVO;
import com.demo.hospital.attendance.utils.AttendanceUtils;
import com.demo.hospital.common.base.controller.Result;
import com.demo.hospital.common.page.Page;
import com.demo.hospital.common.security.UserUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 考勤参与人员 Service
 *
 * @author yaoyingjie
 */
@Service
public class AttendanceGroupUserService {

    @Resource
    private AttendanceGroupUserDao attendanceGroupUserDao;

    public AttendanceGroupUser get(Integer id) {
        return attendanceGroupUserDao.selectByPrimaryKey(id);
    }

    public Page<AttendanceGroupUser> findPage(AttendanceGroupUserPageReq pageReq) {
        Page<AttendanceGroupUserPageReq> pageParam = new Page<>(pageReq.getPageNo(), pageReq.getPageSize());
        pageParam.setParamObj(pageReq);
        List<AttendanceGroupUser> list = attendanceGroupUserDao.findPage(pageParam);
        return new Page<>(pageParam, list);
    }

    //新增或删除考勤组下的人员
    public Result insert(AddAttendanceUserVO entity) {
        //先获取考勤组下的所有人员
        AttendanceGroupUserExample example = new AttendanceGroupUserExample();
        example.createCriteria().andGroupIdEqualTo(entity.getGroupId());
        List<AttendanceGroupUser> attendanceGroupUsers = attendanceGroupUserDao.selectByExample(example);
        //和前端传过来的人员进行比较，如果前端传过来的人员不在考勤组下，则删除
        List<Integer> userIds = entity.getAddUserIds();
        if (userIds == null || userIds.size() == 0) {
            return Result.error("请选择人员");
        }
        //userIds去重
        List<Integer> distinctUserIds = userIds.stream().distinct().collect(java.util.stream.Collectors.toList());
        //获取attendanceGroupUsers中的userId
        List<Integer> userIdsInGroup = attendanceGroupUsers.stream().map(AttendanceGroupUser::getUserId).collect(java.util.stream.Collectors.toList());
        //使用steam比对distinctUserIds和userIdList，获取需要添加的人员
        List<Integer> addUserIds = distinctUserIds.stream().filter(item -> !userIdsInGroup.contains(item)).collect(java.util.stream.Collectors.toList());
        //使用steam比对distinctUserIds和userIdList，获取需要删除的人员
        List<Integer> delUserIds = userIdsInGroup.stream().filter(item -> !distinctUserIds.contains(item)).collect(java.util.stream.Collectors.toList());
        //删除
        if (delUserIds != null && delUserIds.size() > 0) {
            for (Integer delUserId : delUserIds) {
                AttendanceGroupUserExample delExample = new AttendanceGroupUserExample();
                delExample.createCriteria().andGroupIdEqualTo(entity.getGroupId()).andUserIdEqualTo(delUserId);
                attendanceGroupUserDao.deleteByExample(delExample);
            }
        }
        //新增
        if (addUserIds != null && addUserIds.size() > 0) {
            for (Integer addUserId : addUserIds) {
                AttendanceGroupUser attendanceGroupUser = new AttendanceGroupUser();
                attendanceGroupUser.setGroupId(entity.getGroupId());
                attendanceGroupUser.setUserId(addUserId);
                attendanceGroupUser.setCreateTime();
                attendanceGroupUser.setCreateBy(UserUtil.getCurrentUserId());
                attendanceGroupUserDao.insertSelective(attendanceGroupUser);
            }
        }
        //清除用户缓存
        AttendanceUtils.removeGroupUserCache();
        return Result.success();
    }

    public List<AttendanceUserRespVO> listByGroupId(AttendanceUserReqVO reqVO) {
        return attendanceGroupUserDao.listByGroupId(reqVO);
    }
}
