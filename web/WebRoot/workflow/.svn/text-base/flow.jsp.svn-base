<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@page import="java.util.List"%>
<%@page import="gnete.card.workflow.service.WorkflowService"%>
<%@page import="flink.util.SpringContext"%>
<%@page import="gnete.card.workflow.entity.WorkflowHis"%>
<%@ include file="/common/taglibs.jsp" %>
<%response.setHeader("Cache-Control", "no-cache");%>
<%
	String workflowId = (String)request.getParameter("workflowId");
	String refId = (String)request.getParameter("refId");
	
	WorkflowService workflowService = (WorkflowService)SpringContext.getService("workflowService");
	List<WorkflowHis> list = workflowService.findFlowHis(workflowId, refId);
	request.setAttribute("list", list);
%>
	<s:if test="#request.list != null && #request.list.size > 0">
		<h2 style="font-size: 14px;padding-left: 10px;">流程处理记录：</h2>
		<s:iterator value="#request.list" status="status">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
		<tr>
			<td width="100px">流程步骤</td>
			<td width="150px">${nodeName}</td>
			<td width="100px"><s:if test="operType == 0">申请人</s:if><s:else>处理人</s:else></td>
			<td width="150px">${fn:user(userId)}</td>
			<td width="100px"><s:if test="operType == 0">申请时间</s:if><s:else>处理时间</s:else></td>
			<td width="150px"><s:date name="operTime" format="yyyy-MM-dd HH:mm:ss" /></td>
	  	</tr>
	  	<s:if test="operType != 0">
		<tr>
			<td width="100px">处理结果</td>
			<td width="150px" <s:if test="operType == 2">class="redfont"</s:if>>${operTypeName}</td>
			<td width="100px">处理意见</td>
			<td colspan="3">${operDesc}</td>
	  	</tr>
	  	</s:if>
		</table>
	  	<s:if test="#status.count < #request.list.size()">
  			<center><img src="<%=request.getContextPath()%>/images/arrow_flow.gif"/></center>
	  	</s:if>
	  	</s:iterator>
	</s:if>