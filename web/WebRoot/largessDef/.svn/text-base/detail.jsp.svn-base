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
		
		<!-- 赠品定义明细 -->
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>赠品定义详细信息<span class="caption_title"> | <f:link href="/largessDef/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>赠品编号</td>
				<td>${largessDef.largessId}</td>
				<td>赠品名称</td>
				<td>${largessDef.largessName}</td>
			</tr>
			<tr>
				<td>联名机构编号</td>
				<td>${largessDef.jinstId}</td>
				<td>联名机构名称</td>
				<td>${fn:branch(largessDef.jinstId)}${fn:merch(largessDef.jinstId)}</td>
			</tr>
			<tr>
				<td>赠送备注</td>
				<td>${largessDef.largessRule}</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>更新用户名</td>
				<td>${largessDef.updateBy}</td>
				<td>更新时间</td>
				<td><s:date name="largessDef.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr> 
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>