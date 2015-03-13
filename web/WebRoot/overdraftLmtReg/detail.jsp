<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="gnete.etc.Constants"%>
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
			<caption>账户透支额度调整申请记录详细信息<span class="caption_title"> | <f:link href="/overdraftLmtReg/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>调整编号</td>
				<td>${overdraftLmtReg.overdraftLmtId}</td>
				<td>签单客户号</td>
				<td>${overdraftLmtReg.signCustId}</td>
		  	</tr>
		  	<tr>
		  		<td>卡号</td>
				<td>${overdraftLmtReg.cardId}</td>
				<td>账号</td>
				<td>${overdraftLmtReg.acctId}</td>
			<tr>
				<td>原透支金额</td>
				<td>${fn:amount(overdraftLmtReg.overdraft)}</td>
				<td>调整透支金额</td>
				<td>${fn:amount(overdraftLmtReg.newOverdraft)}</td>
			</tr>
			<tr>
				<td>状态</td>
				<td>${overdraftLmtReg.statusName}</td>
				<td>备注</td>
				<td>${overdraftLmtReg.remark}</td>
		  	</tr>
		  	<tr>
		  		<td>更新用户名</td>
				<td>${overdraftLmtReg.updateUser}</td>
				<td>更新时间</td>
				<td><s:date name="overdraftLmtReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
		</table>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=Constants.WORKFLOW_OVERDRAFT_LMT_REG%>"/>
			<jsp:param name="refId" value="${overdraftLmtReg.overdraftLmtId}"/>
		</jsp:include>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>