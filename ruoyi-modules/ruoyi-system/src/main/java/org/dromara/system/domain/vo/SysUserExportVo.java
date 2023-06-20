package org.dromara.system.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.ReverseAutoMapping;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.common.sensitive.annotation.Sensitive;
import org.dromara.common.sensitive.core.SensitiveStrategy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户对象导出VO
 *
 * @author Lion Li
 */

@Data
@NoArgsConstructor
@AutoMapper(target = SysUserVo.class, convertGenerate = false)
public class SysUserExportVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户序号")
    private Long userId;

    /**
     * 用户账号
     */
    @ExcelProperty(value = "登录名称")
    private String userName;

    /**
     * 用户姓名
     */
    @ExcelProperty(value = "用户名称")
    private String realName;

    /**
     * 用户邮箱
     */
    @ExcelProperty(value = "用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @ExcelProperty(value = "手机号码")
    private String phonenumber;

    /**
     * 用户性别
     */
    @ExcelProperty(value = "用户性别", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_sex")
    private String sex;

    /**
     * 帐号状态（0正常 1停用）
     */
    @ExcelProperty(value = "帐号状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_normal_disable")
    private String status;

    /**
     * 身份证件类型
     */
    @ExcelProperty(value = "身份证件类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_cert")
    private String idcardType;

    /**
     * 身份证件号码
     */
    @ExcelProperty(value = "证件号码")
    private String idcardNumb;

    /**
     * 用户身份(1员工2学员)
     */
    @ExcelProperty(value = "用户身份", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_identity")
    private String identity;

    /**
     * 最后登录IP
     */
    @ExcelProperty(value = "最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @ExcelProperty(value = "最后登录时间")
    private Date loginDate;

    /**
     * 部门名称
     */
    @ReverseAutoMapping(target = "deptName", source = "dept.deptName")
    @ExcelProperty(value = "部门名称")
    private String deptName;

    /**
     * 负责人
     */
    @ReverseAutoMapping(target = "leader", source = "dept.leader")
    @ExcelProperty(value = "部门负责人")
    private String leader;

}
