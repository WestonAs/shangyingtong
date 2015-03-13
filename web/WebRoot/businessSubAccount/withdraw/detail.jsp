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
			<caption>提现单详细信息<span class="caption_title"> | <f:link href="/businessSubAccount/withdraw/list.do">返回列表</f:link></span></caption>
			<tr>
				<td width="15%">虚户账号</td>
				<td width="18%">${withDrawBill.accountId}</td>
				<td width="15%">客户编号</td>
				<td width="18%">${withDrawBill.custId}</td> 
				<td width="15%">客户名称</td>
				<td width="18%">${withDrawBill.custName}</td>
		  	</tr>
		  	<tr>
				<td>收款账户开户行</td>
				<td>${withDrawBill.bankName}</td>
				<td>收款账号</td>
				<td>${withDrawBill.bankCardNo}</td> 
				<td>收款户名</td>
				<td>${withDrawBill.bankCardName}</td>
		  	</tr>  
		  	<tr>
				<td>提现金额</td>
				<td>${fn:amount(withDrawBill.amount)}</td>
				<td>手续费</td>
				<td>${fn:amount(withDrawBill.fee)}</td>
		  		<td>提现单状态</td>
				<td>${withDrawBill.stateName}</td>
			</tr>
			<tr>
			    <td>创建时间</td>
				<td><s:date name="withDrawBill.createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>审核时间</td>
				<td><s:date name="withDrawBill.checkTime" format="yyyy-MM-dd HH:mm:ss"/></td>	
				<td>完成时间</td>
				<td><s:date name="withDrawBill.finishTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		  	</tr>
		  	<tr>
		  		<td>备注</td>
				<td colspan="5">${withDrawBill.remark}</td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>