package com.hospital.attendance.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hospital.attendance.domain.AttendanceGroupArea;
import com.hospital.attendance.domain.vo.AttendanceGroupAreaVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 考勤地点表 DAO
 *
 * @author yaoyingjie
 */
public interface AttendanceGroupAreaMapper extends BaseMapperPlus<AttendanceGroupArea, AttendanceGroupAreaVo> {

    default List<AttendanceGroupAreaVo> selectByGroupId(Long groupId) {
        return selectVoList(new LambdaQueryWrapper<AttendanceGroupArea>().eq(AttendanceGroupArea::getGroupId, groupId));
    }

    default List<AttendanceGroupAreaVo> listByHosId(Long hosId){
        return selectVoList(new LambdaQueryWrapper<AttendanceGroupArea>().eq(AttendanceGroupArea::getHosId, hosId));
    }

}

