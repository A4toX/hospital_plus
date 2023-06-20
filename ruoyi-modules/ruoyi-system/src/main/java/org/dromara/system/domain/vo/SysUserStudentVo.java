package org.dromara.system.domain.vo;

import org.dromara.system.domain.SysUserStudent;
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
 * 学员视图对象 sys_user_student
 *
 * @author Lion Li
 * @date 2023-06-19
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysUserStudent.class)
public class SysUserStudentVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 人员类型
     */
    @ExcelProperty(value = "人员类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_type")
    private String personType;

    /**
     * 学员类型
     */
    @ExcelProperty(value = "学员类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_student_type")
    private String studentType;

    /**
     * 招录年份
     */
    @ExcelProperty(value = "招录年份")
    private String residentYear;

    /**
     * 培训专业id
     */
    @ExcelProperty(value = "培训专业id")
    private Long baseId;


}
