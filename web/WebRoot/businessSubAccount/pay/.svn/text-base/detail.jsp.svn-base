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
			<caption>支付详细信息<span class="caption_title"> | <f:link href="/businessSubAccount/pay/list.do">返回列表</f:link></span></caption>
			<tr>
				<td width="15%">付款虚户账号</td>
				<td width="18%">${payBill.payerAccountId}</td>
				<td width="15%">付款客户编号</td>
				<td width="18%">${payBill.payerCustId}</td> 
				<td width="15%">付款客户名称</td>
				<td width="18%">${payBill.payerCustName}</td>
		  	</tr>
		  	<tr>
				<td>收款账户行别</td>
				<td>${payBill.payeeBankTypeName}</td>
				<td>收款银行账号</td>
				<td>${payBill.payeeAcctNo}</td> 
				<td>收款银行户名</td>
				<td>${payBill.payeeAcctName}</td>
		  	</tr>  
		  	<tr>
				<td>支付金额</td>
				<td>${fn:amount(payBill.amount)}</td>
				<td>手续费</td>
				<td>${fn:amount(payBill.fee)}</td>
		  		<td>状态</td>
				<td>${payBill.stateName}</td>
		  	</tr> 
		  	<tr>
		  	    <td>创建时间</td>
				<td><s:date name="payBill.createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		  	    <td>审核时间</td>
				<td><s:date name="payBill.checkTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		  	    <td>支付完成时间</td>
				<td><s:date name="payBill.finishTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		  	</tr>
		  	<tr>
		  		<td>备注</td>
				<td colspan="5">${payBill.remark}</td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>