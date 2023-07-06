package com.hospital.cycle.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.hash.BloomFilter;
import com.hospital.cycle.domain.CycleRule;
import com.hospital.cycle.domain.CycleRuleBase;
import com.hospital.cycle.mapper.CycleRuleBaseMapper;
import com.hospital.cycle.mapper.CycleRuleMapper;
import com.hospital.cycle.utils.CycleCacheUtils;
import com.hospital.cycle.utils.CycleValidUtils;
import org.dromara.common.core.exception.ServiceException;
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
import org.dromara.common.redis.utils.RedisUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.hospital.cycle.domain.bo.CycleStudentBo;
import com.hospital.cycle.domain.vo.CycleStudentVo;
import com.hospital.cycle.domain.CycleStudent;
import com.hospital.cycle.mapper.CycleStudentMapper;
import com.hospital.cycle.service.ICycleStudentService;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hospital.cycle.constant.CycleConstant.CYCLE_STATUS_COMPLETE;
import static com.hospital.cycle.constant.CycleConstant.YES;
import static com.hospital.cycle.constant.CycleRedisConstant.CYCLE_STUDENT_PREFIX;

/**
 * 学员规则关联Service业务层处理
 *
 * @author yaoyingjie
 * @date 2023-06-24
 */
@RequiredArgsConstructor
@Service
public class CycleStudentServiceImpl implements ICycleStudentService {

    private final CycleStudentMapper baseMapper;
    private final CycleRuleMapper ruleMapper;
    private final CycleRuleBaseMapper ruleBaseMapper;
    private final StudentService studentService;

    /**
     * 查询学员规则关联
     */
    @Override
    public CycleStudentVo queryById(Long cycleStudentId){
        return baseMapper.selectVoById(cycleStudentId);
    }

    /**
     * 查询学员规则关联列表
     */
    @Override
    public TableDataInfo<CycleStudentVo> queryPageList(CycleStudentBo bo, PageQuery pageQuery) {
        Page<CycleStudentVo> result = baseMapper.selectPageWithStudentInfo(pageQuery.build(), this.buildQueryWrapper(bo));
        return TableDataInfo.build(result);
    }

    /**
     * 查询学员规则关联列表
     */
    @Override
    public List<CycleStudentVo> queryList(CycleStudentBo bo) {
//        return baseMapper.selectListWithStudentInfo(this.buildQueryWrapper(bo));
        return null;
    }

    private QueryWrapper<CycleStudent> buildQueryWrapper(CycleStudentBo bo) {
        Map<String, Object> params = bo.getParams();
        QueryWrapper<CycleStudent> wrapper = new QueryWrapper<>();
        wrapper.like(params.get("realName")!=null&&!ObjectUtil.equals("",params.get("realName")), "su.real_name", params.get("realName"))
            .eq(bo.getRuleId()!=null, "c.rule_id", bo.getRuleId())
            .eq(params.get("studentType")!=null&&!ObjectUtil.equals("",params.get("studentType")),"sus.student_type",params.get("studentType"))
            .eq(params.get("personType")!=null&&!ObjectUtil.equals("",params.get("personType")),"sus.person_type",params.get("personType"))
            .eq(params.get("residentYear")!=null&&!ObjectUtil.equals("",params.get("residentYear")),"sus.resident_year",params.get("residentYear"))
            .eq(params.get("baseId")!=null&&!ObjectUtil.equals("",params.get("baseId")),"sus.base_id",params.get("baseId"));
        return wrapper;
    }

    /**
     * 新增学员规则关联
     */
    @Override
    public Boolean insertByBo(List<CycleStudentBo> bos) {
        //去重
        bos = bos.stream().distinct().collect(Collectors.toList());
        List<CycleStudent> adds = MapstructUtils.convert(bos, CycleStudent.class);
        validEntityBeforeSave(adds);
        //新增或删除
        List<CycleStudent> cycleStudents = baseMapper.selectList(Wrappers.<CycleStudent>lambdaQuery().eq(CycleStudent::getRuleId, adds.get(0).getRuleId()));
        if (cycleStudents.isEmpty()){//如果没有人员的话直接新增
            baseMapper.insertBatch(adds);
            CycleCacheUtils.setStudent(adds);
            return true;
        }
        //新增人员
        List<CycleStudent> add = adds.stream()
            .filter(a -> cycleStudents.stream()
                .noneMatch(b -> ObjectUtil.equals(b.getUserId(), a.getUserId())
                    && ObjectUtil.equals(b.getRuleId(), a.getRuleId())
                )
            )
            .collect(Collectors.toList());
        //删除人员
        List<CycleStudent> del = cycleStudents.stream()
            .filter(a -> adds.stream()
                .noneMatch(b -> ObjectUtil.equals(b.getUserId(), a.getUserId())
                    && ObjectUtil.equals(b.getRuleId(), a.getRuleId())
                )
            )
            .toList();
        //新增
        if (!add.isEmpty()){
            baseMapper.insertBatch(add);
            CycleCacheUtils.setStudent(add);
        }
        //删除
        if (!del.isEmpty()){
            baseMapper.deleteBatchIds(del.stream().map(CycleStudent::getCycleStudentId).collect(Collectors.toList()));
            Collection<Long> userIds = del.stream().map(CycleStudent::getCycleStudentId).collect(Collectors.toList());
            CycleCacheUtils.delStudent(userIds);
        }
        return true;
    }

    @Override
    public Boolean updateByBo(CycleStudentBo bo) {
        return null;
    }


    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(List<CycleStudent> list){
        CycleValidUtils.validCycleStudent(list);
    }

    /**
     * 批量删除学员规则关联
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
