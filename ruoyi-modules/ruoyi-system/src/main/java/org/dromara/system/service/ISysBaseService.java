package org.dromara.system.service;

import org.dromara.system.domain.SysBase;
import org.dromara.system.domain.vo.SysBaseVo;
import org.dromara.system.domain.bo.SysBaseBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 专业Service接口
 *
 * @author yaoyingjie
 * @date 2023-06-18
 */
public interface ISysBaseService {

    /**
     * 查询专业
     */
    SysBaseVo queryById(Long baseId);

    /**
     * 查询专业列表
     */
    TableDataInfo<SysBaseVo> queryPageList(SysBaseBo bo, PageQuery pageQuery);

    /**
     * 查询专业列表
     */
    List<SysBaseVo> queryList(SysBaseBo bo);

    /**
     * 新增专业
     */
    Boolean insertByBo(SysBaseBo bo);

    /**
     * 修改专业
     */
    Boolean updateByBo(SysBaseBo bo);

    /**
     * 校验并批量删除专业信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
