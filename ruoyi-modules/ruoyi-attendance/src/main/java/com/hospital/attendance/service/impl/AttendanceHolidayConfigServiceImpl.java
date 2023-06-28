package com.hospital.attendance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.service.BaseServiceImpl;
import com.hospital.attendance.domain.AttendanceHolidayConfig;
import com.hospital.attendance.domain.bo.AttendanceHolidayConfigBo;
import com.hospital.attendance.domain.vo.AttendanceHolidayConfigVo;
import com.hospital.attendance.mapper.AttendanceHolidayConfigMapper;
import com.hospital.attendance.service.IAttendanceHolidayConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 节假日配置 服务实现
 *
 * @author liguoxian
 */
@RequiredArgsConstructor
@Service
public class AttendanceHolidayConfigServiceImpl extends BaseServiceImpl<AttendanceHolidayConfigMapper, AttendanceHolidayConfig, AttendanceHolidayConfigVo, AttendanceHolidayConfigBo> implements IAttendanceHolidayConfigService {

    @Override
    public TableDataInfo<AttendanceHolidayConfigVo> selectPageList(AttendanceHolidayConfigBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AttendanceHolidayConfig> lqw = buildQueryWrapper(bo);
        Page<AttendanceHolidayConfigVo> page = mapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    @Override
    public List<AttendanceHolidayConfigVo> selectList(AttendanceHolidayConfigBo bo) {
        LambdaQueryWrapper<AttendanceHolidayConfig> lqw = buildQueryWrapper(bo);
        return mapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<AttendanceHolidayConfig> buildQueryWrapper(AttendanceHolidayConfigBo bo) {
        return new LambdaQueryWrapperX<AttendanceHolidayConfig>()
                .eqIfPresent(AttendanceHolidayConfig::getBelongYear, bo.getBelongYear())
                .eqIfPresent(AttendanceHolidayConfig::getBeginDate, bo.getBeginDate())
                .eqIfPresent(AttendanceHolidayConfig::getEndDate, bo.getEndDate())
                .eqIfPresent(AttendanceHolidayConfig::getHoliday, bo.getHoliday())
                .eqIfPresent(AttendanceHolidayConfig::getHolidayRemark, bo.getHolidayRemark())
                .eqIfPresent(AttendanceHolidayConfig::getInverseDays, bo.getInverseDays())
                .eqIfPresent(AttendanceHolidayConfig::getCreateDept, bo.getCreateDept())
                .eqIfPresent(AttendanceHolidayConfig::getRemark, bo.getRemark())
        ;
    }
}
