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
			<caption>便民充值缴费信息<span class="caption_title"> | <f:link href="/wxbusi/payDeposit/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>缴费充值单号</td>
				<td>${wxPayDepositInfo.chargeBillNo}</td>
				<td>虚拟账号</td>
				<td>${wxPayDepositInfo.cardId}</td>
		  	</tr>
			<tr>
				<td>付款帐号</td>
				<td>${wxPayDepositInfo.payBankAcct}</td>
				<td>服务号</td>
				<td>${wxPayDepositInfo.providerNo}</td>
		  	</tr>
		  	<tr>
				<td>充值金额</td>
				<td>${fn:amount(wxPayDepositInfo.depositAmount)}</td>
				<td>实际扣款金额</td>
				<td>${fn:amount(wxPayDepositInfo.settAmount)}</td>
		  	</tr>
		  	<tr>
				<td>交易流水号</td>
				<td>${wxPayDepositInfo.transSn}</td>
				<td>状态</td>
				<td>${wxPayDepositInfo.statuName}</td>
		  	</tr>
		  	<tr>
				<td>交易流水号</td>
				<td>${wxPayDepositInfo.transSn}</td>
				<td>状态</td>
				<td>${wxPayDepositInfo.statuName}</td>
		  	</tr>
		  	<tr>
				<td>便民充值时间</td>
				<td><s:date name="wxPayDepositInfo.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>登记人</td>
				<td>${wxPayDepositInfo.updateUser}</td>
		  	</tr>
		  	<tr>
				<td>附言</td>
				<td colspan = "3">${wxPayDepositInfo.postScript}</td>
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