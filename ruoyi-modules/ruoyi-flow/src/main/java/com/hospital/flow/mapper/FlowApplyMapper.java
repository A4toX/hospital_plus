package com.hospital.flow.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital.flow.domain.req.MyFlowReq;
import com.hospital.flow.domain.req.TodoFlowReq;
import com.hospital.flow.domain.resp.ApplyResp;
import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import com.hospital.flow.domain.FlowApply;
import com.hospital.flow.domain.vo.FlowApplyVo;

/**
 * 流程申请 数据层
 *
 * @author liguoxian
 */
public interface FlowApplyMapper extends BaseMapperPlus<FlowApply, FlowApplyVo> {

    Page<ApplyResp> myFlow(Page page, @Param("param") MyFlowReq req);

    Page<ApplyResp> todoFlow(Page page, @Param("param") TodoFlowReq req, @Param("userId") Long userId);
}

