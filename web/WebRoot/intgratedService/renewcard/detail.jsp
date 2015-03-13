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
			<table class="detail_grid" width="80%" border="1" cellspacing="0" cellpadding="1">
				<caption>换卡详细信息<span class="caption_title"> | <f:link href="/intgratedService/renewcard/list.do?goBack=goBack">返回列表</f:link></span></caption>
				<tr>
					<td>换卡编号</td>
					<td>${renewCardReg.renewCardId}</td>	
					<td>新卡号</td>
					<td>${renewCardReg.newCardId}</td>	
				</tr>
				<tr>
					<td>旧卡号</td>	
					<td>${renewCardReg.cardId}</td>	
					<td>账号</td>
					<td>${renewCardReg.acctId}</td>
				</tr>
				<tr>
					<td>持卡人姓名</td>
					<td>${renewCardReg.custName}</td>
					<td>证件类型</td>
					<td>${renewCardReg.certTypeName}</td>	
				</tr>
				<tr>
					<td>证件号码</td>	
					<td>${renewCardReg.certNo}</td>	
					<td>换卡类型</td>
					<td>${renewCardReg.renewTypeName}</td>
				</tr>
				<tr>
					<td>状态</td>
					<td>${renewCardReg.statusName}</td>	
					<td>操作员</td>	
					<td>${renewCardReg.updateUser}</td>	
				</tr>
				<tr>
					<td>操作机构</td>	
					<td>${renewCardReg.branchCode}</td>	
					<td>机构名称</td>
					<td>${renewCardReg.branchName}</td>	
				</tr>
				<tr>	
					<td>操作时间</td>
					<td><s:date name = "renewCardReg.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td>备注</td>	
					<td>${renewCardReg.remark}</td>	
				</tr>
			</table>
			</div>
			
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=WorkflowConstants.WORKFLOW_RENEW_CARD%>"/>
			<jsp:param name="refId" value="${renewCardReg.renewCardId}"/>
		</jsp:include>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>
