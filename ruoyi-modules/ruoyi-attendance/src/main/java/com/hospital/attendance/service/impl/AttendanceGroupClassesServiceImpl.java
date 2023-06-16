package com.hospital.attendance.service.impl;

import com.hospital.attendance.domain.AttendanceGroupClasses;
import com.hospital.attendance.domain.bo.AttendanceGroupClassesBo;
import com.hospital.attendance.domain.vo.AttendanceGroupClassesVo;
import com.hospital.attendance.mapper.AttendanceClassesMapper;
import com.hospital.attendance.mapper.AttendanceGroupClassesMapper;
import com.hospital.attendance.mapper.AttendanceGroupMapper;
import com.hospital.attendance.service.IAttendanceGroupClassesService;
import com.hospital.attendance.utils.AttendanceUtils;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.mybatis.core.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 排班班次表(AttendanceGroupClasses) Service
 *
 * @author makejava
 * @since 2023-05-21 17:56:38
 */
@Service
@RequiredArgsConstructor
public class AttendanceGroupClassesServiceImpl extends BaseServiceImpl<AttendanceGroupClassesMapper, AttendanceGroupClasses, AttendanceGroupClassesVo, AttendanceGroupClassesBo> implements IAttendanceGroupClassesService {

    private AttendanceGroupMapper attendanceGroupMapper;
    private AttendanceClassesMapper attendanceClassesMapper;

    @Override
    public int insert(AttendanceGroupClassesBo bo) {
        validateExist(bo.getGroupId(), bo.getClassesId());

        List<AttendanceGroupClasses> groupClassesList = mapper.exsitGroupByWeek(bo.getGroupId(), bo.getWeekly());
        if (!groupClassesList.isEmpty()) {
            throw new ServiceException("周次已经有关联关系，不能添加");
        }
        int result = super.insert(bo);
        AttendanceUtils.removeGroupClassesCache(bo.getGroupId());
        return result;
    }

    @Override
    public int update(AttendanceGroupClassesBo bo) {
        int result = super.update(bo);
        AttendanceUtils.removeGroupClassesCache(bo.getGroupId());
        return result;
    }

    private void validateExist(Long groupId, Long classesId) {
        //校验考勤组是否存在
        if (attendanceGroupMapper.selectById(groupId) == null) {
            throw new ServiceException("考勤组不存在");
        }
        //校验班次是否存在
        if (attendanceClassesMapper.selectById(classesId) == null) {
            throw new ServiceException("班次不存在");
        }
    }
}
