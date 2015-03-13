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
			<caption>签单规则登记簿详细信息<span class="caption_title"> | <f:link href="/signCardMgr/signRuleReg/checkList.do">返回列表</f:link></span></caption>
			<tr>
				<td>签单规则ID</td>
				<td>${signRuleReg.signRuleId}</td>
				<td>签单规则名称</td>
				<td>${signRuleReg.signRuleName}</td>
				<td>购卡客户ID</td>
				<td>${signRuleReg.signCustId}</td>
			</tr>
			<tr>
				<td>购卡客户名称</td>
				<td>${signCust.signCustName}</td>
				<td>充值返利规则</td>
				<td>${rebateRule.rebateName}</td>
				<td>透支金额</td>
				<td>${fn:amount(signRuleReg.overdraft)}</td>
			</tr>
			<tr>
				<td>年费</td>
				<td>${fn:amount(signRuleReg.annuity)}</td>
				<td>年费减免方式</td>
				<td>${signRuleReg.derateTypeName}</td>
				<td>减免次数</td>
				<td>${signRuleReg.derateCount}</td>
			</tr>
			<tr>
				<td>减免金额</td>
				<td>${fn:amount(signRuleReg.derateAmt)}</td>
				<td>状态</td>
				<td>${signRuleReg.statusName}</td>
				<td>更新人</td>
				<td>${signRuleReg.updateUser}</td>
		  	</tr>
		  	<tr>
				<td>更新时间</td>
				<td><s:date name="signRuleReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>				
		  	</tr>
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
			<s:iterator value="rebateRuleDetailList"> 
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
			<jsp:param name="workflowId" value="<%=Constants.WORKFLOW_SIGN_RULE_REG%>"/>
			<jsp:param name="refId" value="${signRuleReg.signRuleId}"/>
		</jsp:include>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>