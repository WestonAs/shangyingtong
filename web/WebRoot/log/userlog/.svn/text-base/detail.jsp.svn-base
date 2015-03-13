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
			<caption>用户日志详细信息<span class="caption_title"> | <f:link href="/log/userlog/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>日志编号</td>
				<td>${userLog.id}</td>
				<td>用户编号</td>
				<td>${userLog.userId}</td>
				<td>机构编号</td>
				<td>${userLog.branchNo}</td>
		  	</tr>
		  	<tr>
				<td>商户编号</td>
				<td>${userLog.merchantNo}</td>
				<td>权限编号</td>
				<td>${userLog.limitId}</td>
				<td>登录IP</td>
				<td>${userLog.loginIp}</td>
		  	</tr>
		  	<tr>
				<td>记录时间</td>
				<td><s:date name="userLog.logDate" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td>日志类型</td>
				<td>${userLog.logTypeName}</td>
				<td>建议处理方式</td>
				<td>${userLog.approach}</td>
		  	</tr>
		  	<tr>
				<td>日志内容</td>
				<td colspan="5">${userLog.content}</td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>