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
			<caption>积分调整记录详细信息<span class="caption_title"> | <f:link href="/pointBus/pointChange/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>积分调整编号</td>
				<td>${pointChgReg.pointChgId}</td>
				<td>积分类型</td>
				<td>${pointChgReg.ptClass}</td>
			</tr>
			<tr>
				<td>机构类型</td>
				<td>${pointChgReg.jinstTypeName}</td>
				<td>机构编号</td>
				<td>${pointChgReg.jinstId}</td>
			</tr>
			<tr>
				<td>卡号</td>
				<td>${pointChgReg.cardId}</td>
				<td>账号</td>
				<td>${pointChgReg.acctId}</td>
			</tr>
			<tr>
				<td>原可用积分</td>
				<td>${fn:amount(pointChgReg.ptAvlb)}</td>
				<td>调整积分</td>
				<td>${fn:amount(pointChgReg.ptChg)}</td>
			</tr>
			<tr>
				<td>发卡机构</td>
				<td>${pointChgReg.branchCode}-${fn:branch(pointChgReg.branchCode)}</td>
				<td>操作机构</td>
				<td>${pointChgReg.cardBranch}-${fn:branch(pointChgReg.cardBranch)}</td>
			</tr>
			<tr>
				<td>状态</td>
				<td>${pointChgReg.statusName}</td>
				<td>备注</td>
				<td>${pointChgReg.remark}</td>
			</tr>
		  	<tr>
				<td>更新用户名</td>
				<td>${pointChgReg.updateUser}</td>
		  		<td>更新时间</td>
				<td><s:date name="pointChgReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=Constants.WORKFLOW_POINT_CHG_REG%>"/>
			<jsp:param name="refId" value="${pointChgReg.pointChgId}"/>
		</jsp:include>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>