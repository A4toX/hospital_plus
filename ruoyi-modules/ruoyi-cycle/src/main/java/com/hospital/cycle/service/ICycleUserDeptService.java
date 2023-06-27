package com.hospital.cycle.service;

import com.hospital.cycle.domain.CycleUserDept;
import com.hospital.cycle.domain.vo.CycleUserDeptVo;
import com.hospital.cycle.domain.bo.CycleUserDeptBo;
import org.dromara.common.core.validate.ValidList;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 轮转学生选课记录Service接口
 *
 * @author yaoyingjie
 * @date 2023-06-27
 */
public interface ICycleUserDeptService {

    /**
     * 查询轮转学生选课记录
     */
    CycleUserDeptVo queryById(Long id);

    /**
     * 查询轮转学生选课记录列表
     */
    TableDataInfo<CycleUserDeptVo> queryPageList(CycleUserDeptBo bo, PageQuery pageQuery);

    /**
     * 查询轮转学生选课记录列表
     */
    List<CycleUserDeptVo> queryList(CycleUserDeptBo bo);

    /**
     * 新增轮转学生选课记录
     */
    Boolean insertByBo(List<CycleUserDeptBo> bos);

    /**
     * 修改轮转学生选课记录
     */
    Boolean updateByBo(CycleUserDeptBo bo);

    /**
     * 校验并批量删除轮转学生选课记录信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
