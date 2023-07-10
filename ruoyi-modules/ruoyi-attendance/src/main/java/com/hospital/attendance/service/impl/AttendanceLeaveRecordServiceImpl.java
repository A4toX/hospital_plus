package com.hospital.attendance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital.attendance.domain.AttendanceLeaveRecord;
import com.hospital.attendance.domain.bo.AttendanceLeaveRecordBo;
import com.hospital.attendance.domain.vo.AttendanceLeaveRecordVo;
import com.hospital.attendance.mapper.AttendanceLeaveRecordMapper;
import com.hospital.attendance.service.IAttendanceLeaveRecordService;
import com.hospital.flow.enums.FlowKeyEnum;
import com.hospital.flow.enums.FlowStatusEnum;
import com.hospital.flow.utils.FlowUtils;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.service.BaseServiceImpl;
import org.dromara.common.satoken.utils.LoginHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 请假记录 服务实现
 *
 * @author liguoxian
 */
@RequiredArgsConstructor
@Service
public class AttendanceLeaveRecordServiceImpl extends BaseServiceImpl<AttendanceLeaveRecordMapper, AttendanceLeaveRecord, AttendanceLeaveRecordVo, AttendanceLeaveRecordBo> implements IAttendanceLeaveRecordService {

    @Override
    public int insert(AttendanceLeaveRecordBo bo) {
        AttendanceLeaveRecord record = MapstructUtils.convert(bo, AttendanceLeaveRecord.class);
        record.setUserId(LoginHelper.getUserId());
        record.setResult(FlowStatusEnum.RUNNING.getStatus());
        int result = mapper.insert(record);
        boolean apply = FlowUtils.apply(FlowKeyEnum.LEAVE.getKey(), record.getId(), null);
        if(!apply) {
            throw new ServiceException("申请失败");
        }
        return result;
    }

    @Override
    public TableDataInfo<AttendanceLeaveRecordVo> selectPageList(AttendanceLeaveRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AttendanceLeaveRecord> lqw = buildQueryWrapper(bo);
        Page<AttendanceLeaveRecordVo> page = mapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    @Override
    public List<AttendanceLeaveRecordVo> selectList(AttendanceLeaveRecordBo bo) {
        LambdaQueryWrapper<AttendanceLeaveRecord> lqw = buildQueryWrapper(bo);
        return mapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<AttendanceLeaveRecord> buildQueryWrapper(AttendanceLeaveRecordBo bo) {
        return new LambdaQueryWrapperX<AttendanceLeaveRecord>()
                .eqIfPresent(AttendanceLeaveRecord::getLeaveConfigId, bo.getLeaveConfigId())
                .eqIfPresent(AttendanceLeaveRecord::getUserId, bo.getUserId())
                .eqIfPresent(AttendanceLeaveRecord::getLeaveStartDate, bo.getLeaveStartDate())
                .eqIfPresent(AttendanceLeaveRecord::getLeaveEndDate, bo.getLeaveEndDate())
                .eqIfPresent(AttendanceLeaveRecord::getLeaveStartTime, bo.getLeaveStartTime())
                .eqIfPresent(AttendanceLeaveRecord::getLeaveEndTime, bo.getLeaveEndTime())
                .eqIfPresent(AttendanceLeaveRecord::getLeaveLength, bo.getLeaveLength())
                .eqIfPresent(AttendanceLeaveRecord::getLeaveThings, bo.getLeaveThings())
                .eqIfPresent(AttendanceLeaveRecord::getFiles, bo.getFiles())
                .eqIfPresent(AttendanceLeaveRecord::getProcessInstanceId, bo.getProcessInstanceId())
                .eqIfPresent(AttendanceLeaveRecord::getResult, bo.getResult())
                .eqIfPresent(AttendanceLeaveRecord::getCreateDept, bo.getCreateDept())
                .eqIfPresent(AttendanceLeaveRecord::getRemark, bo.getRemark())
        ;
    }
}
