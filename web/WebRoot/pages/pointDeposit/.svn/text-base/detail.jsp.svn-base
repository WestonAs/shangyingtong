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
		
		<div id="printArea" >
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>积分充值明细信息<span class="caption_title"> | <f:link href="/pages/pointDeposit/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>充值批次ID</td>
				<td>${depositPointReg.depositBatchId}</td>
				<td>卡号</td>
				<td>${depositPointReg.cardId}</td>
				<td>充值日期</td>
				<td>${depositPointReg.depositDate}</td>
			</tr>
		  	<tr>
		  		<td>发卡机构号</td>
		  		<td>${depositPointReg.cardBranch}</td>
		  		<td>发卡机构名称</td>
		  		<td>${fn:branch(depositPointReg.cardBranch)}</td>
				<td>状态</td>
				<td>${depositPointReg.statusName}</td>
			</tr>
			<tr>
		  		<td>充值积分</td>
				<td>${depositPointReg.ptPoint}</td>
				<td>折算金额</td>
				<td>${fn:amount(depositPointReg.refAmt)}</td>
				<td>积分类型</td>
				<td>${depositPointReg.ptClass}</td>
			</tr>
			<tr>
				<td>充值机构号</td>
				<td>${depositPointReg.depositBranch}</td>
				<td>充值机构名</td>
				<td>${fn:branch(depositPointReg.depositBranch)}</td>
				<td>备注</td>
		  		<td>${depositPointReg.remark}</td>
			</tr>
		  	<tr>
		  		<td>更新人</td>
				<td>${depositPointReg.updateUser}</td>
		  		<td>更新时间</td>
				<td colspan="3"><s:date name="depositPointReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>
		</div>
		
		<div style="padding-left: 30px;margin: 0px;">
			<input type="button" value="打印" onclick="openPrint();"/>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=WorkflowConstants.WORKFLOW_DEPOSIT_POINT%>"/>
			<jsp:param name="refId" value="${depositPointReg.depositBatchId}"/>
		</jsp:include>
		<br />
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>