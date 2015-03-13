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
		
		<!--分支机构运营手续费明细 -->
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>${ACT.name}<span class="caption_title"> | <f:link href="/releaseCardFee/cardIssuerFeeInfo/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>计费日期</td>
				<td colspan="3">${cardIssuerFeeDTotal.feeDate}</td>
			</tr>
				<td>分支机构</td>
				<td>${cardIssuerFeeDTotal.chlCode}-${fn:branch(cardIssuerFeeDTotal.chlCode)}</td>
				<td>分支机构货币符</td>
				<td>${cardIssuerFeeDTotal.chlCurCode}</td>
			</tr>
			<tr>
				<td>发卡机构</td>
				<td>${cardIssuerFeeDTotal.branchCode}-${fn:branch(cardIssuerFeeDTotal.branchCode)}</td>
				<td>发卡机构货币符</td>
				<td>${cardIssuerFeeDTotal.branchCurCode}</td>
			</tr>
			<tr>
				<td>交易类型</td>
				<td>${fn:branch(cardIssuerFeeDTotal.branchCode)}</td>
				<td>计费方式</td>
				<td>${cardIssuerFeeDTotal.feeTypeName}</td>
			</tr>
				<td>手续费费率</td>
				<td>${fn:merch(cardIssuerFeeDTotal.feeRate)}</td>
				<td>交易笔数</td>
				<td>${fn:amount(cardIssuerFeeDTotal.transNum)}</td>
			</tr>
			<tr>
				<td>交易金额</td>
				<td>${fn:amount(cardIssuerFeeDTotal.amount)}</td>
				<td>汇率</td>
				<td>${cardIssuerFeeDTotal.exchRate}</td>
			</tr>
			<tr>
				<td>发卡机构应付手续费</td>
				<td>${fn:amount(cardIssuerFeeDTotal.feeAmt)}</td>
				<td>分支机构应收手续费</td>
				<td>${fn:amount(cardIssuerFeeDTotal.chlFeeAmt)}</td>
			</tr>
			<tr>
				<td>运营代理</td>
				<td>${cardIssuerFeeDTotal.proxyId}-${fn:branch(cardIssuerFeeDTotal.proxyId)}</td>
				<td>运营代理分润金额</td>
				<td>${fn:amount(cardIssuerFeeDTotal.proxyFeeAmt)}</td>
			</tr>
			<tr>
				<td>更新用户名</td>
				<td>${cardIssuerFeeDTotal.updateBy}</td>
				<td>更新时间</td>
				<td><s:date name="cardIssuerFeeDTotal.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>