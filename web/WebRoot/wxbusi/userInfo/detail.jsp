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
			<caption>用户信息<span class="caption_title"> | <f:link href="/wxbusi/userInfo/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>用户编号</td>
				<td>${wxUserInfo.userId}</td>
				<td>用户姓名</td>
				<td>${wxUserInfo.userName}</td>
		  	</tr>
		  	<tr>
				<td>手机号</td>
				<td>${wxUserInfo.externalCardId}</td>
				<td>用户类型</td>
				<td>${wxUserInfo.userTypeName}</td>
		  	</tr>
		  	<tr>
				<td>证件类型</td>
				<td>${wxUserInfo.userCredTypeName}</td>
				<td>证件号码</td>
				<td>${wxUserInfo.credNo}</td>
		  	</tr>
		  	<tr>
				<td>联系地址</td>
				<td>${wxUserInfo.contactAddress}</td>
				<td>联系电话</td>
				<td>${wxUserInfo.contactPhone}</td>
		  	</tr>
		  	<tr>
				<td>电子邮件</td>
				<td>${wxUserInfo.email}</td>
				<td>注册时间</td>
				<td><s:date name="wxUserInfo.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		  	</tr>
		  	<tr>
		  		<td>注册渠道</td>
				<td>${wxUserInfo.accessNo}</td>
				<td>附言</td>
				<td>${wxUserInfo.remakr}</td>
		  	</tr>
		  	<tr>
		  		<td>商盈通卡号</td>
				<td>${wxUserInfo.cardId}</td>
				<td></td>
				<td></td>
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