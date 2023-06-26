package org.dromara.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.common.sensitive.annotation.Sensitive;
import org.dromara.common.sensitive.core.SensitiveStrategy;
import org.dromara.system.domain.SysUser;
import org.dromara.system.domain.SysUserStudent;

import java.io.Serial;
import java.io.Serializable;

@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysUser.class)
public class SysUserSimpleVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
        /**
        * 用户ID
        */
        @ExcelProperty(value = "用户id")
        private Long userId;

        /**
        * 用户姓名
        */
        @ExcelProperty(value = "用户姓名")
        private String realName;

        /**
         * 用户性别（0男 1女 2未知）
         */
        private String sex;

        /**
         * 手机号码
         */
        @Sensitive(strategy = SensitiveStrategy.PHONE)
        private String phonenumber;

        /**
         * 身份证件号码
         */
        @Sensitive(strategy = SensitiveStrategy.ID_CARD)
        private String idcardNumb;

}
