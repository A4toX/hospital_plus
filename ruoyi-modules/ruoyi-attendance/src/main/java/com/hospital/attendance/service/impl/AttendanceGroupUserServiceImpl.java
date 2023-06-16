package com.hospital.attendance.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital.attendance.domain.AttendanceGroupUser;
import com.hospital.attendance.domain.bo.AttendanceGroupUserBo;
import com.hospital.attendance.domain.vo.AttendanceGroupUserVo;
import com.hospital.attendance.domain.vo.attendUser.AddAttendanceUserVo;
import com.hospital.attendance.domain.vo.attendUser.AttendanceUserReqVO;
import com.hospital.attendance.domain.vo.attendUser.AttendanceUserRespVO;
import com.hospital.attendance.mapper.AttendanceGroupUserMapper;
import com.hospital.attendance.service.IAttendanceGroupUserService;
import com.hospital.attendance.utils.AttendanceUtils;
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
 * 考勤参与人员 Service
 *
 * @author yaoyingjie
 */
@Service
@RequiredArgsConstructor
public class AttendanceGroupUserServiceImpl extends BaseServiceImpl<AttendanceGroupUserMapper, AttendanceGroupUser, AttendanceGroupUserVo, AttendanceGroupUserBo> implements IAttendanceGroupUserService {

    @Override
    public TableDataInfo<AttendanceGroupUserVo> selectPageList(AttendanceGroupUserBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AttendanceGroupUser> lqw = buildQueryWrapper(bo);
        Page<AttendanceGroupUserVo> page = mapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    @Override
    @Transactional
    public void addUser(AddAttendanceUserVo entity) {
        if (CollUtil.isEmpty(entity.getAddUserIds())) {
            throw new ServiceException("请选择人员");
        }
        List<AttendanceGroupUser> users = mapper.selectList(new LambdaQueryWrapper<AttendanceGroupUser>().eq(AttendanceGroupUser::getGroupId, entity.getGroupId()));
        Set<Long> nowUserIds = Set.of(entity.getAddUserIds().toArray(Long[]::new));
        Set<Long> oldUserIds = users.stream().map(AttendanceGroupUser::getUserId).collect(Collectors.toSet());
        //使用steam比对distinctUserIds和userIdList，获取需要添加的人员
        Set<Long> addUserIds = nowUserIds.stream().filter(item -> !oldUserIds.contains(item)).collect(Collectors.toSet());
        //使用steam比对distinctUserIds和userIdList，获取需要删除的人员
        Set<Long> delUserIds = oldUserIds.stream().filter(item -> !nowUserIds.contains(item)).collect(Collectors.toSet());
        //删除
        if (delUserIds != null && delUserIds.size() > 0) {
            mapper.delete(new LambdaQueryWrapper<AttendanceGroupUser>()
                .eq(AttendanceGroupUser::getGroupId, entity.getGroupId())
                .in(AttendanceGroupUser::getUserId, delUserIds));
        }
        //新增
        if (addUserIds != null && addUserIds.size() > 0) {
            mapper.insertBatch(addUserIds.stream().map(userId -> {
                AttendanceGroupUser user = new AttendanceGroupUser();
                user.setGroupId(entity.getGroupId());
                user.setUserId(userId);
                return user;
            }).collect(Collectors.toList()));
        }
        //清除用户缓存
        AttendanceUtils.removeGroupUserCache();
    }

    @Override
    public List<AttendanceUserRespVO> listByGroupId(AttendanceUserReqVO reqVO) {
        return mapper.listByGroupId(reqVO);
    }

    private LambdaQueryWrapper<AttendanceGroupUser> buildQueryWrapper(AttendanceGroupUserBo bo) {
        return new LambdaQueryWrapperX<AttendanceGroupUser>()
            .like(AttendanceGroupUser::getUserId, bo.getUserId());
    }
}
