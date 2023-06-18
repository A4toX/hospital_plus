package com.hospital.attendance.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital.attendance.domain.AttendanceManagementUser;
import com.hospital.attendance.domain.bo.AttendanceManagementUserBo;
import com.hospital.attendance.domain.vo.AttendanceManagementUserVo;
import com.hospital.attendance.domain.vo.attendUser.AddAttendanceUserVo;
import com.hospital.attendance.domain.vo.attendUser.AttendanceMUserReqVO;
import com.hospital.attendance.domain.vo.attendUser.AttendanceMUserRespVo;
import com.hospital.attendance.mapper.AttendanceManagementUserMapper;
import com.hospital.attendance.service.IAttendanceManagementUserService;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 考勤组负责人(AttendanceManagementUser) Service
 *
 * @author makejava
 * @since 2023-05-21 19:29:23
 */
@Service
@RequiredArgsConstructor
public class AttendanceManagementUserServiceImpl extends BaseServiceImpl<AttendanceManagementUserMapper, AttendanceManagementUser, AttendanceManagementUserVo, AttendanceManagementUserBo> implements IAttendanceManagementUserService {

    @Override
    public TableDataInfo<AttendanceManagementUserVo> selectPageList(AttendanceManagementUserBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AttendanceManagementUser> lqw = buildQueryWrapper(bo);
        Page<AttendanceManagementUserVo> page = mapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    @Transactional
    @Override
    public void addUser(AddAttendanceUserVo entity) {
        if (CollUtil.isEmpty(entity.getAddUserIds())) {
            throw new ServiceException("请选择人员");
        }
        List<AttendanceManagementUser> users = mapper.selectList(new LambdaQueryWrapper<AttendanceManagementUser>().eq(AttendanceManagementUser::getGroupId, entity.getGroupId()));
        Set<Long> nowUserIds = Set.of(entity.getAddUserIds().toArray(Long[]::new));
        Set<Long> oldUserIds = users.stream().map(AttendanceManagementUser::getUserId).collect(Collectors.toSet());

        //使用steam比对distinctUserIds和userIdList，获取需要添加的人员
        Set<Long> addUserIds = nowUserIds.stream().filter(userId -> !oldUserIds.contains(userId)).collect(Collectors.toSet());
        //使用steam比对distinctUserIds和userIdList，获取需要删除的人员
        Set<Long> delUserIds = oldUserIds.stream().filter(userId -> !nowUserIds.contains(userId)).collect(Collectors.toSet());
        //删除
        if (delUserIds != null && delUserIds.size() > 0) {
            mapper.delete(new LambdaQueryWrapper<AttendanceManagementUser>()
                .eq(AttendanceManagementUser::getGroupId, entity.getGroupId())
                .in(AttendanceManagementUser::getUserId, delUserIds));
        }
        //添加
        if (addUserIds != null && addUserIds.size() > 0) {
            mapper.insertBatch(addUserIds.stream().map(userId -> {
                AttendanceManagementUser user = new AttendanceManagementUser();
                user.setGroupId(entity.getGroupId());
                user.setUserId(userId);
                return user;
            }).collect(Collectors.toList()));
        }
    }

    @Override
    public List<AttendanceMUserRespVo> listByGroupId(AttendanceMUserReqVO reqVO) {
        return mapper.listByGroupId(reqVO);
    }

    @Override
    public List<AttendanceMUserRespVo> listAllStaffByHosId(AttendanceMUserReqVO reqVO) {
        return mapper.listAllStaffByHosId(reqVO);
    }

    private LambdaQueryWrapper<AttendanceManagementUser> buildQueryWrapper(AttendanceManagementUserBo bo) {
        return new LambdaQueryWrapperX<AttendanceManagementUser>()
            .like(AttendanceManagementUser::getUserId, bo.getUserId());
    }
}
