package com.hospital.attendance.service.impl;

import com.hospital.attendance.domain.AttendanceGroupArea;
import com.hospital.attendance.domain.bo.AttendanceGroupAreaBo;
import com.hospital.attendance.domain.vo.AttendanceGroupAreaVo;
import com.hospital.attendance.mapper.AttendanceGroupAreaMapper;
import com.hospital.attendance.service.IAttendanceGroupAreaService;
import com.hospital.attendance.utils.AttendanceUtils;
import lombok.RequiredArgsConstructor;
import org.dromara.common.mybatis.core.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 考勤地点表 Service
 *
 * @author yaoyingjie
 */
@Service
@RequiredArgsConstructor
public class AttendanceGroupAreaServiceImpl extends BaseServiceImpl<AttendanceGroupAreaMapper, AttendanceGroupArea, AttendanceGroupAreaVo, AttendanceGroupAreaBo> implements IAttendanceGroupAreaService {

    @Override
    public int deleteById(Long id) {
        int result = super.deleteById(id);
        AttendanceGroupAreaVo data = selectById(id);
        if (data != null) {
            AttendanceUtils.removeGroupAreasCache(data.getGroupId());
        }
        return result;
    }

    @Override
    public int insert(AttendanceGroupAreaBo bo) {
        int result = super.insert(bo);
        AttendanceUtils.removeGroupAreasCache(bo.getGroupId());
        return result;
    }

    @Override
    public int update(AttendanceGroupAreaBo bo) {
        int result = super.update(bo);
        AttendanceUtils.removeGroupAreasCache(bo.getGroupId());
        return result;
    }

    @Override
    public List<AttendanceGroupAreaVo> getHistoryByHosId(Long hosId) {
        List<AttendanceGroupAreaVo> areaList = mapper.listByHosId(hosId);
        //根据地点中的经纬度去重
        List<AttendanceGroupAreaVo> distinctList = areaList.stream()
                .distinct()
                .collect(Collectors.toList());
        return distinctList;
    }
}
