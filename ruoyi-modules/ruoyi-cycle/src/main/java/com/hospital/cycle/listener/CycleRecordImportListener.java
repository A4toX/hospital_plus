package com.hospital.cycle.listener;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hospital.cycle.domain.vo.CycleRecordImportVo;
import com.hospital.cycle.service.ICycleRecordService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.core.utils.ValidatorUtils;
import org.dromara.common.excel.core.DefaultExcelListener;
import org.dromara.common.excel.core.ExcelListener;
import org.dromara.common.excel.core.ExcelResult;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.system.domain.bo.SysUserBo;
import org.dromara.system.domain.vo.SysUserImportVo;
import org.dromara.system.domain.vo.SysUserVo;
import org.dromara.system.service.ISysConfigService;
import org.dromara.system.service.ISysUserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 系统用户自定义导入
 *
 * @author Lion Li
 */
@Slf4j
public class CycleRecordImportListener extends AnalysisEventListener<Map<String,Object>> implements ExcelListener<Map<String,Object>> {

    private final ICycleRecordService recordService;
    private final List<String> failureMsg = new ArrayList<>();

    public CycleRecordImportListener(Boolean isUpdateSupport,Long ruleId) {
        this.recordService = SpringUtils.getBean(ICycleRecordService.class);
    }

    private Map<Integer, String> headMap;
    List<CycleRecordImportVo> voList = new ArrayList<>();
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        //表头信息
        this.headMap = headMap;
        //校验时间格式
        for (int i = 1; i < headMap.size(); i++) {
            if(headMap.get(i)==null) {
                failureMsg.add("第" + i + "列时间不能为空");
            }else {
                String validTime = headMap.get(i).replaceAll("\\s*", "");
                String validStartTime = validTime.substring(0, 10);
                //从结尾开始截取10位结束时间
                String validEndTime = validTime.substring(validTime.length() - 10);
                String reg = "\\d{4}-\\d{2}-\\d{2}";
                if (!validStartTime.matches(reg) || !validEndTime.matches(reg)) {
                    failureMsg.add("第" + (i + 1) + "列时间格式不正确");
                }
            }
        }
    }

    @Override
    public void invoke(Map<String, Object> valueData, AnalysisContext context) {
        //获取科室信息
        if (valueData.get(0)==null){
            failureMsg.add("第"+context.readRowHolder().getRowIndex()+"行科室不能为空");
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
            //截取前10位开始时间
            String startTime = time.substring(0, 10);
            //从结尾开始截取10位结束时间
            String endTime = time.substring(time.length() - 10);
                  /*  startTime = DateUtil.format(DateUtil.parse(startTime), "yyyy-MM-dd");
                    endTime = DateUtil.format(DateUtil.parse(endTime), "yyyy-MM-dd");*/
            //使用正则校验时间格式是否为yyyy-MM-dd
            /*String reg = "\\d{4}-\\d{2}-\\d{2}";
            if (startTime.matches(reg) && endTime.matches(reg)) {
                importVo.setStartTime(startTime);
                importVo.setEndTime(endTime);
            } else {
                String msg = "第" + (i + 1) + "列时间格式不正确";
                failureMsg.append("<br/>").append(failureNum).append("、").append(msg);
                failureNum++;
                System.out.println(msg);
            }*/
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
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (failureMsg.size() < 1) {
            recordService.importRecord(voList);
        }
    }

    @Override
    public ExcelResult<Map<String,Object>> getExcelResult() {
        return new ExcelResult<>() {

            @Override
            public String getAnalysis() {
                return null;
            }

            @Override
            public List<Map<String,Object>> getList() {
                return null;
            }

            @Override
            public List<String> getErrorList() {
                return failureMsg;
            }
        };
    }
}
