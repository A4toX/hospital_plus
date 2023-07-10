package com.hospital.cycle.service.impl;

import com.hospital.cycle.domain.CycleGroup;
import com.hospital.cycle.mapper.CycleGroupMapper;
import com.hospital.cycle.utils.CycleValidUtils;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.core.validate.ValidList;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hospital.cycle.domain.bo.CycleUserDeptBo;
import com.hospital.cycle.domain.vo.CycleUserDeptVo;
import com.hospital.cycle.domain.CycleUserDept;
import com.hospital.cycle.mapper.CycleUserDeptMapper;
import com.hospital.cycle.service.ICycleUserDeptService;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hospital.cycle.constant.CycleConstant.*;

/**
 * 轮转学生选课记录Service业务层处理
 *
 * @author yaoyingjie
 * @date 2023-06-27
 */
@RequiredArgsConstructor
@Service
public class CycleUserDeptServiceImpl implements ICycleUserDeptService {

    private final CycleUserDeptMapper baseMapper;
    private final CycleGroupMapper groupMapper;

    /**
     * 查询轮转学生选课记录
     */
    @Override
    public CycleUserDeptVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询轮转学生选课记录列表
     */
    @Override
    public TableDataInfo<CycleUserDeptVo> queryPageList(CycleUserDeptBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CycleUserDept> lqw = buildQueryWrapper(bo);
        Page<CycleUserDeptVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询轮转学生选课记录列表
     */
    @Override
    public List<CycleUserDeptVo> queryList(CycleUserDeptBo bo) {
        LambdaQueryWrapper<CycleUserDept> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CycleUserDept> buildQueryWrapper(CycleUserDeptBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CycleUserDept> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, CycleUserDept::getUserId, bo.getUserId());
        lqw.eq(bo.getGroupDeptId() != null, CycleUserDept::getGroupDeptId, bo.getGroupDeptId());
        lqw.eq(bo.getRuleId() != null, CycleUserDept::getRuleId, bo.getRuleId());
        lqw.eq(bo.getGroupId() != null, CycleUserDept::getGroupId, bo.getGroupId());
        lqw.eq(bo.getDeptId() != null, CycleUserDept::getDeptId, bo.getDeptId());
        lqw.eq(bo.getSelectTime() != null, CycleUserDept::getSelectTime, bo.getSelectTime());
        return lqw;
    }

    /**
     * 新增轮转学生选课记录
     */
    @Override
    public Boolean insertByBo(List<CycleUserDeptBo> bos) {
        List<CycleUserDept> adds = MapstructUtils.convert(bos, CycleUserDept.class);
        //校验数据
        validEntityBeforeSave(adds);
        baseMapper.insertBatch(adds);

        return true;
    }

    /**
     * 修改轮转学生选课记录
     */
    @Override
    public Boolean updateByBo(CycleUserDeptBo bo) {
        CycleUserDept update = MapstructUtils.convert(bo, CycleUserDept.class);
//        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(List<CycleUserDept> entitys){
        //校验
        CycleValidUtils.ValidStudentSelectDept(entitys);
    }


    /**
     * 批量删除轮转学生选课记录
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
