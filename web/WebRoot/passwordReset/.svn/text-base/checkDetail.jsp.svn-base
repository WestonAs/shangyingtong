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
			<caption>${ACT.name}<span class="caption_title"> | <f:link href="/passwordReset/checkList.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
			<td>重置编号</td>
				<td>${passwordResetReg.passwordResetRegId}</td>
				<td>卡号</td>
				<td>${passwordResetReg.cardId}</td>
			</tr>
			<tr>
				<td>状态</td>
				<td>${passwordResetReg.statusName}</td>
				<td>备注</td>
				<td>${passwordResetReg.remark}</td>
			</tr>
			<tr>
				<td>更新用户名</td>
				<td>${passwordResetReg.updateBy}</td>
				<td>更新时间</td>
				<td><s:date name="passwordResetReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=Constants.WORKFLOW_PASSWORD_RESET_REG%>"/>
			<jsp:param name="refId" value="${passwordResetReg.passwordResetRegId}"/>
		</jsp:include>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>