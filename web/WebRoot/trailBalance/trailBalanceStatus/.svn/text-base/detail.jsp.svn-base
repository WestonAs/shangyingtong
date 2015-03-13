<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base target="_self" />
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
			<caption>${ACT.name} <span class="caption_title"> | <a href="list.do?goBack=goBack">返回列表</a></span></caption>
			<tr>
				<td>试算日期</td>
				<td>${trailBalanceStatus.settDate}</td>
				<td>发卡机构</td>
				<td>${trailBalanceStatus.cardIssuer}-${fn:branch(trailBalanceStatus.cardIssuer)}</td>
			</tr>
			<tr>
				<td>平衡标志</td>
				<td>${trailBalanceStatus.balanceFlagName}</td>
				<td>资金账户平衡标志</td>
				<td>${trailBalanceStatus.subAcctBalanceFlagName}</td>
			</tr>
			<tr>
				<td>次卡账户平衡标志</td>
				<td>${trailBalanceStatus.accuBalanceFlagName}</td>
				<td>积分账户平衡标志</td>
				<td>${trailBalanceStatus.pointBalanceFlagName}</td>
			</tr>
			<tr>
				<td>赠券账户平衡标志</td>
				<td>${trailBalanceStatus.couponBalanceFlagName}</td>
				<td>更新时间</td>
				<td><s:date name="trailBalanceStatus.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
		</table>
		</div>
		
		<!-- 资金账户试算平衡明细 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<caption>相关资金账户试算平衡明细</caption>	
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">消费交易金额</td>
			   <td align="center" nowrap class="titlebg">过期消费金额</td>
			   <td align="center" nowrap class="titlebg">售卡金额</td>
			   <td align="center" nowrap class="titlebg">刷卡充值金额</td>
			   <td align="center" nowrap class="titlebg">预售卡激活金额</td>
			   <td align="center" nowrap class="titlebg">现金充值金额</td>
			   <td align="center" nowrap class="titlebg">预充值激活金额</td>
			   <td align="center" nowrap class="titlebg">派赠券卡金额</td>
			   <td align="center" nowrap class="titlebg">退货金额</td>
			   <td align="center" nowrap class="titlebg">积分返利金额</td>
			   <td align="center" nowrap class="titlebg">补账金额</td>
			   <td align="center" nowrap class="titlebg">调账金额</td>
			   <td align="center" nowrap class="titlebg">上一日子账户余额</td>
			   <td align="center" nowrap class="titlebg">当前子账户余额</td>
			   <td align="center" nowrap class="titlebg">平衡标志</td>
			</tr>
			</thead>
			
			<s:iterator value="subAcctPage.data"> 
			<tr>		
			  <td align="right" nowrap>${fn:amount(consumeAmt)}</td>
			  <td align="right" nowrap>${fn:amount(expirAmt)}</td>			  
			  <td align="right" nowrap>${fn:amount(sellCardAmt)}</td>		
			  <td align="right" nowrap>${fn:amount(posRechargeAmt)}</td>		
			  <td align="right" nowrap>${fn:amount(activateSellCardAmt)}</td>		
			  <td align="right" nowrap>${fn:amount(rechargeAmt)}</td>		
			  <td align="right" nowrap>${fn:amount(activateRechargeAmt)}</td>		
			  <td align="right" nowrap>${fn:amount(sendCouponCardAmt)}</td>		
			  <td align="right" nowrap>${fn:amount(returnGoodAmt)}</td>		
			  <td align="right" nowrap>${fn:amount(pointRebateAmt)}</td>		
			  <td align="right" nowrap>${fn:amount(retransAmt)}</td>		
			  <td align="right" nowrap>${fn:amount(adjustAmt)}</td>		
			  <td align="right" nowrap>${fn:amount(lastBal)}</td>		
			  <td align="right" nowrap>${fn:amount(curBal)}</td>		
			  <td align="center" nowrap>${balanceFlagName}</td>  
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="subAcctPage"/>
		</div>
		
		<!-- 次卡账户试算平衡明细数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<caption>次卡账户试算平衡明细</caption>	
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">次卡子类型</td>
			   <td align="center" nowrap class="titlebg">消费交易金额</td>
			   <td align="center" nowrap class="titlebg">过期消费金额</td>
			   <td align="center" nowrap class="titlebg">售卡金额</td>
			   <td align="center" nowrap class="titlebg">刷卡充值金额</td>
			   <td align="center" nowrap class="titlebg">预售卡激活金额</td>
			   <td align="center" nowrap class="titlebg">现金充值金额</td>
			   <td align="center" nowrap class="titlebg">预充值激活金额</td>
			   <td align="center" nowrap class="titlebg">上一日次卡账户余额</td>
			   <td align="center" nowrap class="titlebg">当前次卡账户余额</td>
			   <td align="center" nowrap class="titlebg">平衡标志</td>
			</tr>
			</thead>
		
			<s:iterator value="accuPage.data"> 
			<tr>		
			  <td align="center" nowrap>${accuClass}</td>
			  <td align="right" nowrap>${fn:amount(consumeAmt)}</td>
			  <td align="right" nowrap>${fn:amount(expirAmt)}</td>			  
			  <td align="right" nowrap>${fn:amount(sellCardAmt)}</td>		
			  <td align="right" nowrap>${fn:amount(posRechargeAmt)}</td>		
			  <td align="right" nowrap>${fn:amount(activateSellCardAmt)}</td>		
			  <td align="right" nowrap>${fn:amount(rechargeAmt)}</td>		
			  <td align="right" nowrap>${fn:amount(activateRechargeAmt)}</td>	
			  <td align="right" nowrap>${fn:amount(lastBal)}</td>		
			  <td align="right" nowrap>${fn:amount(curBal)}</td>		
			  <td align="center" nowrap>${balanceFlagName}</td>  
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="accuPage"/>
		</div>
		<!-- 赠券账户试算平衡明细数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<caption>赠券账户试算平衡明细</caption>	
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">赠券子类型</td>
			   <td align="center" nowrap class="titlebg">发行机构</td>
			   <td align="center" nowrap class="titlebg">发行机构类型</td>
			   <td align="center" nowrap class="titlebg">变动金额</td>
			   <td align="center" nowrap class="titlebg">上一日赠券账户余额</td>
			   <td align="center" nowrap class="titlebg">当前赠券账户余额</td>
			   <td align="center" nowrap class="titlebg">平衡标志</td>
			</tr>
			</thead>
		
			<s:iterator value="couponPage.data"> 
			<tr>		
			  <td align="center" nowrap>${couponClass}</td>
			  <td align="center" nowrap>${issId}-${fn:branch(issId)}${fn:merch(issId)}</td>
			  <td align="center" nowrap>${issTypeName}</td>
			  <td align="right" nowrap>${fn:amount(deltaAmt)}</td>		
			  <td align="right" nowrap>${fn:amount(lastBal)}</td>		
			  <td align="right" nowrap>${fn:amount(curBal)}</td>		
			  <td align="center" nowrap>${balanceFlagName}</td>  
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="couponPage"/>
		</div>
		<!-- 积分账户试算平衡明细数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<caption>积分账户试算平衡明细</caption>	
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">积分子类型</td>
			   <td align="center" nowrap class="titlebg">变动金额</td>
			   <td align="center" nowrap class="titlebg">上一日积分账户余额</td>
			   <td align="center" nowrap class="titlebg">当前积分账户余额</td>
			   <td align="center" nowrap class="titlebg">平衡标志</td>
			</tr>
			</thead>
		
			<s:iterator value="pointPage.data"> 
			<tr>		
			  <td align="center" nowrap>${ptClass}</td>
			  <td align="right" nowrap>${fn:amount(deltaAmt)}</td>
			  <td align="right" nowrap>${fn:amount(lastBal)}</td>		
			  <td align="right" nowrap>${fn:amount(curBal)}</td>		
			  <td align="center" nowrap>${balanceFlagName}</td>  
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="pointPage"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>