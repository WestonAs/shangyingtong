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
			<caption>预制卡详细信息<span class="caption_title"> | <f:link href="/preCard/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>卡号</td>
				<td>${cardInfo.cardId}</td>
				<td>发卡机构</td>
				<td>${fn:branch(cardInfo.cardIssuer)}</td>
			</tr>
			<tr>
				<td>卡种类</td>
				<td>${cardInfo.cardClassName}</td>
				<td>卡子类</td>
				<td>${cardInfo.cardSubclass}</td>
			</tr>
			<tr>
				<td>卡子类型</td>
				<td>${cardInfo.cardSubclass}</td>
				<td>批次号</td>
				<td>${cardInfo.batchNo}</td>
		  	</tr>
		  	<tr>
				<td>领卡机构编号</td>
				<td>${cardInfo.appOrgId}</td>
				<td>领卡机构名称</td>
				<td>${fn:branch(cardInfo.appOrgId)}</td>
			</tr>
			<tr>
				<td>建档日期</td>
				<td>${cardInfo.createDate}</td>
				<td>卡状态</td>
				<td>${cardInfo.cardStatusName}</td>
			</tr>
			<tr>
				<td>更新用户名</td>
				<td>${cardInfo.updateBy}</td>
				<td>更新时间</td>
				<td><s:date name="cardInfo.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>

