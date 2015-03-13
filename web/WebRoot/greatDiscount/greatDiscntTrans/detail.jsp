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
			<caption>高级折扣交易详细信息<span class="caption_title"> | <f:link href="/greatDiscount/greatDiscntTrans/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>原交易流水</td>
				<td>${greatDiscntTrans.transSn}</td>
				<td>卡号</td>
				<td>${greatDiscntTrans.cardId}</td>
			</tr>
			<tr>
				<td>交易金额</td>
				<td>${greatDiscntTrans.transAmt}</td>
				<td>清算金额</td>
				<td>${greatDiscntTrans.settAmt}</td>
			</tr>
			<tr>
				<td>原交易类型</td>
				<td>${greatDiscntTrans.transTypeName}</td>
				<td>交易清算日期</td>
				<td>${greatDiscntTrans.settDate}</td>
			</tr>
			<tr>
				<td>发卡机构</td>
				<td>${greatDiscntTrans.cardIssuer}-${fn:branch(greatDiscntTrans.cardIssuer)}</td>
				<td>商户</td>
				<td>${greatDiscntTrans.merchNo}-${fn:merch(greatDiscntTrans.merchNo)}</td>
			</tr>
			<tr>
				<td>折扣协议号</td>
				<td>${greatDiscntTrans.greatDiscntProtclId}</td>
				<td>支付方式</td>
				<td>${greatDiscntTrans.payWayName}</td>
			</tr>
			<tr>
				<td>事后返还持卡人金额</td>
				<td>${greatDiscntTrans.issuerRepaidHolderAmt}</td>
				<td>事后商户返还发卡机构金额</td>
				<td>${greatDiscntTrans.merchRepaidIssuerAmt}</td>
			</tr>
			<tr>
				<td>系统自动返还标志</td>
				<td>${greatDiscntTrans.payStatusName}</td>
				<td>返还日期</td>
				<td>${greatDiscntTrans.payDate}</td>
			</tr>
			<tr>
				<td>插入时间</td>
				<td><s:date name="greatDiscntTrans.insertTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td>更新时间</td>
				<td><s:date name="greatDiscntTrans.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<td>备注</td>
				<td>${greatDiscntTrans.remark}</td>
				<td></td>
				<td></td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>