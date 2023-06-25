package com.hospital.cycle.service;

import com.hospital.cycle.domain.CycleGroup;
import com.hospital.cycle.domain.vo.CycleGroupVo;
import com.hospital.cycle.domain.bo.CycleGroupBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 轮转规则组Service接口
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
public interface ICycleGroupService {

    /**
     * 查询轮转规则组
     */
    CycleGroupVo queryById(Long groupId);

    /**
     * 查询轮转规则组列表
     */
    TableDataInfo<CycleGroupVo> queryPageList(CycleGroupBo bo, PageQuery pageQuery);

    /**
     * 查询轮转规则组列表
     */
    List<CycleGroupVo> queryList(CycleGroupBo bo);

    /**
     * 新增轮转规则组
     */
    Boolean insertByBo(CycleGroupBo bo);

    /**
     * 修改轮转规则组
     */
    Boolean updateByBo(CycleGroupBo bo);

    /**
     * 校验并批量删除轮转规则组信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
