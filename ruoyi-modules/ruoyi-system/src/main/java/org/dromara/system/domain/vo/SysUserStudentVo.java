package org.dromara.system.domain.vo;

import org.dromara.common.sensitive.annotation.Sensitive;
import org.dromara.common.sensitive.core.SensitiveStrategy;
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
import java.util.List;


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
    private Long userId;


    /**
     * 租户ID
     */
    private String tenantId;


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
    private Long baseId;

    @ExcelProperty(value = "专业代码")
    private String baseCode;


    /**
     * 用户账号
     */
    @ExcelProperty(value = "用户账号")
    private String userName;


    /**
     * 用户姓名
     */
    @ExcelProperty(value = "姓名")
    private String realName;


    /**
     * 用户邮箱
     */
    @ExcelProperty(value = "邮箱")
    @Sensitive(strategy = SensitiveStrategy.EMAIL)
    private String email;

    /**
     * 手机号码
     */

    @Sensitive(strategy = SensitiveStrategy.PHONE)
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
    @ExcelProperty(value = "证件号码", converter = ExcelDictConvert.class)
    @Sensitive(strategy = SensitiveStrategy.ID_CARD)
    private String idcardNumb;


    /**
     * 用户身份(1员工2学员)
     */
    private String identity;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @ExcelProperty(value = "用户性别", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_sex")
    private String sex;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;


    /**
     * 创建时间
     */
    private Date createTime;






}
