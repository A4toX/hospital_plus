package com.hospital.attendance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.dromara.common.flowable.common.enums.ProcessStatus;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.service.BaseServiceImpl;
import com.hospital.attendance.domain.AttendanceFillRecord;
import com.hospital.attendance.domain.bo.AttendanceFillRecordBo;
import com.hospital.attendance.domain.vo.AttendanceFillRecordVo;
import com.hospital.attendance.mapper.AttendanceFillRecordMapper;
import com.hospital.attendance.service.IAttendanceFillRecordService;
import org.dromara.common.satoken.utils.LoginHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 补卡记录 服务实现
 *
 * @author liguoxian
 */
@RequiredArgsConstructor
@Service
public class AttendanceFillRecordServiceImpl extends BaseServiceImpl<AttendanceFillRecordMapper, AttendanceFillRecord, AttendanceFillRecordVo, AttendanceFillRecordBo> implements IAttendanceFillRecordService {

    @Override
    public int insert(AttendanceFillRecordBo bo) {
        bo.setUserId(LoginHelper.getUserId());
        bo.setResult(ProcessStatus.RUNNING.getStatus());
        return super.insert(bo);
    }

    @Override
    public TableDataInfo<AttendanceFillRecordVo> selectPageList(AttendanceFillRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AttendanceFillRecord> lqw = buildQueryWrapper(bo);
        Page<AttendanceFillRecordVo> page = mapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    @Override
    public List<AttendanceFillRecordVo> selectList(AttendanceFillRecordBo bo) {
        LambdaQueryWrapper<AttendanceFillRecord> lqw = buildQueryWrapper(bo);
        return mapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<AttendanceFillRecord> buildQueryWrapper(AttendanceFillRecordBo bo) {
        return new LambdaQueryWrapperX<AttendanceFillRecord>()
                .eqIfPresent(AttendanceFillRecord::getFlowId, bo.getFlowId())
                .eqIfPresent(AttendanceFillRecord::getUserId, bo.getUserId())
                .eqIfPresent(AttendanceFillRecord::getFillDate, bo.getFillDate())
                .eqIfPresent(AttendanceFillRecord::getFillTime, bo.getFillTime())
                .eqIfPresent(AttendanceFillRecord::getOriginalTime, bo.getOriginalTime())
                .eqIfPresent(AttendanceFillRecord::getFillReason, bo.getFillReason())
                .eqIfPresent(AttendanceFillRecord::getFillImg, bo.getFillImg())
                .eqIfPresent(AttendanceFillRecord::getResult, bo.getResult())
                .eqIfPresent(AttendanceFillRecord::getProcessInstanceId, bo.getProcessInstanceId())
                .eqIfPresent(AttendanceFillRecord::getCreateDept, bo.getCreateDept())
                .eqIfPresent(AttendanceFillRecord::getRemark, bo.getRemark())
        ;
    }
}
