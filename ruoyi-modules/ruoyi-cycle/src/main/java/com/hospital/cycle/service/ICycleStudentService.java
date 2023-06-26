package com.hospital.cycle.service;

import com.hospital.cycle.domain.CycleStudent;
import com.hospital.cycle.domain.vo.CycleStudentVo;
import com.hospital.cycle.domain.bo.CycleStudentBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 学员规则关联Service接口
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
public interface ICycleStudentService {

    /**
     * 查询学员规则关联
     */
    CycleStudentVo queryById(Long cycleStudentId);

    /**
     * 查询学员规则关联列表
     */
    TableDataInfo<CycleStudentVo> queryPageList(CycleStudentBo bo, PageQuery pageQuery);

    /**
     * 查询学员规则关联列表
     */
    List<CycleStudentVo> queryList(CycleStudentBo bo);

    /**
     * 新增学员规则关联
     */
    Boolean insertByBo(List<CycleStudentBo> bos);

    /**
     * 修改学员规则关联
     */
    Boolean updateByBo(CycleStudentBo bo);

    /**
     * 校验并批量删除学员规则关联信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
