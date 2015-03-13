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
		<f:js src="/js/paginater.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div id="printArea" >
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>提现信息<span class="caption_title"> | <f:link href="/wxbusi/cashWithdraw/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>提现单号</td>
				<td>${wxWithdrawInfo.withdrawBillNo}</td>
				<td>交易流水号</td>
				<td>${wxWithdrawInfo.transSn}</td>
		  	</tr>
		  	<tr>
				<td>卡号</td>
				<td>${wxWithdrawInfo.cardId}</td>
				<td>外部卡号</td>
				<td>${wxWithdrawInfo.extCardId}</td>
		  	</tr>
		  	<tr>
				<td>订单金额</td>
				<td>${wxWithdrawInfo.billAmount}</td>
				<td>账户余额</td>
				<td>${wxWithdrawInfo.availBal}</td>
		  	</tr>
		  	<tr>
				<td>状态</td>
				<td>${wxWithdrawInfo.status}</td>
				<td>提现银行卡号</td>
				<td>${wxWithdrawInfo.bankAcct}</td>
		  	</tr>
		  	<tr>
				<td>提现银行行号</td>
				<td>${wxWithdrawInfo.bankCode}</td>
				<td>提现银行账户名</td>
				<td>${wxWithdrawInfo.bankAcctName}</td>
		  	</tr>
		  	<tr>
				<td>提现日期</td>
				<td><s:date name="wxWithdrawInfo.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>附言</td>
				<td>${wxWithdrawInfo.postScript}</td>
		  	</tr>
		</table>
		</div>
		</div>
		
		<div style="padding-left: 30px;margin: 0px;">
			<input type="button" value="打印" onclick="openPrint();"/>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>