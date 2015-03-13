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
			<caption>商户详细信息<span class="caption_title"> | <f:link href="/pages/cardMerchGroup/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>商户组编号</td>
				<td>${cardMerchGroup.groupId}</td>
				<td>商户组名称</td>
				<td>${cardMerchGroup.groupName}</td>
		  	</tr>
		  	<tr>
				<td>发卡机构编号</td>
				<td>${cardMerchGroup.branchCode}</td>
				<td>手续费计算方式</td>
				<td>${cardMerchGroup.feeTypeName }</td>
			</tr>
			<tr>
				<td>更新人</td>
				<td>${cardMerchGroup.updateBy}</td>
				<td>更新时间</td>
				<td><s:date name="cardMerchGroup.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		  	<tr>
				<td>拥有商户编号</td>
				<td colspan="5">${merchants}</td>
		  	</tr>
		  	<tr>
				<td>拥有商户名</td>
				<td colspan="5">${merchNames}</td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>