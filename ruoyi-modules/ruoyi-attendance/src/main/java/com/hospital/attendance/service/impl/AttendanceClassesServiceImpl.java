package com.hospital.attendance.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital.attendance.domain.AttendanceClasses;
import com.hospital.attendance.domain.AttendanceGroupClasses;
import com.hospital.attendance.domain.bo.AttendanceClassesBo;
import com.hospital.attendance.domain.vo.AttendanceClassesVo;
import com.hospital.attendance.mapper.AttendanceClassesMapper;
import com.hospital.attendance.mapper.AttendanceGroupClassesMapper;
import com.hospital.attendance.service.IAttendanceClassesService;
import com.hospital.attendance.utils.AttendanceUtils;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 班次设置表 Service
 *
 * @author yaoyingjie
 */
@Service
@RequiredArgsConstructor
public class AttendanceClassesServiceImpl extends BaseServiceImpl<AttendanceClassesMapper, AttendanceClasses, AttendanceClassesVo, AttendanceClassesBo> implements IAttendanceClassesService {

    private final AttendanceGroupClassesMapper attendanceGroupClassesMapper;

    @Override
    public TableDataInfo<AttendanceClassesVo> selectPageList(AttendanceClassesBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AttendanceClasses> lqw = buildQueryWrapper(bo);
        Page<AttendanceClassesVo> page = mapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    @Override
    public int deleteById(Long id) {
        List<AttendanceGroupClasses> attendanceGroupClasses = attendanceGroupClassesMapper.selectByClassesId(id);
        if (CollUtil.isNotEmpty(attendanceGroupClasses)) {
            throw new ServiceException("该班次已被考勤组使用，不能删除");
        }
        int result = super.deleteById(id);
        AttendanceUtils.initClassesCache();
        return result;
    }

    @Override
    public List<AttendanceClassesVo> listByHosId(Long hosId) {
        return mapper.listByHosId(hosId);
    }

    private LambdaQueryWrapper<AttendanceClasses> buildQueryWrapper(AttendanceClassesBo bo) {
        return new LambdaQueryWrapperX<AttendanceClasses>()
            .like(AttendanceClasses::getName, bo.getName());
    }
}
