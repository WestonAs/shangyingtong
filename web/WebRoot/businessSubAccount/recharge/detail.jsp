<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ page import="flink.util.Paginater"%>
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
			<caption>充值单详细信息<span class="caption_title"> | <f:link href="/businessSubAccount/recharge/list.do">返回列表</f:link></span></caption>
			<tr>
				<td>虚户账号</td>
				<td>${rechargeBill.no}</td>
				<td>客户编号</td>
				<td>${rechargeBill.custId}</td> 
				<td>客户名称</td>
				<td>${rechargeBill.custName}</td>
		  	</tr>
		  	<tr>
				<td>付款账户开户行</td>
				<td>${rechargeBill.bankName}</td>
				<td>付款账号</td>
				<td>${rechargeBill.bankCardNo}</td> 
				<td>付款户名</td>
				<td>${rechargeBill.bankCardName}</td>
		  	</tr>  
		  	<tr>
				<td>充值金额</td>
				<td>${fn:amount(rechargeBill.amount)}</td>
				<td>收款账号</td>
				<td>${rechargeBill.recAcctNo}</td> 
				<td>收款户名</td>
				<td>${rechargeBill.recAcctName}</td>
		  	</tr> 
		  	<tr>
		  		<td>充值单状态</td>
				<td>${rechargeBill.stateName}</td>
				<td>到账时间</td>
				<td><s:date name="rechargeBill.remitTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>凭证号</td>
				<td>${rechargeBill.voucherNo}</td> 
		  	</tr> 
		  	<tr>
		  		<td>备注</td>
				<td colspan="5">${rechargeBill.note}</td>
		  	</tr> 
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>