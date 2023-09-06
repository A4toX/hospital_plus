package com.hospital.flow.service;

import com.hospital.flow.domain.req.AuditReq;
import com.hospital.flow.domain.req.MyFlowReq;
import com.hospital.flow.domain.req.TodoFlowReq;
import com.hospital.flow.domain.resp.ApplyResp;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.service.IBaseService;
import com.hospital.flow.domain.bo.FlowApplyBo;
import com.hospital.flow.domain.vo.FlowApplyVo;

import java.util.List;

/**
 * 流程申请 接口
 *
 * @author liguoxian
 */
public interface IFlowApplyService extends IBaseService<FlowApplyVo, FlowApplyBo> {

    /**
     * 同意申请
     * @param req
     * @return
     */
    int approve(AuditReq req);

    /**
     * 拒绝申请
     * @param req
     * @return
     */
    int reject(AuditReq req);

    /**
     * 取消申请
     * @param applyIds
     * @return
     */
    int cancel(Long[] applyIds);

    /**
     * 我的流程
     * @param req
     * @param pageQuery
     * @return
     */
    TableDataInfo<ApplyResp> myFlow(MyFlowReq req, PageQuery pageQuery);

    /**
     * 待办流程
     * @param req
     * @param pageQuery
     * @return
     */
    TableDataInfo<ApplyResp> todoFlow(TodoFlowReq req, PageQuery pageQuery);
}
