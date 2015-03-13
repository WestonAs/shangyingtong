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
			<caption>配额登记详细信息<span class="caption_title"> | <f:link href="/sellAmt/checkList.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>申请编号</td>
				<td>${branchSellReg.id}</td>
				<td>发卡机构代码</td>
				<td>${branchSellReg.cardBranch}-${fn:branch(branchSellReg.cardBranch)}</td>
				<td>售卡机构代码</td>
				<td>${branchSellReg.sellBranch}-${fn:branch(branchSellReg.sellBranch)}${fn:dept(branchSellReg.sellBranch)}</td>
		  	</tr>
		  	<tr>
		  		<td>售卡机构类型</td>
				<td>${branchSellReg.sellTypeName}</td>
		  		<td>调整类型</td>
				<td>${branchSellReg.adjTypeName}</td>
				<td>相关金额</td>
				<td>${fn:amount(branchSellReg.amt)}</td>
		  	</tr>
		  	<tr>
		  		<td>原金额</td>
				<td>${fn:amount(branchSellReg.orgAmt)}</td>
				<td>状态</td>
				<td>${branchSellReg.statusName}</td>
				<td>更新日期</td>
				<td>${branchSellReg.effectiveDate}</td>
		  	</tr>
		</table>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=WorkflowConstants.WORKFLOW_BRANCH_SELL_REG%>"/>
			<jsp:param name="refId" value="${branchSellReg.id}"/>
		</jsp:include>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>