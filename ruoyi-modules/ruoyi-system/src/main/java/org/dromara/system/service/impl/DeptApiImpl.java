package org.dromara.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.system.api.DeptApi;
import org.dromara.common.core.system.domain.Dept;
import org.dromara.system.domain.SysDept;
import org.dromara.system.domain.vo.SysDeptVo;
import org.dromara.system.mapper.SysDeptMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeptApiImpl implements DeptApi{
    private final SysDeptMapper deptMapper;
    @Override
    public Dept getDeptByDeptName(String  deptName) {
        SysDeptVo sysDeptVo = deptMapper.selectVoOne(new LambdaQueryWrapper<SysDept>().eq(SysDept::getDeptName, deptName));
        if (sysDeptVo == null) {
            return null;
        }
        Dept dept = new Dept();
        dept.setDeptId(sysDeptVo.getDeptId());
        dept.setDeptName(sysDeptVo.getDeptName());
        return dept;
    }

}
