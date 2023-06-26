package com.hospital.activity.service;

import com.hospital.activity.domain.ActivityTeacher;
import com.hospital.activity.domain.vo.ActivityTeacherVo;
import com.hospital.activity.domain.bo.ActivityTeacherBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 教学活动教师Service接口
 *
 * @author Lion Li
 * @date 2023-06-21
 */
public interface IActivityTeacherService {

    /**
     * 查询教学活动教师
     */
    ActivityTeacherVo queryById(Long id);

    /**
     * 查询教学活动教师列表
     */
    TableDataInfo<ActivityTeacherVo> queryPageList(ActivityTeacherBo bo, PageQuery pageQuery);

    /**
     * 查询教学活动教师列表
     */
    List<ActivityTeacherVo> queryList(ActivityTeacherBo bo);

    /**
     * 新增教学活动教师
     */
    Boolean insertByBo(List<ActivityTeacherBo> bos);

    /**
     * 修改教学活动教师
     */
    Boolean updateByBo(ActivityTeacherBo bo);

    /**
     * 校验并批量删除教学活动教师信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
