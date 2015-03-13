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
			<caption>风险准备金变动详细信息<span class="caption_title"> | <f:link href="/cardRisk/cardRiskQuery/cardRiskChgList.do?cardRiskBalance.branchCode=${cardRiskChg.branchCode}&goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>变动编号</td>
				<td>${cardRiskChg.id}</td>
				<td>发卡机构</td>
				<td>${cardRiskChg.branchCode}-${fn:branch(cardRiskChg.branchCode)}</td>
			</tr>
			<tr>
				<td>调整类型</td>
				<td>${cardRiskChg.adjTypeName}</td>
				<td>相关金额</td>
				<td>${cardRiskChg.amt}</td>
			</tr>
			<tr>
				<td>可用充值售卡金额</td>
				<td>${cardRiskChg.availableAmt}</td>
				<td>参考ID</td>
				<td>${cardRiskChg.refid}</td>
		  	</tr>
		  	<tr>
				<td>变动日期</td>
				<td><s:date name="cardRiskChg.changeDate" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td></td>
				<td></td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>