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
		
		<!-- 客户返利明细 -->
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>客户返利登记簿详细信息<span class="caption_title"> | <f:link href="/customerRebateMgr/customerRebateReg/checkList.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>购卡客户ID</td>
				<td>${cardCustomer.cardCustomerId}</td>
				<td>购卡客户名称</td>
				<td>${cardCustomer.cardCustomerName}</td>
				<td>卡BIN</td>
				<td>${cardBin.binNo}</td>
			</tr>
			<tr>
				<td>卡BIN名称</td>
				<td>${cardBin.binName}</td>
				<td>更新人</td>
				<td>${customerRebateReg.updateUser}</td>
				<td>更新时间</td>
				<td><s:date name="customerRebateReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<td>售卡返利ID</td>
				<td>${saleRebateRule.rebateId}</td>
				<td>售卡返利名称</td>
				<td>${saleRebateRule.rebateName}</td>
				<td>售卡返利计算方式</td>
				<td>${saleRebateRule.calTypeName}</td>				
			</tr>
			<tr>
				<td>充值返利ID</td>
				<td>${depositRebateRule.rebateId}</td>
				<td>充值返利名称</td>
				<td>${depositRebateRule.rebateName}</td>
				<td>充值返利计算方式</td>
				<td>${depositRebateRule.calTypeName}</td>	
			</tr>
			<tr>
				<td>状态</td>
				<td>${customerRebateReg.statusName}</td>
				<td>发卡机构</td>
				<td>${customerRebateReg.cardBranch}</td>
				<td>录入机构</td>
				<td>${customerRebateReg.inputBranch}-${fn:branch(customerRebateReg.inputBranch)}</td>							
		  	</tr>
		  	
		</table>
		</div>
		
		<!-- 售卡返利规则分段比例明细 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">售卡返利规则ID</td>
			   <td align="center" nowrap class="titlebg">售卡返利分段号</td>
			   <td align="center" nowrap class="titlebg">售卡分段金额上限</td>
			   <td align="center" nowrap class="titlebg">售卡返利比（%）或返利金额</td>			   
			</tr>
			</thead>
			<s:iterator value="saleRebateRuleDetailList"> 
			<tr>
			  <td nowrap>${rebateId}</td>
			  <td align="center" nowrap>${rebateSect}</td>			  
			  <td align="center" nowrap>${rebateUlimit}</td>
			  <td align="center" nowrap>${rebateRate}</td>			  
			</tr>
			</s:iterator>
			</table>
		</div>

		<!-- 充值返利规则分段比例明细 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">充值返利规则ID</td>
			   <td align="center" nowrap class="titlebg">充值返利分段号</td>
			   <td align="center" nowrap class="titlebg">充值分段金额上限</td>
			   <td align="center" nowrap class="titlebg">充值返利比（%）或返利金额</td>			   
			</tr>
			</thead>
			<s:iterator value="depositRebateRuleDetailList"> 
			<tr>
			  <td nowrap>${rebateId}</td>
			  <td align="center" nowrap>${rebateSect}</td>			  
			  <td align="center" nowrap>${rebateUlimit}</td>
			  <td align="center" nowrap>${rebateRate}</td>			  
			</tr>
			</s:iterator>
			</table>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=Constants.WORKFLOW_CUSTOMER_REBATE_REG%>"/>
			<jsp:param name="refId" value="${customerRebateReg.customerRebateRegId}"/>
		</jsp:include>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>