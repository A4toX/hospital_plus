package com.hospital.flow.service;

import com.hospital.flow.domain.req.AuditReq;
import org.dromara.common.mybatis.core.service.IBaseService;
import com.hospital.flow.domain.bo.FlowApplyBo;
import com.hospital.flow.domain.vo.FlowApplyVo;

/**
 * 流程申请 接口
 *
 * @author liguoxian
 */
public interface IFlowApplyService extends IBaseService<FlowApplyVo, FlowApplyBo> {

    int approve(AuditReq req);

    int reject(AuditReq req);
}
