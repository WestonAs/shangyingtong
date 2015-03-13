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
			<caption>${ACT.name}<span class="caption_title"> | <f:link href="/riskMerch/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>商户号</td>
				<td>${riskMerch.merchId}</td>
			</tr>
			<tr>
		  		<td>商户名称</td>
				<td>${riskMerch.merchName}</td>
			</tr>
			<tr>
		  		<td>备注</td>
				<td>${riskMerch.remark}</td> 
			</tr>
			</tr>
		  		<td>更新用户名</td>
				<td>${riskMerch.updateBy}</td>
			</tr>
			<tr>
		  		<td>添加时间</td>
				<td><s:date name="riskMerch.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>