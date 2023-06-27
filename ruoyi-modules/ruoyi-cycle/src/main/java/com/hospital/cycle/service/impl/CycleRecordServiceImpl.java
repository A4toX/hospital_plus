package com.hospital.cycle.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hospital.cycle.domain.CycleGroupDept;
import com.hospital.cycle.domain.vo.CycleRecordImportVo;
import com.hospital.cycle.mapper.CycleGroupDeptMapper;
import org.dromara.common.core.system.domain.Dept;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.core.utils.system.DeptUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.system.mapper.SysDeptMapper;
import org.springframework.stereotype.Service;
import com.hospital.cycle.domain.bo.CycleRecordBo;
import com.hospital.cycle.domain.vo.CycleRecordVo;
import com.hospital.cycle.domain.CycleRecord;
import com.hospital.cycle.mapper.CycleRecordMapper;
import com.hospital.cycle.service.ICycleRecordService;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 用户轮转记录Service业务层处理
 *
 * @author yaoyingjie
 * @date 2023-06-25
 */
@RequiredArgsConstructor
@Service
public class CycleRecordServiceImpl implements ICycleRecordService {

    private final CycleRecordMapper baseMapper;
    private final CycleGroupDeptMapper groupDeptMapper;

    /**
     * 查询用户轮转记录
     */
    @Override
    public CycleRecordVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询用户轮转记录列表
     */
    @Override
    public TableDataInfo<CycleRecordVo> queryPageList(CycleRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CycleRecord> lqw = buildQueryWrapper(bo);
        Page<CycleRecordVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询用户轮转记录列表
     */
    @Override
    public List<CycleRecordVo> queryList(CycleRecordBo bo) {
        LambdaQueryWrapper<CycleRecord> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CycleRecord> buildQueryWrapper(CycleRecordBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CycleRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, CycleRecord::getUserId, bo.getUserId());
        lqw.eq(bo.getDeptId() != null, CycleRecord::getDeptId, bo.getDeptId());
        lqw.eq(StringUtils.isNotBlank(bo.getCycleStatus()), CycleRecord::getCycleStatus, bo.getCycleStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getStartTime()), CycleRecord::getStartTime, bo.getStartTime());
        lqw.eq(StringUtils.isNotBlank(bo.getEndTime()), CycleRecord::getEndTime, bo.getEndTime());
        lqw.eq(bo.getRuleId() != null, CycleRecord::getRuleId, bo.getRuleId());
        lqw.eq(bo.getBaseId() != null, CycleRecord::getBaseId, bo.getBaseId());
        lqw.eq(bo.getCycleRecordIndex() != null, CycleRecord::getCycleRecordIndex, bo.getCycleRecordIndex());
        return lqw;
    }

    /**
     * 新增用户轮转记录
     */
    @Override
    public Boolean insertByBo(CycleRecordBo bo) {
        CycleRecord add = MapstructUtils.convert(bo, CycleRecord.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改用户轮转记录
     */
    @Override
    public Boolean updateByBo(CycleRecordBo bo) {
        CycleRecord update = MapstructUtils.convert(bo, CycleRecord.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CycleRecord entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除用户轮转记录
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }


    public void importData(InputStream inputStream,Long ruleId){
        EasyExcel.read(inputStream,new AnalysisEventListener<Map<String, Object>>() {
            private Map<Integer, String> headMap;
            private int failureNum = 0;
            private final StringBuilder failureMsg = new StringBuilder();




            @Override
            public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
                this.headMap = headMap;
                System.out.println("表头信息：" + headMap);
            }
            @Override
            public void invoke(Map<String, Object> valueData, AnalysisContext context) {
                List<CycleRecordImportVo> voList = new ArrayList<>();
                //获取科室信息
                    if (valueData.get(0)==null){
                        String msg = "第"+context.readRowHolder().getRowIndex()+"行科室不能为空";
                        failureMsg.append("<br/>").append(failureNum).append("、").append(msg);
                        failureNum++;
                        System.out.println(msg);
                    }
                    /*else {
                        importVo.setDeptName(valueData.get(0).toString().replaceAll("\\s*", ""));
                        Dept dept = DeptUtils.getDeptByDeptName(valueData.get(i).toString());

                        if (dept==null){
                            String msg = "第"+context.readRowHolder().getRowIndex()+"列科室不存在";
                            failureMsg.append("<br/>").append(failureNum).append("、").append(msg);
                            failureNum++;
//                            System.out.println(msg);
                        }else {
                            //校验科室是否在轮转规则中，不在的话返回错误信息
                            CycleGroupDept cycleGroupDept = groupDeptMapper.selectOne(Wrappers.<CycleGroupDept>lambdaQuery().eq(CycleGroupDept::getDeptId, dept.getDeptId()).eq(CycleGroupDept::getRuleId, ruleId));
                            if (cycleGroupDept==null){
                                String msg = "第"+(i+1)+"列科室不在轮转规则中";
                                failureMsg.append("<br/>").append(failureNum).append("、").append(msg);
                                failureNum++;
                            }else {
                                importVo.setDeptId(dept.getDeptId());
                            }

                        }
                    }*/
                //获取开始时间和结束时间
                for(int i=1;i<valueData.size();i++){
                    CycleRecordImportVo importVo = new CycleRecordImportVo();
                    importVo.setDeptName(valueData.get(0).toString().replaceAll("\\s*", ""));
                    //获取时间去除所有的空格
                    String time = headMap.get(i).replaceAll("\\s*", "");
                    System.out.println("时间："+time);
                    //截取前10位开始时间
                    String startTime = time.substring(0, 10);
                    //从结尾开始截取10位结束时间
                    String endTime = time.substring(time.length() - 10);
                  /*  startTime = DateUtil.format(DateUtil.parse(startTime), "yyyy-MM-dd");
                    endTime = DateUtil.format(DateUtil.parse(endTime), "yyyy-MM-dd");*/
                    //使用正则校验时间格式是否为yyyy-MM-dd
                    String reg = "\\d{4}-\\d{2}-\\d{2}";
                    if (startTime.matches(reg) && endTime.matches(reg)) {
                        importVo.setStartTime(startTime);
                        importVo.setEndTime(endTime);
                    } else {
                        String msg = "第" + (i + 1) + "列时间格式不正确";
                        failureMsg.append("<br/>").append(failureNum).append("、").append(msg);
                        failureNum++;
                        System.out.println(msg);
                    }
                    importVo.setStartTime(startTime);
                    importVo.setEndTime(endTime);

                    if (valueData.get(i)!=null){
                        //去除空格和尾部多余逗号
                        String data = valueData.get(i).toString()
                            .replaceAll("\\s*", "");
                        //判断是否有中文逗号，有的话替换成英文逗号
                        if (data.contains("，")){
                            data = data.replaceAll("，",",");
                        }
                        //判断是否有逗号，有的话分割成list
                        if (data.contains(",")){
                            //判断尾部是否有逗号，有的话去除
                            if (data.endsWith(",")){
                                data = data.substring(0,data.length()-1);
                            }
                            String[] split = data.split(",");
                            List<String> students = Arrays.asList(split);
                            importVo.setStudentList(students);
                        }else {
                            List<String> students = new ArrayList<>();
                            students.add(data);
                            importVo.setStudentList(students);
                        }
                    }
                    voList.add(importVo);
                }
                voList.forEach(vo -> {
                    System.out.println("科室名称: "+vo.getDeptName()+"开始时间: "+vo.getStartTime()+"结束时间: "+vo.getEndTime()+"学生: "+vo.getStudentList());
                });
            }


            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
        }).sheet().doRead();
    }
}
