package com.hospital.attendance.utils.dto;

import lombok.Data;

import java.util.List;

/**
 * @author liguoxian
 */
@Data
public class Holiday {

    private String begin;

    private String end;

    private String holiday;

    private String holiday_remark;

    private List<String> inverse_days;
}
