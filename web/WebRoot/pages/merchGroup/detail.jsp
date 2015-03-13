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
			<caption>商户详细信息<span class="caption_title"> | <f:link href="/pages/merchGroup/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>商圈编号</td>
				<td>${merchGroup.groupId}</td>
				<td>商圈名称</td>
				<td>${merchGroup.groupName}</td>
		  	</tr>
		  	<tr>
				<td>管理机构</td>
				<td>${merchGroup.manageBranch}-${fn:branch(merchGroup.manageBranch)}</td>
				<td>发卡机构</td>
				<td>${merchGroup.cardIssuer}-${fn:branch(merchGroup.cardIssuer)}</td>
		  	</tr>
		  	<tr>
				<td>状态</td>
				<td>${merchGroup.statusName}</td>
				<td>入库时间</td>
				<td><s:date name="merchGroup.insertTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		  	<tr>
				<td>更新人</td>
				<td>${merchGroup.updateBy}</td>
				<td>更新时间</td>
				<td><s:date name="merchGroup.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		  	<tr>
				<td>商圈拥有商户</td>
				<td colspan="5">${merchNames}</td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>