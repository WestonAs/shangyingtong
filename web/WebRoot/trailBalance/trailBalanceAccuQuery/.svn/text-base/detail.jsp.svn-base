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
			<caption>${ACT.name}<span class="caption_title"> | <f:link href="/trailBalance/trailBalanceAccuQuery/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>试算日期</td>
				<td>${trailBalanceAccu.settDate}</td>
				<td>发卡机构</td>
				<td>${trailBalanceAccu.cardIssuer}-${fn:branch(trailBalanceAccu.cardIssuer)}</td>
			</tr>
			<tr>
				<td>次卡子类型</td>
				<td>${trailBalanceAccu.accuClass}</td>
				<td>消费交易金额</td>
				<td>${fn:amount(trailBalanceAccu.consumeAmt)}</td>
			</tr>
			<tr>
				<td>过期消费金额</td>
				<td>${fn:amount(trailBalanceAccu.expirAmt)}</td>
				<td>售卡金额</td>
				<td>${fn:amount(trailBalanceAccu.sellCardAmt)}</td>
			</tr>
			<tr>
				<td>刷卡充值金额</td>
				<td>${fn:amount(trailBalanceAccu.posRechargeAmt)}</td>
				<td>预售卡激活金额</td>
				<td>${fn:amount(trailBalanceAccu.activateSellCardAmt)}</td>
			</tr>
			<tr>
				<td>现金充值金额</td>
				<td>${fn:amount(trailBalanceAccu.rechargeAmt)}</td>
				<td>预充值激活金额</td>
				<td>${fn:amount(trailBalanceAccu.activateRechargeAmt)}</td>
			</tr>
			<tr>
				<td>补帐金额</td>
				<td>${fn:amount(trailBalanceAccu.retransAmt)}</td>
				<td>调帐金额</td>
				<td>${fn:amount(trailBalanceAccu.adjustAmt)}</td>
			</tr>
			<tr>
				<td>上一日次卡账户余额</td>
				<td>${fn:amount(trailBalanceAccu.lastBal)}</td>
				<td>当前次卡账户余额</td>
				<td>${fn:amount(trailBalanceAccu.curBal)}</td>
			</tr>
			<tr>
				<td>平衡标志</td>
				<td>${trailBalanceAccu.balanceFlagName}</td>
				<td>更新时间</td>
				<td><s:date name="trailBalanceAccu.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>