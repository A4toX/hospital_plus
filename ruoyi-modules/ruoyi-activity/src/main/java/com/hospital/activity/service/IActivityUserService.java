package com.hospital.activity.service;

import com.hospital.activity.domain.ActivityUser;
import com.hospital.activity.domain.vo.ActivityUserVo;
import com.hospital.activity.domain.bo.ActivityUserBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 教学活动参与用户Service接口
 *
 * @author yyj
 * @date 2023-06-21
 */
public interface IActivityUserService {

    /**
     * 查询教学活动参与用户
     */
    ActivityUserVo queryById(Long id);

    /**
     * 查询教学活动参与用户列表
     */
    TableDataInfo<ActivityUserVo> queryPageList(ActivityUserBo bo, PageQuery pageQuery);

    /**
     * 查询教学活动参与用户列表
     */
    List<ActivityUserVo> queryList(ActivityUserBo bo);

    /**
     * 新增教学活动参与用户
     */
    Boolean insertByBo(List<ActivityUserBo> bos);

    /**
     * 修改教学活动参与用户
     */
    Boolean updateByBo(ActivityUserBo bo);

    /**
     * 校验并批量删除教学活动参与用户信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    void usersign(Long acvitityId);
}
