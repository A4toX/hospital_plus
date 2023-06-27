package org.dromara.common.core.utils.system;

import org.dromara.common.core.system.api.DeptApi;
import org.dromara.common.core.system.api.UserApi;
import org.dromara.common.core.system.domain.Dept;
import org.dromara.common.core.utils.SpringUtils;

public class DeptUtils {
    private static DeptApi deptApi = SpringUtils.getBean(DeptApi.class);
    public static Dept getDeptByDeptName(String deptName) {
        return deptApi.getDeptByDeptName(deptName);
    }

}
