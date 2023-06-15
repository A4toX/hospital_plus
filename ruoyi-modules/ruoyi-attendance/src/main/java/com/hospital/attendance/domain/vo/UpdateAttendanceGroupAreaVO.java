package com.hospital.attendance.domain.vo;

import com.demo.hospital.attendance.model.AttendanceGroupArea;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@Data
@ApiModel("管理考勤组下的地点")
public class UpdateAttendanceGroupAreaVO {
    @ApiModelProperty("新增地点列表")
    private List<AttendanceGroupArea> addAreaList;
    @ApiModelProperty("删除地点列表")
    private List<AttendanceGroupArea> delAreaList;

}
