package com.hospital.attendance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital.attendance.domain.AttendanceLeaveConfig;
import com.hospital.attendance.domain.bo.AttendanceLeaveConfigBo;
import com.hospital.attendance.domain.vo.AttendanceLeaveConfigVo;
import com.hospital.attendance.mapper.AttendanceLeaveConfigMapper;
import com.hospital.attendance.mapper.AttendanceLeaveRecordMapper;
import com.hospital.attendance.service.IAttendanceLeaveConfigService;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 请假配置 服务实现
 *
 * @author liguoxian
 */
@RequiredArgsConstructor
@Service
public class AttendanceLeaveConfigServiceImpl extends BaseServiceImpl<AttendanceLeaveConfigMapper, AttendanceLeaveConfig, AttendanceLeaveConfigVo, AttendanceLeaveConfigBo> implements IAttendanceLeaveConfigService {

    private final AttendanceLeaveRecordMapper attendanceLeaveRecordMapper;

    @Override
    public TableDataInfo<AttendanceLeaveConfigVo> selectPageList(AttendanceLeaveConfigBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AttendanceLeaveConfig> lqw = buildQueryWrapper(bo);
        Page<AttendanceLeaveConfigVo> page = mapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    @Override
    public List<AttendanceLeaveConfigVo> selectList(AttendanceLeaveConfigBo bo) {
        LambdaQueryWrapper<AttendanceLeaveConfig> lqw = buildQueryWrapper(bo);
        return mapper.selectVoList(lqw);
    }

    @Override
    public int updateStatus(Long roleId, String status) {
        return mapper.update(null,
            new LambdaUpdateWrapper<AttendanceLeaveConfig>()
                .set(AttendanceLeaveConfig::getStatus, status)
                .eq(AttendanceLeaveConfig::getId, roleId));
    }

    @Override
    public int deleteByIds(Long[] ids) {
        for (Long id : ids) {
            AttendanceLeaveConfig attendanceLeaveConfig = mapper.selectById(id);
            if (attendanceLeaveConfig != null) {
                long num = attendanceLeaveRecordMapper.getRecordNumByConfig(id);
                if (num > 0) {
                    throw new ServiceException(String.format("请假配置【%s】已被使用，请勿删除。如果不再使用请停用即可！", attendanceLeaveConfig.getLeaveName()));
                }
            }
        }
        return mapper.deleteBatchIds(Arrays.asList(ids));
    }

    private LambdaQueryWrapper<AttendanceLeaveConfig> buildQueryWrapper(AttendanceLeaveConfigBo bo) {
        return new LambdaQueryWrapperX<AttendanceLeaveConfig>()
            .eqIfPresent(AttendanceLeaveConfig::getLeaveName, bo.getLeaveName())
            .eqIfPresent(AttendanceLeaveConfig::getLeaveUnit, bo.getLeaveUnit())
            .eqIfPresent(AttendanceLeaveConfig::getLeaveTimeType, bo.getLeaveTimeType())
            .eqIfPresent(AttendanceLeaveConfig::getStatus, bo.getStatus())
            .eqIfPresent(AttendanceLeaveConfig::getCreateDept, bo.getCreateDept())
            .eqIfPresent(AttendanceLeaveConfig::getRemark, bo.getRemark())
            ;
    }
}
