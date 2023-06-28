package com.hospital.attendance.mapper;

import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import com.hospital.attendance.domain.AttendanceHolidayConfig;
import com.hospital.attendance.domain.vo.AttendanceHolidayConfigVo;
import org.dromara.common.mybatis.core.mapper.LambdaQueryWrapperX;

import java.util.List;

/**
 * 节假日配置 数据层
 *
 * @author liguoxian
 */
public interface AttendanceHolidayConfigMapper extends BaseMapperPlus<AttendanceHolidayConfig, AttendanceHolidayConfigVo> {

    default List<AttendanceHolidayConfig> selectByYear(String year) {
        return selectList(new LambdaQueryWrapperX<AttendanceHolidayConfig>()
            .eq(AttendanceHolidayConfig::getBelongYear, year));
    }
}

