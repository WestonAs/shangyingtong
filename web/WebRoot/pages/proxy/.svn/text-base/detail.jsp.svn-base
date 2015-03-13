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
			<caption>代理关系详细信息<span class="caption_title"> | <f:link href="/pages/proxy/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>代理机构编号</td>
				<td>${branchProxy.proxyId}</td>
				<td>所属机构编号</td>
				<td>${branchProxy.respOrg}</td>
				<td>代理标志</td>
				<td>${branchProxy.proxyTypeName}</td>
		  	</tr>
		  	<tr>
				<td>状态</td>
				<td>${branchProxy.statusName}</td>
				<td>更新人</td>
				<td>${branchProxy.updateBy}</td>
				<td>更新时间</td>
				<td><s:date name="branchProxy.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>