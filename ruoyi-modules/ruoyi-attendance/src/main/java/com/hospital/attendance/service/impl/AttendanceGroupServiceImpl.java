package com.hospital.attendance.service.impl;

import com.demo.hospital.attendance.model.*;
import com.demo.hospital.attendance.model.vo.AttendanceGroupClassesSimpleRespVO;
import com.demo.hospital.attendance.utils.AttendanceUtils;
import com.demo.hospital.common.base.controller.Result;
import com.demo.hospital.common.exceptions.BizException;
import com.demo.hospital.common.page.Page;
import com.demo.hospital.common.security.UserUtil;
import com.hospital.attendance.domain.AttendanceGroup;
import com.hospital.attendance.domain.bo.AttendanceGroupBo;
import com.hospital.attendance.domain.vo.AttendanceGroupVO;
import com.hospital.attendance.mapper.AttendanceClassesMapper;
import com.hospital.attendance.mapper.AttendanceGroupAreaMapper;
import com.hospital.attendance.mapper.AttendanceGroupClassesMapper;
import com.hospital.attendance.mapper.AttendanceGroupMapper;
import com.hospital.attendance.service.AttendanceGroupService;
import lombok.RequiredArgsConstructor;
import org.dromara.common.mybatis.core.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.demo.hospital.common.constant.Constant.DELETE_FLAG;
import static com.demo.hospital.common.constant.Constant.YES;

/**
 * 考勤组信息表 Service
 *
 * @author yaoyingjie
 */
@Service
@RequiredArgsConstructor
public class AttendanceGroupServiceImpl extends BaseServiceImpl<AttendanceGroupMapper, AttendanceGroup, AttendanceGroupVO, AttendanceGroupBo> implements AttendanceGroupService {

    private AttendanceClassesMapper attendanceClassesMapper;
    private AttendanceGroupClassesMapper attendanceGroupClassesMapper;
    private AttendanceGroupAreaMapper attendanceGroupAreaMapper;

    @Override
    public AttendanceGroupVO selectById(Long id) {
        AttendanceGroupVO vo = new AttendanceGroupVO();
        //根据考勤组id查询考勤组信息
        AttendanceGroup attendanceGroup = mapper.selectById(id);
        if (attendanceGroup == null) {
            return vo;
        }
        vo.setAttendanceGroup(attendanceGroup);
        //如果考勤组为固定班次
        if (attendanceGroup.getGroupType() == 1) {
            //根据考勤组id查询考勤班次信息
            List<AttendanceGroupClassesSimpleRespVO> classVOList = attendanceGroupClassesDao.selectByGroupIdSimple(id);
            vo.setGroupClassesSimpleRespVOS(classVOList);
        }
        //封装考勤地点信息
        if (attendanceGroup.getGroupMethod() == 1) {//定位考勤
            AttendanceGroupAreaExample example = new AttendanceGroupAreaExample();
            example.createCriteria().andGroupIdEqualTo(id).andIsDeleteEqualTo(YES);
            List<AttendanceGroupArea> list = attendanceGroupAreaDao.selectByExample(example);
            if (!list.isEmpty()) {
                vo.setAttendanceGroupAreaList(list);
            }
        }
        return vo;
    }

    public Page<AttendanceGroup> findPage(AttendanceGroupPageReq pageReq) {
        Page<AttendanceGroupPageReq> pageParam = new Page<>(pageReq.getPageNo(), pageReq.getPageSize());
        pageParam.setParamObj(pageReq);
        List<AttendanceGroup> list = attendanceGroupDao.findPage(pageParam);
        return new Page<>(pageParam, list);
    }

    public Result insert(AttendanceGroup entity) {
        //校验同医院下是否有重名的考勤组
        validateName(entity.getGroupName(), entity.getHosId(), null);
        entity.setCreateTime();
        entity.setCreateBy(UserUtil.getCurrentUserId());
        attendanceGroupDao.insertSelective(entity);
        return Result.success(entity.getId());
    }

    private void validateName(String groupName, Integer hosId, Integer id) {
        AttendanceGroupExample example = new AttendanceGroupExample();
        if (id != null) {
            example.createCriteria().andGroupNameEqualTo(groupName).andHosIdEqualTo(hosId).andIdNotEqualTo(id);
        } else {
            example.createCriteria().andGroupNameEqualTo(groupName).andHosIdEqualTo(hosId);
        }
        List<AttendanceGroup> list = attendanceGroupDao.selectByExample(example);
        if (list.size() > 0) {
            throw new BizException("同医院下考勤组名称不能重复");
        }
    }

    public Result update(AttendanceGroup entity) {
        //校验同医院下是否有重名的考勤组
        validateName(entity.getGroupName(), entity.getHosId(), entity.getId());
        entity.setUpdateTime();
        entity.setUpdateBy(UserUtil.getCurrentUserId());
        //清除缓存
        AttendanceUtils.removeGroupCache(entity.getId());
        return Result.of(attendanceGroupDao.updateByPrimaryKeySelective(entity));
    }

    //考勤组执行逻辑删除
    public void delete(Integer id) {
        AttendanceGroup attendanceGroup = new AttendanceGroup();
        attendanceGroup.setId(id);
        attendanceGroup.setIsDelete(DELETE_FLAG);
        attendanceGroup.setUpdateTime();
        attendanceGroup.setUpdateBy(UserUtil.getCurrentUserId());
        attendanceGroupDao.updateByPrimaryKeySelective(attendanceGroup);
        //清除缓存
        AttendanceUtils.removeGroupCache(id);
        AttendanceUtils.removeGroupClassesCache(id);
        AttendanceUtils.removeGroupAreasCache(id);
        AttendanceUtils.removeGroupUserCache();
    }
}
