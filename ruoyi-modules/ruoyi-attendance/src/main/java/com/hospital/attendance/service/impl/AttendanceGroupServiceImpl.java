package com.hospital.attendance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital.attendance.domain.AttendanceGroup;
import com.hospital.attendance.domain.bo.AttendanceGroupBo;
import com.hospital.attendance.domain.vo.AttendanceGroupAreaVo;
import com.hospital.attendance.domain.vo.AttendanceGroupClassesSimpleRespVo;
import com.hospital.attendance.domain.vo.AttendanceGroupRespVo;
import com.hospital.attendance.domain.vo.AttendanceGroupVo;
import com.hospital.attendance.enums.AttendanceMethodEnum;
import com.hospital.attendance.enums.AttendanceTypeEnum;
import com.hospital.attendance.mapper.AttendanceClassesMapper;
import com.hospital.attendance.mapper.AttendanceGroupAreaMapper;
import com.hospital.attendance.mapper.AttendanceGroupClassesMapper;
import com.hospital.attendance.mapper.AttendanceGroupMapper;
import com.hospital.attendance.service.IAttendanceGroupService;
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
 * 考勤组信息表 Service
 *
 * @author yaoyingjie
 */
@Service
@RequiredArgsConstructor
public class AttendanceGroupServiceImpl extends BaseServiceImpl<AttendanceGroupMapper, AttendanceGroup, AttendanceGroupVo, AttendanceGroupBo> implements IAttendanceGroupService {

    private final AttendanceClassesMapper attendanceClassesMapper;
    private final AttendanceGroupClassesMapper attendanceGroupClassesMapper;
    private final AttendanceGroupAreaMapper attendanceGroupAreaMapper;

    @Override
    public AttendanceGroupRespVo selectAllInfoById(Long id) {
        AttendanceGroupRespVo vo = new AttendanceGroupRespVo();
        //根据考勤组id查询考勤组信息
        AttendanceGroupVo attendanceGroup = mapper.selectVoById(id);
        if (attendanceGroup == null) {
            return vo;
        }
        vo.setAttendanceGroup(attendanceGroup);
        //如果考勤组为固定班次
        if (attendanceGroup.getGroupType() == AttendanceTypeEnum.fixed_time.getType()) {
            //根据考勤组id查询考勤班次信息
            List<AttendanceGroupClassesSimpleRespVo> classVOList = attendanceGroupClassesMapper.selectByGroupIdSimple(id);
            vo.setGroupClassesSimpleRespVOS(classVOList);
        }
        //定位考勤， 封装考勤地点信息
        if (attendanceGroup.getGroupMethod() == AttendanceMethodEnum.position.getType()) {
            List<AttendanceGroupAreaVo> list = attendanceGroupAreaMapper.selectByGroupId(id);
            if (!list.isEmpty()) {
                vo.setAttendanceGroupAreaList(list);
            }
        }
        return vo;
    }

    @Override
    public TableDataInfo<AttendanceGroupVo> selectPageList(AttendanceGroupBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AttendanceGroup> lqw = buildQueryWrapper(bo);
        Page<AttendanceGroupVo> page = mapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    @Override
    public int insert(AttendanceGroupBo bo) {
        validateName(bo.getGroupName(), null);
        return super.insert(bo);
    }

    @Override
    public int update(AttendanceGroupBo bo) {
        validateName(bo.getGroupName(), bo.getId());
        int result = super.update(bo);
        AttendanceUtils.removeGroupCache(bo.getId());
        return result;
    }

    @Override
    public int deleteById(Long id) {
        int result = super.deleteById(id);
        //清除缓存
        AttendanceUtils.removeGroupCache(id);
        AttendanceUtils.removeGroupClassesCache(id);
        AttendanceUtils.removeGroupAreasCache(id);
        AttendanceUtils.removeGroupUserCache();
        return result;
    }

    private void validateName(String groupName, Long id) {
        LambdaQueryWrapper<AttendanceGroup> lqw = new LambdaQueryWrapperX<>();
        lqw.eq(AttendanceGroup::getGroupName, groupName);
        if (id != null) {
            lqw.ne(AttendanceGroup::getId, id);
        }
        List<AttendanceGroup> list = mapper.selectList(lqw);
        if (list.size() > 0) {
            throw new ServiceException("同医院下考勤组名称不能重复");
        }
    }

    private LambdaQueryWrapper<AttendanceGroup> buildQueryWrapper(AttendanceGroupBo bo) {
        return new LambdaQueryWrapperX<AttendanceGroup>()
            .eqIfPresent(AttendanceGroup::getGroupType, bo.getGroupType())
            .eqIfPresent(AttendanceGroup::getGroupMethod, bo.getGroupMethod())
            .eqIfPresent(AttendanceGroup::getAreaOutside, bo.getAreaOutside())
            .eqIfPresent(AttendanceGroup::getGroupCode, bo.getGroupCode())
            .eqIfPresent(AttendanceGroup::getHolidayLeave, bo.getHolidayLeave())
            .likeIfPresent(AttendanceGroup::getGroupName, bo.getGroupName());
    }
}
