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
			<caption>单机产品卡样详细信息<span class="caption_title"> | <f:link href="/pages/singleProduct/freeNum/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>终端编号</td>
				<td>${cardissuerFreeNum.termId}</td>
				<td>周期</td>
				<td>第${cardissuerFreeNum.period}年</td>
		  	</tr>
		  	<tr>
				<td>发卡机构编号</td>
				<td>${cardissuerFreeNum.issId}</td>
				<td>发卡机构名称</td>
				<td>${fn:branch(cardissuerFreeNum.issId)}</td>
		  	</tr>
		  	<tr>
				<td>每台终端赠送卡数</td>
				<td>${cardissuerFreeNum.sendNum}</td>
				<td>已用免费制卡数量</td>
				<td>${cardissuerFreeNum.usedNum}</td>
		  	</tr>
		  	<tr>
				<td>状态</td>
				<td>${cardissuerFreeNum.statusName}</td>
				<td>更新人</td>
				<td>${cardissuerFreeNum.updateBy}</td>
		  	</tr>
		  	<tr>
				<td>更新时间</td>
				<td colspan="3"><s:date name="cardissuerFreeNum.updateTime" format="yyyy-MM-dd hh:mm:ss"/></td>
		  	</tr>
		</table>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>