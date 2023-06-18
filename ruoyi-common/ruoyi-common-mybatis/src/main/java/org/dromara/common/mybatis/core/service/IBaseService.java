package org.dromara.common.mybatis.core.service;

import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * service 基础接口
 * @author liguoxian
 */
public interface IBaseService<V, B> {

    /**
     * 根据条件分页查询数据
     * @param bo 查询参数
     * @param pageQuery 分页参数
     * @return 分页数据
     */
    default TableDataInfo<V> selectPageList(B bo, PageQuery pageQuery) {
        return TableDataInfo.build();
    }

    /**
     * 根据条件查询数据
     *
     * @param bo 查询参数
     * @return 数据
     */
    default List<V> selectList(B bo) {
        return new ArrayList<>();
    };

    /**
     * 根据 ID查询信息
     *
     * @param id 主键
     * @return 数据
     */
    V selectById(Long id);

    /**
     * 批量删除数据信息
     *
     * @param ids 需要删除的数据ID集合
     */
    int deleteByIds(Long[] ids);

    /**
     * 根据主键删除数据
     * @param id 需要删除的数据ID
     */
    int deleteById(Long id);

    /**
     * 新增数据信息
     *
     * @param bo 数据信息
     * @return 新增结果
     */
    default int insert(B bo) {
        return 0;
    }

    /**
     * 修改数据信息
     *
     * @param bo 数据信息
     * @return 修改结果
     */
    default int update(B bo) {
        return 0;
    }
}
