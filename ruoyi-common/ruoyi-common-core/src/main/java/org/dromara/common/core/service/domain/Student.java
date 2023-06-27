package org.dromara.common.core.service.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Student {
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
    private String personType;

    /**
     * 学员类型
     */
    private String studentType;

    /**
     * 招录年份
     */
    private String residentYear;

    /**
     * 培训专业id
     */
    private Long baseId;

    /**
     * 专业代码
     */
    private String baseCode;


    /**
     * 用户账号
     */
    private String userName;


    /**
     * 用户姓名
     */
    private String realName;


    /**
     * 用户邮箱
     */

    private String email;

    /**
     * 手机号码
     */


    private String phonenumber;


    /**
     * 身份证件类型
     */

    private String idcardType;

    /**
     * 身份证件号码
     */

    private String idcardNumb;


    /**
     * 用户身份(1员工2学员)
     */
    private String identity;

    /**
     * 用户性别（0男 1女 2未知）
     */

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
