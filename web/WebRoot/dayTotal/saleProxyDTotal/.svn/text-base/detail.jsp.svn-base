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
			<caption>${ACT.name}<span class="caption_title"> | <f:link href="/dayTotal/saleProxyDTotal/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>清算日期</td>
				<td>${saleProxyRtnDTotal.feeDate}</td>
				<td>卡Bin</td>
				<td>${saleProxyRtnDTotal.cardBin}</td>
			</tr>
			<tr>
				<td>机构编号</td>
				<td>${saleProxyRtnDTotal.orgId}</td>
				<td>机构名称</td>
				<td>${saleProxyRtnDTotal.orgName}</td>
			</tr>
			<tr>
				<td>代理机构编号</td>
				<td>${saleProxyRtnDTotal.proxyId}</td>
				<td>代理机构名称</td>
				<td>${saleProxyRtnDTotal.proxyName}</td>
			</tr>
			<tr>
				<td>售卡笔数</td>
				<td>${saleProxyRtnDTotal.saleNum}</td>
				<td>售卡金额</td>
				<td>${fn:amount(saleProxyRtnDTotal.saleAmt)}</td>
			</tr>
			<tr>
				<td>售卡返利比例</td>
				<td>${saleProxyRtnDTotal.feeRateSale}</td>
				<td>售卡返利金额</td>
				<td>${fn:amount(saleProxyRtnDTotal.shareAmtSale)}</td>
			</tr>
			<tr>
				<td>充值笔数</td>
				<td>${saleProxyRtnDTotal.suffNum}</td>
				<td>充值总金额</td>
				<td>${fn:amount(saleProxyRtnDTotal.suffAmt)}</td>
			</tr>
			<tr>
				<td>充值返利比例</td>
				<td>${saleProxyRtnDTotal.feeRateSuff}</td>
				<td>充值返利金额</td>
				<td>${fn:amount(saleProxyRtnDTotal.shareAmtSuff)}</td>
			</tr>
			<tr>
				<td>货币符</td>
				<td>${saleProxyRtnDTotal.curCode}</td>
				<td>充值售卡返利</td>
				<td>${fn:amount(shareAmt)}</td>
			</tr>
			<tr>
				<td>更新用户名</td>
				<td>${saleProxyRtnDTotal.updateBy}</td>
				<td>更新时间</td>
				<td><s:date name="saleProxyRtnDTotal.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>