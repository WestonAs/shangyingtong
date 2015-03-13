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
				<caption>卡延期详细信息<span class="caption_title"> | <f:link href="/carddDefer/checkList.do?goBack=goBack">返回列表</f:link></span></caption>
				<tr>
					<td>延期编号</td>
					<td>${cardDeferReg.cardDeferId}</td>	
					<td>卡号</td>	
					<td>${cardDeferReg.cardId}</td>	
				</tr>
				<tr>
					<td>开始卡号</td>
					<td>${cardDeferReg.startCard}</td>
					<td>结束卡号</td>
					<td>${cardDeferReg.endCard}</td>
				</tr>
				<tr>
					<td>原生效日期</td>
					<td>${cardDeferReg.effDate}</td>
					<td>原失效日期</td>
					<td>${cardDeferReg.expirDate}</td>
				</tr>
				<tr>
					<td>延期日期</td>
					<td>${cardDeferReg.newExpirDate}</td>	
					<td>状态</td>
					<td>${cardDeferReg.statusName}</td>	
				</tr>
				<tr>
					<td>操作机构</td>
					<td>${cardDeferReg.branchCode}</td>	
					<td>机构名称</td>
					<td>${cardDeferReg.branchName}</td>	
				</tr>
				<tr>
					<td>发卡机构</td>	
					<td>${cardDeferReg.cardBranch}-${fn:branch(cardDeferReg.cardBranch) }</td>	
					<td>领卡机构</td>
					<td>${cardDeferReg.appOrgId}-${fn:branch(cardDeferReg.appOrgId)}${fn:dept(cardDeferReg.appOrgId)}</td>	
				</tr>
				<tr>
					<td>操作员</td>	
					<td>${cardDeferReg.updateUser}</td>	
					<td>操作时间</td>
					<td><s:date name = "cardDeferReg.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>	
				</tr>
				<tr>
					<td>备注</td>	
					<td colspan="3">${cardDeferReg.remark}</td>	
				</tr>
				</table>
			</div>
			
			<!-- 流程相关信息 -->
			<jsp:include flush="true" page="/workflow/flow.jsp">
				<jsp:param name="workflowId" value="<%=WorkflowConstants.WORKFLOW_CARD_DEFFER%>"/>
				<jsp:param name="refId" value="${cardDeferReg.cardDeferId}"/>
			</jsp:include>
			
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>
