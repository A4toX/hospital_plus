package com.hospital.cycle.service;

import com.hospital.cycle.domain.CycleCalcRecord;
import com.hospital.cycle.domain.vo.CycleCalcRecordVo;
import com.hospital.cycle.domain.bo.CycleCalcRecordBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 轮转计算过程记录Service接口
 *
 * @author yaoyingjie
 * @date 2023-07-02
 */
public interface ICycleCalcRecordService {

    /**
     * 查询轮转计算过程记录
     */
    CycleCalcRecordVo queryById(Long calcRecordId);

    /**
     * 查询轮转计算过程记录列表
     */
    TableDataInfo<CycleCalcRecordVo> queryPageList(CycleCalcRecordBo bo, PageQuery pageQuery);

    /**
     * 查询轮转计算过程记录列表
     */
    List<CycleCalcRecordVo> queryList(CycleCalcRecordBo bo);

    /**
     * 新增轮转计算过程记录
     */
    Boolean insertByBo(CycleCalcRecordBo bo);

    /**
     * 修改轮转计算过程记录
     */
    Boolean updateByBo(CycleCalcRecordBo bo);

    /**
     * 校验并批量删除轮转计算过程记录信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
