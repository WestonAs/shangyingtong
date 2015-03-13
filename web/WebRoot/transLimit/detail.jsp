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
			<caption>${ACT.name}<span class="caption_title"> | <f:link href="/transLimit/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>发卡机构编号</td>
				<td>${transLimit.cardIssuer}</td>
				<td>发卡机构名称</td>
				<td>${fn:branch(transLimit.cardIssuer)}</td>
		  	</tr>
		  	<tr>
				<td>商户号</td>
				<td>${transLimit.merNo}</td>
				<td>商户名称</td>
				<td>${fn:merch(transLimit.merNo)}</td>
			</tr>
			<tr>
				<td>卡BIN</td>
				<td>${transLimit.cardBin}</td>
				<td>状态</td>
				<td>${transLimit.statusName}</td>
		  	</tr>
			<tr>
				<td>交易类型</td>
				<td>${transLimit.transTypeName}</td>
				<td>更新用户名</td>
				<td>${transLimit.updateBy}</td>
			</tr>
			<tr>
		  		<td>更新时间</td>
				<td><s:date name="transLimit.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td></td>
				<td></td>
		  	<tr>
		  	</tr>
		</table>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>