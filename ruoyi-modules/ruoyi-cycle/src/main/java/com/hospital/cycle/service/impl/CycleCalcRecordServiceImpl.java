package com.hospital.cycle.service.impl;

import com.hospital.cycle.domain.CycleGroupDept;
import com.hospital.cycle.mapper.CycleGroupDeptMapper;
import com.hospital.cycle.mapper.CycleRecordMapper;
import com.hospital.cycle.utils.CycleCalcUtils;
import com.hospital.cycle.utils.CycleUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.dromara.common.core.service.DeptService;
import org.dromara.common.core.service.StudentService;
import org.dromara.common.core.service.domain.Student;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hospital.cycle.domain.bo.CycleCalcRecordBo;
import com.hospital.cycle.domain.vo.CycleCalcRecordVo;
import com.hospital.cycle.domain.CycleCalcRecord;
import com.hospital.cycle.mapper.CycleCalcRecordMapper;
import com.hospital.cycle.service.ICycleCalcRecordService;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 轮转计算过程记录Service业务层处理
 *
 * @author yaoyingjie
 * @date 2023-07-02
 */
@RequiredArgsConstructor
@Service
public class CycleCalcRecordServiceImpl implements ICycleCalcRecordService {

    private final CycleCalcRecordMapper baseMapper;
    private final CycleGroupDeptMapper groupDeptMapper;
    private final DeptService deptService;
    private final StudentService studentService;

    /**
     * 查询轮转计算过程记录
     */
    @Override
    public CycleCalcRecordVo queryById(Long calcRecordId){
        return baseMapper.selectVoById(calcRecordId);
    }

    /**
     * 查询轮转计算过程记录列表
     */
    @Override
    public TableDataInfo<CycleCalcRecordVo> queryPageList(CycleCalcRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CycleCalcRecord> lqw = buildQueryWrapper(bo);
        Page<CycleCalcRecordVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询轮转计算过程记录列表
     */
    @Override
    public List<CycleCalcRecordVo> queryList(CycleCalcRecordBo bo) {
        LambdaQueryWrapper<CycleCalcRecord> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CycleCalcRecord> buildQueryWrapper(CycleCalcRecordBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CycleCalcRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getRuleId() != null, CycleCalcRecord::getRuleId, bo.getRuleId());
        lqw.eq(bo.getDeptId() != null, CycleCalcRecord::getDeptId, bo.getDeptId());
        lqw.eq(bo.getDeptIndex() != null, CycleCalcRecord::getDeptIndex, bo.getDeptIndex());
        lqw.eq(bo.getDeptCapacity() != null, CycleCalcRecord::getDeptCapacity, bo.getDeptCapacity());
        lqw.eq(bo.getUnDeptCapacity() != null, CycleCalcRecord::getUnDeptCapacity, bo.getUnDeptCapacity());
        lqw.eq(StringUtils.isNotBlank(bo.getUserIds()), CycleCalcRecord::getUserIds, bo.getUserIds());
        return lqw;
    }

    /**
     * 新增轮转计算过程记录
     */
    @Override
    public Boolean insertByBo(CycleCalcRecordBo bo) {
        CycleCalcRecord add = MapstructUtils.convert(bo, CycleCalcRecord.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setCalcRecordId(add.getCalcRecordId());
        }
        return flag;
    }

    /**
     * 修改轮转计算过程记录
     */
    @Override
    public Boolean updateByBo(CycleCalcRecordBo bo) {
        CycleCalcRecord update = MapstructUtils.convert(bo, CycleCalcRecord.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CycleCalcRecord entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除轮转计算过程记录
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public void exportList(Long ruleId, HttpServletResponse response){
        //设置表头
        List<List<String>> headList = CycleUtils.getCycleExportHeadByUserWtihUnit(ruleId);
        List<String> deptHead = new ArrayList<>();
        headList.add(deptHead);
        deptHead.add("科室\\数字");
        Integer ruleTotalTimeUnit = CycleCalcUtils.getTotalTimeUnit(ruleId);
        for (int i=1;i<=ruleTotalTimeUnit;i++){
            List<String> head = new ArrayList<>();
            head.add(String.valueOf(i));
            headList.add(head);
        }
        List<List<String>> dataList = new ArrayList<>();
        //获取规则下所有科室
        List<CycleGroupDept> cycleGroupDeptList = groupDeptMapper.selectList(Wrappers.<CycleGroupDept>lambdaQuery().eq(CycleGroupDept::getRuleId, ruleId));
        //设置数据
        cycleGroupDeptList.forEach(cycleGroupDept ->{
            List<String> data = new ArrayList<>();
            //先添加科室名
            Long deptId = cycleGroupDept.getDeptId();
            data.add(deptService.selectDeptNameById(deptId));//设置科室名
            for (int i=1;i<headList.size();i++){
                Integer headIndex = Integer.valueOf(headList.get(i).get(0));
                CycleCalcRecord calcRecords = baseMapper.selectOne(Wrappers.<CycleCalcRecord>lambdaQuery()
                    .eq(CycleCalcRecord::getRuleId, ruleId)
                    .eq(CycleCalcRecord::getDeptId, deptId)
                    .eq(CycleCalcRecord::getDeptIndex, headIndex));
                if (calcRecords.getUserIds()==null||"".equals(calcRecords.getUserIds())){
                    data.add("无数据");
                    continue;
                }
                Set<Long> studentIdList = Arrays.stream(calcRecords.getUserIds().split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toSet());

                List<Student> students = studentService.selectStudentByUserIds(studentIdList);
                String studentNames = students.stream().map(Student::getRealName).collect(Collectors.joining(","));
                data.add(studentNames);
            }
            dataList.add(data);
        });


        //循环表头
//                String startTime = head.get(0).substring(0, 10);
        //结束时间
//                String endTime = head.get(0).substring(head.get(0).length() - 10);

        //导出
        ExcelUtil.writeForHeaderList(response,"轮转表.xlsx", "steet1", headList,headList.size(),dataList);


    }
}
