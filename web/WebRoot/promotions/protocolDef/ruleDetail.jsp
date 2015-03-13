<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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
			<caption>协议积分规则详细信息<span class="caption_title"> | <f:link href="/promotions/protocolDef/ruleList.do?promtRuleDef.promtId=${promtRuleDef.promtId}">返回列表</f:link></span></caption>
			<tr>
				<td>协议积分规则ID</td>
				<td>${promtRuleDef.promtRuleId}</td>
				<td>协议积分活动ID</td>
				<td>${promtRuleDef.promtId}</td>
		  	</tr>
		  	<tr>
				<td>金额类型</td>
				<td>${promtRuleDef.amtTypeName}</td>
				<td>参考金额</td>
				<td>${fn:amount(promtRuleDef.amtRef)}</td>
		  	</tr>
		  	<tr>
				<td>规则类型</td>
				<td colspan="3">${promtRuleDef.promtRuleTypeName}</td>
		  	</tr>
		  	<tr>
				<td>金额参数1</td>
				<td>${fn:amount(promtRuleDef.ruleParam1)}</td>
				<td>金额参数2</td>
				<td>${fn:amount(promtRuleDef.ruleParam2)}</td>
		  	</tr>
		  	<tr>
				<td>倍率参数</td>
				<td>${fn:amount(promtRuleDef.ruleParam3)}</td>
				<td>积分参数</td>
				<td>${promtRuleDef.ruleParam4}</td>
		  	</tr>
		  	<tr>
				<td>积分子类型</td>
				<td>${promtRuleDef.ruleParam5}</td>
				<td>规则状态</td>
				<td>${promtRuleDef.ruleStatusName}</td>
		  	</tr>
		</table>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>