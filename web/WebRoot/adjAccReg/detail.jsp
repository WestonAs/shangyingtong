<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="gnete.etc.WorkflowConstants"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>${ACT.name}</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>调账申请详细信息<span class="caption_title"> | <f:link href="/adjAccReg/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>编号</td>
				<td>${adjAccReg.adjAccId}</td>
				<td>交易流水</td>
				<td>${adjAccReg.transSn}</td>
				<td>交易类型</td>
				<td>${adjAccReg.transTypeName}</td>
		  	</tr>
			<tr>
				<td>账号</td>
				<td>${adjAccReg.acctId}</td>
				<td>卡号</td>
				<td>${adjAccReg.cardId}</td>
				<td>商户号</td>
				<td>${adjAccReg.merNo}</td>
		  	</tr>
			<tr>
				<td>商户名称</td>
				<td>${fn:merch(adjAccReg.merNo)}</td>
				<td>终端号</td>
				<td>${adjAccReg.termlId}</td>
				<td>发起平台</td>
				<td>${adjAccReg.platformName}</td>
			</tr>
			<tr>
				<td>交易金额</td>
				<td>${fn:amount(adjAccReg.transAmt)}</td>
				<td>处理时间</td>
				<td><s:date name="adjAccReg.procTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td>处理状态</td>
				<td>${adjAccReg.procStatusName}</td>
			</tr>
			<tr>
				<td>业务状态</td>
				<td>${adjAccReg.busiStatusName}</td>
				<td>退货状态</td>
				<td>${adjAccReg.goodsStatusName}</td>
				<td>调账金额</td>
				<td>${fn:amount(adjAccReg.amt)}</td>
			</tr>
			<tr>
				<td>状态</td>
				<td>${adjAccReg.statusName}</td>
		  		<td>标志</td>
				<td>${adjAccReg.flagName}</td>
				<td>操作员</td>
				<td>${adjAccReg.updateUser}</td>
			</tr>
			<tr>
				<td>操作时间</td>
				<td><s:date name="adjAccReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td>备注</td>
				<td colspan="3">${adjAccReg.remark}</td>
		  	</tr>
		</table>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=WorkflowConstants.WORKFLOW_ADJ%>"/>
			<jsp:param name="refId" value="${adjAccReg.adjAccId}"/>
		</jsp:include>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>