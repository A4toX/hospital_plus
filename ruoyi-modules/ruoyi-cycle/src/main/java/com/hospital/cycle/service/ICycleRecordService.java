package com.hospital.cycle.service;

import com.hospital.cycle.domain.CycleRecord;
import com.hospital.cycle.domain.vo.CycleRecordVo;
import com.hospital.cycle.domain.bo.CycleRecordBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 用户轮转记录Service接口
 *
 * @author yaoyingjie
 * @date 2023-06-25
 */
public interface ICycleRecordService {

    /**
     * 查询用户轮转记录
     */
    CycleRecordVo queryById(Long id);

    /**
     * 查询用户轮转记录列表
     */
    TableDataInfo<CycleRecordVo> queryPageList(CycleRecordBo bo, PageQuery pageQuery);

    /**
     * 查询用户轮转记录列表
     */
    List<CycleRecordVo> queryList(CycleRecordBo bo);

    /**
     * 新增用户轮转记录
     */
    Boolean insertByBo(CycleRecordBo bo);

    /**
     * 修改用户轮转记录
     */
    Boolean updateByBo(CycleRecordBo bo);

    /**
     * 校验并批量删除用户轮转记录信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
