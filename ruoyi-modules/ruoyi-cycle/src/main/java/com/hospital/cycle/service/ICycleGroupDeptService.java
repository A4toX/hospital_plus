package com.hospital.cycle.service;

import com.hospital.cycle.domain.CycleGroupDept;
import com.hospital.cycle.domain.vo.CycleGroupDeptVo;
import com.hospital.cycle.domain.bo.CycleGroupDeptBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 轮转规则组关联科室Service接口
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
public interface ICycleGroupDeptService {

    /**
     * 查询轮转规则组关联科室
     */
    CycleGroupDeptVo queryById(Long groupDeptId);

    /**
     * 查询轮转规则组关联科室列表
     */
    TableDataInfo<CycleGroupDeptVo> queryPageList(CycleGroupDeptBo bo, PageQuery pageQuery);

    /**
     * 查询轮转规则组关联科室列表
     */
    List<CycleGroupDeptVo> queryList(CycleGroupDeptBo bo);

    /**
     * 新增轮转规则组关联科室
     */
    Boolean insertByBo(List<CycleGroupDeptBo> bos);

    /**
     * 修改轮转规则组关联科室
     */
    Boolean updateByBo(CycleGroupDeptBo bo);

    /**
     * 校验并批量删除轮转规则组关联科室信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
