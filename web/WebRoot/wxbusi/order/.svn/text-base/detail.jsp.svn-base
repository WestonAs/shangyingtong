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
			<caption>订单信息<span class="caption_title"> | <f:link href="/wxbusi/order/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>订单号</td>
				<td>${wxBillInfo.billNo}</td>
				<td>渠道机构号</td>
				<td>${wxBillInfo.fromNo}</td>
		  	</tr>
			<tr>
				<td>订单类型</td>
				<td>${wxBillInfo.billTypeName}</td>
				<td>收款人（收银员）手机号</td>
				<td>${wxBillInfo.casherExtCard}</td>
		  	</tr>
			<tr>
				<td>付款人卡号</td>
				<td>${wxBillInfo.payerCardId}</td>
				<td>收款人卡号</td>
				<td>${wxBillInfo.recvCardId}</td>
		  	</tr>
		  	<tr>
				<td>微信平台渠道机构号</td>
				<td>${wxBillInfo.payFromNo}</td>
				<td>外部标识</td>
				<td>${wxBillInfo.payExtFlag}</td>
		  	</tr>
		  	<tr>
				<td>订单金额</td>
				<td>${wxBillInfo.billAmount}</td>
				<td>实际扣款金额</td>
				<td>${wxBillInfo.realPayAmount}</td>
		  	</tr>
		  	<tr>
				<td>订单状态</td>
				<td>${wxBillInfo.statusName}</td>
				<td>交易流水</td>
				<td>${wxBillInfo.transSn}</td>
		  	</tr>
		  	<tr>
				<td>订单提交时间</td>
				<td><s:date name="wxBillInfo.billCommitTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>订单有效期</td>
				<td><s:date name="wxBillInfo.billExpirtDate" format="yyyy-MM-dd HH:mm:ss"/></td>
		  	</tr>
		  	<tr>
				<td>订单支付时间</td>
				<td><s:date name="wxBillInfo.billPayTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>订单退货时间</td>
				<td><s:date name="wxBillInfo.rtnGoodsTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		  	</tr>
		  	<tr>
				<td>订单登记时间</td>
				<td><s:date name="wxBillInfo.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>登记人</td>
				<td>${wxBillInfo.updateUser}</td>
		  	</tr>
		  	<tr>
				<td>订单摘要</td>
				<td>${wxBillInfo.billBrief}</td>
				<td>订单备注</td>
				<td>${wxBillInfo.billRemark}</td>
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