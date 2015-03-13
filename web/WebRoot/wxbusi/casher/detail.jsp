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
			<caption>收银员信息<span class="caption_title"> | <f:link href="/wxbusi/casher/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>机构/商户号</td>
				<td>${wxCashierInfo.merchNo}</td>
				<td>账号分户</td>
				<td>${wxCashierInfo.acctSubject}</td>
		  	</tr>
			<tr>
				<td>收银员用户标识</td>
				<td>${wxCashierInfo.cashierExtid}</td>
				<td>收银员姓名</td>
				<td>${wxCashierInfo.cashierName}</td>
		  	</tr>
		  	<tr>
				<td>创建时间</td>
				<td><s:date name="wxCashierInfo.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>状态</td>
				<td>${wxCashierInfo.statuName}</td>
		  	</tr>
		  	<tr>
				<td>权限</td>
				<td>${wxCashierInfo.permissionTypeName}</td>
				<td>备注</td>
				<td>${wxCashierInfo.remark}</td>
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