package org.dromara.common.core.system.api;

import org.dromara.common.core.system.domain.Dept;

public interface DeptApi {
    Dept getDeptByDeptName(String deptName);
}
