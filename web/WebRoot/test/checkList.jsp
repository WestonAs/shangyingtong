<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ page import="flink.util.Paginater"%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		
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

		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
				<td><input type="checkbox" onclick="FormUtils.selectAll(this, 'ids')" />全选</td>
				<td align="center" nowrap class="titlebg">编号</td>
				<td align="center" nowrap class="titlebg">名称</td>
				<td align="center" nowrap class="titlebg">时间</td>
				<td align="center" nowrap class="titlebg">类型</td>
			</tr>
			</thead>
			<s:iterator value="list">
			<tr>
				<td><s:checkbox name="ids" value="${element.id}"/></td>
				<td nowrap>${id}</td>
				<td align="center" nowrap>${name}</td>
				<td align="center" nowrap>${leavetime}</td>
				<td align="center" nowrap><s:if test="state==0">待审核</s:if><s:if test="state==1">已审核</s:if><s:if test="state==2">审核不通过</s:if></td>
			</tr>
			</s:iterator>
			</table>
		</div>
		
		<f:workflow adapter="TestAdapter" returnUrl=""><s:token/></f:workflow>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>