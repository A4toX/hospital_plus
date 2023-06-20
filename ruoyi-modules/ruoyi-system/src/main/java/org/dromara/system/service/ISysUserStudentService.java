package org.dromara.system.service;

import org.dromara.system.domain.SysUserStudent;
import org.dromara.system.domain.vo.SysUserStudentVo;
import org.dromara.system.domain.bo.SysUserStudentBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 学员Service接口
 *
 * @author Lion Li
 * @date 2023-06-19
 */
public interface ISysUserStudentService {

    /**
     * 查询学员
     */
    SysUserStudentVo queryById(Long userId);

    /**
     * 查询学员列表
     */
    TableDataInfo<SysUserStudentVo> queryPageList(SysUserStudentBo bo, PageQuery pageQuery);

    /**
     * 查询学员列表
     */
    List<SysUserStudentVo> queryList(SysUserStudentBo bo);

    /**
     * 新增学员
     */
    Boolean insertByBo(SysUserStudentBo bo);

    /**
     * 修改学员
     */
    Boolean updateByBo(SysUserStudentBo bo);

    /**
     * 校验并批量删除学员信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
