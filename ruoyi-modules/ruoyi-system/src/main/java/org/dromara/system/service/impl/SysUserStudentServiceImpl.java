package org.dromara.system.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dromara.common.core.constant.UserConstants;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.service.StudentService;
import org.dromara.common.core.service.domain.Student;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.tenant.helper.TenantHelper;
import org.dromara.system.domain.SysUser;
import org.dromara.system.domain.bo.SysUserBo;
import org.dromara.system.mapper.SysUserMapper;
import org.dromara.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import org.dromara.system.domain.bo.SysUserStudentBo;
import org.dromara.system.domain.vo.SysUserStudentVo;
import org.dromara.system.domain.SysUserStudent;
import org.dromara.system.mapper.SysUserStudentMapper;
import org.dromara.system.service.ISysUserStudentService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.Set;

/**
 * 学员Service业务层处理
 *
 * @author Lion Li
 * @date 2023-06-19
 */
@RequiredArgsConstructor
@Service
public class SysUserStudentServiceImpl implements ISysUserStudentService, StudentService {

    private final SysUserStudentMapper baseMapper;


    /**
     * 查询学员
     */
    @Override
    public SysUserStudentVo queryById(Long userId){
        return baseMapper.selectVoById(userId);
    }

    /**
     * 查询学员列表
     */
    @Override
    public TableDataInfo<SysUserStudentVo> queryPageList(SysUserStudentBo bo, PageQuery pageQuery) {
        Page<SysUserStudentVo> result = baseMapper.pageStudentList(pageQuery.build(), this.buildQueryWrapper(bo));
        return TableDataInfo.build(result);
    }

    /**
     * 查询学员列表
     */
    @Override
    public List<SysUserStudentVo> queryList(SysUserStudentBo bo) {
        return baseMapper.selectStudentList(this.buildQueryWrapper(bo));
    }


    private Wrapper<SysUserStudent> buildQueryWrapper(SysUserStudentBo bo) {
        Map<String, Object> params = bo.getParams();
        QueryWrapper<SysUserStudent> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(bo.getPersonType()),"us.person_type", bo.getPersonType())
            .in(bo.getUserIds()!=null,"us.user_id", bo.getUserIds())
            .eq(bo.getUserId()!=null,"us.user_id", bo.getUserId())
            .eq(StringUtils.isNotBlank(bo.getStudentType()),"us.student_type", bo.getStudentType())
            .eq(StringUtils.isNotBlank(bo.getResidentYear()),"us.resident_year", bo.getResidentYear())
            .like(params.get("realName") != null, "u.real_name", params.get("realName"))
            .like(params.get("phonenumber") != null, "u.phonenumber", params.get("phonenumber"))
            .like("u.del_flag", UserConstants.USER_NORMAL);
        return wrapper;
    }

    /**
     * 新增学员
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(SysUserStudentBo bo) {
        SysUserStudent add = MapstructUtils.convert(bo, SysUserStudent.class);
        validEntityBeforeSave(add);
        return baseMapper.insert(add) > 0;
    }

    /**
     * 修改学员
     */
    @Override
    public Boolean updateByBo(SysUserStudentBo bo) {
        SysUserStudent update = MapstructUtils.convert(bo, SysUserStudent.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysUserStudent entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除学员
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public Long selectStudentBaseIdByUserId(Long userId) {
        return baseMapper.selectById(userId).getBaseId();
    }

    @Override
    public List<Student> selectStudentByUserIds(Set<Long> userIds) {
        SysUserStudentBo bo = new SysUserStudentBo();
        bo.setUserIds(userIds);
        List<SysUserStudentVo> voList = baseMapper.selectStudentList(this.buildQueryWrapper(bo));
        if (voList == null || voList.isEmpty()) {
            return null;
        }
        return BeanUtil.copyToList(voList, Student.class);
    }


}
