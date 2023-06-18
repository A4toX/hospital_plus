package com.hospital.attendance.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hospital.attendance.domain.AttendanceClasses;
import com.hospital.attendance.domain.vo.AttendanceClassesVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 班次设置表(AttendanceClasses) DAO
 *
 * @author makejava
 * @since 2023-05-21 17:37:13
 */
public interface AttendanceClassesMapper extends BaseMapperPlus<AttendanceClasses, AttendanceClassesVo> {

   default List<AttendanceClassesVo> listByHosId(Long hosId){
        return selectVoList(new LambdaQueryWrapper<AttendanceClasses>().eq(AttendanceClasses::getHosId, hosId));
   }
}

