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
			<caption>${ACT.name}<span class="caption_title"> | <f:link href="/expireCard/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>卡号</td>
				<td>${cardInfo.cardId}</td>
				<td>账号</td>
				<td>${cardInfo.acctId}</td>
			</tr>
			<tr>
				<td>卡类型</td>
				<td>${cardTypeCode.cardTypeName}</td>
				<td>卡子类</td>
				<td>${cardInfo.cardSubclass}</td>
			</tr>
			<tr>
				<td>发卡机构</td>
				<td>${fn:branch(cardInfo.cardIssuer)}</td>
				<td>卡BIN</td>
				<td>${cardBin}</td>
			</tr>
			<tr>
				<td>领卡机构</td>
				<td>${fn:branch(cardInfo.appOrgId)}</td>
				<td>领卡日期</td>
				<td>${cardInfo.appDate}</td>
			</tr>
			<tr>
				<td>售卡机构</td>
				<td>${fn:branch(cardInfo.saleOrgId)}</td>
				<td>售卡日期</td>
				<td>${cardInfo.saleDate}</td>
			</tr>
			<tr>
				<td>交易总金额</td>
				<td>${fn:amount(cardInfo.amount)}</td>
				<td>卡状态</td>
				<td>${cardInfo.cardStatusName}</td>
			</tr>
			<tr>
				<td>生效日期</td>
				<td>${cardInfo.effDate}</td>
				<td>失效日期</td>
				<td>${cardInfo.expirDate}</td>
			</tr>
		</table>
		</div>

		<!-- 子账户数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<caption>相关子账户详细信息</caption>	
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">子账户类型</td>
			   <td align="center" nowrap class="titlebg">可用余额</td>
			   <td align="center" nowrap class="titlebg">冻结金额</td>
			   <td align="center" nowrap class="titlebg">生效日期</td>
			   <td align="center" nowrap class="titlebg">失效日期</td>
			</tr>
			</thead>
		
			<s:iterator value="page.data"> 
			<tr>		
			  <td align="center" nowrap>${subacctTypeName}</td>
			  <td align="right" nowrap>${fn:amount(avlbBal)}</td>
			  <td align="right" nowrap>${fn:amount(fznAmt)}</td>
			  <td align="center" nowrap>${effDate}</td>
			  <td align="center" nowrap>${expirDate}</td>			  
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>