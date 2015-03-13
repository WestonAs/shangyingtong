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
		
		<!-- 准备金调整申请信息明细 -->
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>风险准备金调整申请详细信息<span class="caption_title"> | <f:link href="/cardRisk/cardRiskReg/checkList.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>申请编号</td>
				<td>${cardRiskReg.id}</td>
				<td>发卡机构</td>
				<td>${cardRiskReg.branchCode}-${fn:branch(cardRiskReg.branchCode)}</td>
		  	</tr>
		  	<tr>
		  		<td>调整类型</td>
				<td>${cardRiskReg.adjTypeName}</td>
				<td>相关金额</td>
				<td>${cardRiskReg.amt}</td>
		  	</tr>
		  	<tr>
		  		<td>原金额</td>
				<td>${cardRiskReg.orgAmt}</td>
				<td>状态</td>
				<td>${cardRiskReg.statusName}</td>
		  	</tr>
		  	<tr>
				<td>生效日期</td>
				<td><s:date name="cardRiskReg.effectiveDate" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>
		
		<!-- 机构准备金余额信息明细 -->
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>机构准备金余额详细信息<span class="caption_title"> </span></caption>
			<tr>
				<td>机构编号</td>
				<td>${cardRiskBalance.branchCode}</td>
				<td>资质信任额度</td>
				<td>${cardRiskBalance.trustAmt}</td>				
			</tr>
		  	<tr>		  		
				<td>风险保证金</td>
				<td>${cardRiskBalance.riskAmt}</td>
				<td>售卡充值总金额</td>
				<td>${cardRiskBalance.sellAmt}</td>
			</tr>
		  	<tr>		  		
				<td>已清算本金总金额</td>
				<td>${cardRiskBalance.settleAmt}</td>
				<td>当前可用充值售卡金额</td>
				<td>${cardRiskBalance.availableAmt}</td>
			</tr>
		</table>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=Constants.WORKFLOW_CARD_RISK_REG%>"/>
			<jsp:param name="refId" value="${cardRiskReg.id}"/>
		</jsp:include>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>