package org.dromara.system.domain.vo;

import org.dromara.system.domain.SysBase;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 专业基地视图对象 sys_base
 *
 * @author yaoyingjie
 * @date 2023-06-15
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysBase.class)
public class SysBaseVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 专业名称
     */
    @ExcelProperty(value = "专业名称")
    private String baseName;

    /**
     * 专业代码
     */
    @ExcelProperty(value = "专业代码")
    private String baseCode;

    /**
     * 专业负责人联系电话
     */
    @ExcelProperty(value = "专业负责人联系电话")
    private String baseLeaderPhone;

    /**
     * 专业负责人名称
     */
    @ExcelProperty(value = "专业负责人名称")
    private String baseLeaderName;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
