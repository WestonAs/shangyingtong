<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ page import="flink.util.Paginater"%>
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
			<caption>系统日志详细信息<span class="caption_title"> | <f:link href="/log/syslog/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>日志编号</td>
				<td>${sysLog.id}</td>
				<td>机构编号</td>
				<td>${sysLog.branchNo}</td>
				<td>商户编号</td>
				<td>${sysLog.merchantNo}</td>
		  	</tr>
		  	<tr>
				<td>权限编号</td>
				<td>${sysLog.limitId}</td>
				<td>权限编号</td>
				<td>${sysLog.errorCode}</td>
				<td>日志类型</td>
				<td>${sysLog.logTypeName}</td>
		  	</tr>
		  	<tr>
				<td>记录时间</td>
				<td><s:date name="sysLog.logDate" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td>日志种类</td>
				<td>${sysLog.logClassName}</td>
				<td>建议处理方式</td>
				<td>${sysLog.approach}</td>
		  	</tr>
		  	<tr>
				<td>查看状态</td>
				<td>${sysLog.stateName}</td>
				<td>第一次查看者</td>
				<td>${sysLog.viewUser}</td>
				<td>第一次查看时间</td>
				<td><s:date name="sysLog.viewDate" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		  	<tr>
				<td>日志内容</td>
				<td colspan="5">${sysLog.content}</td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>