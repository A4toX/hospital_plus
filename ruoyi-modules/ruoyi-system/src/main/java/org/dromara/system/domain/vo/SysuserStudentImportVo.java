package org.dromara.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class SysuserStudentImportVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


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


    @ExcelProperty(value = "专业代码")
    private String baseCode;


    /**
     * 用户姓名
     */
    @ExcelProperty(value = "姓名")
    private String realName;


    /**
     * 用户邮箱
     */
    @ExcelProperty(value = "邮箱")
    private String email;

    /**
     * 手机号码
     */

    @ExcelProperty(value = "手机号码")
    private String phonenumber;


    /**
     * 身份证件类型
     */
    @ExcelProperty(value = "证件类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_cert")
    private String idcardType;

    /**
     * 身份证件号码
     */
    @ExcelProperty(value = "证件号码")
    private String idcardNumb;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @ExcelProperty(value = "用户性别", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_sex")
    private String sex;

}
