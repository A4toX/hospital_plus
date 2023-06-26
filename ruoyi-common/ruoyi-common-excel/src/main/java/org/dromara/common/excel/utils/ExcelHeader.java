package org.dromara.common.excel.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * excel 表头
 * @author liguoxian
 */
@Data
@NoArgsConstructor
public class ExcelHeader {

    /**
     * 表头名称
     */
    private String headerName;

    /**
     * 子表头集合
     */
    private List<ExcelHeader> subHeaders;

    public ExcelHeader(String headerName) {
        this.headerName = headerName;
    }
}
