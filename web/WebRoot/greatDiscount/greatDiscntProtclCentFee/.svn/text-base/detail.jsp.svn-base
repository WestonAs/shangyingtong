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
			<caption>高级折扣中心费率详细信息<span class="caption_title"> | <f:link href="/greatDiscount/greatDiscntProtclCentFee/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>折扣协议号</td>
				<td>${greatDiscntProtclCentFee.greatDiscntProtclNo}</td>
				<td>折扣协议名</td>
				<td>${greatDiscntProtclCentFee.greatDiscntProtclName}</td>

			</tr>
			<tr>
				<td>联名发卡机构</td>
				<td>${greatDiscntProtclCentFee.cardIssuer}-${fn:branch(greatDiscntProtclCentFee.cardIssuer)}</td>
				<td>运营机构</td>
				<td>${greatDiscntProtclCentFee.chlCode}-${fn:branch(greatDiscntProtclCentFee.chlCode)}</td>

			</tr>
			<tr>
				<td>运营中心收益</td>
				<td>${fn:perDecimal(greatDiscntProtclCentFee.centIncomeFee)}</td>
				<td>插入时间</td>
				<td><s:date name="greatDiscntProtclCentFee.insertTime" format="yyyy-MM-dd HH:mm:ss" /></td>

			</tr>
			<tr>
				<td>更新时间</td>
				<td><s:date name="greatDiscntProtclCentFee.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td>备注</td>
				<td>${greatDiscntProtclCentFee.remark}</td>

			</tr>

		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>