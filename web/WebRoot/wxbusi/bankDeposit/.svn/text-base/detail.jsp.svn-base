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
			<caption>银行卡充值信息<span class="caption_title"> | <f:link href="/wxbusi/bankDeposit/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>流水号</td>
				<td>${wxBankDepositReg.depositId}</td>
				<td>来源标志</td>
				<td>${wxBankDepositReg.wsSn}</td>
		  	</tr>
		  	<tr>
				<td>渠道机构编号</td>
				<td>${wxBankDepositReg.accessNo}</td>
				<td>卡号</td>
				<td>${wxBankDepositReg.cardId}</td>
		  	</tr>
		  	<tr>
				<td>充值金额</td>
				<td>${wxBankDepositReg.depositAmount}</td>
				<td>实际充值金额</td>
				<td>${wxBankDepositReg.realDepositAmount}</td>
		  	</tr>
		  	<tr>
				<td>机构/商户号</td>
				<td>${wxBankDepositReg.insId}</td>
				<td>状态</td>
				<td>${wxBankDepositReg.statusName}</td>
		  	</tr>
		  	<tr>
				<td>银行卡账号</td>
				<td>${wxBankDepositReg.bankAcct}</td>
				<td>充值日期</td>
				<td><s:date name="wxBankDepositReg.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		  	</tr>
		  	<tr>
				<td>清算金额</td>
				<td>${wxBankDepositReg.settAmt}</td>
				<td>清算日期</td>
				<td><s:date name="wxBankDepositReg.settDate" format="yyyy-MM-dd HH:mm:ss"/></td>
		  	</tr>
		  	<tr>
				<td>附言</td>
				<td colspan = "3" >${wxBankDepositReg.postScript}</td>
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