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
		
		<!-- 签单规则明细 -->
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>签单规则细信息<span class="caption_title"> | <f:link href="/signCardMgr/signRuleReg/list.do">返回列表</f:link></span></caption>
			<tr>
				<td>签单规则ID</td>
				<td>${signRuleReg.signRuleId}</td>
				<td>签单规则名称</td>
				<td>${signRuleReg.signRuleName}</td>
				<td>发卡机构</td>
				<td>${signCust.cardBranch }</td>
			</tr>
			<tr>
				<td>签单客户ID</td>
				<td>${signRuleReg.signCustId}</td>
				<td>签单客户名称</td>
				<td>${signCust.signCustName}</td>
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
				<td>备注</td>
				<td>${remark }</td>
		  	</tr>
		  	<tr>
				<td>更新人</td>
				<td>${signRuleReg.updateUser}</td>
				<td>更新时间</td>
				<td><s:date name="signRuleReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td></td>
				<td></td>				
		  	</tr>
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