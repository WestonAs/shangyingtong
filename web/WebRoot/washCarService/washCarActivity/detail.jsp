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
			<caption>洗车活动<span class="caption_title"> | <f:link href="/washCarService/washCarActivity/list.do?goBack=goBack')?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>发卡机构</td>
				<td>${washCarActivity.cardIssuer}</td>
				<td>商户</td>
				<td>${washCarActivity.merchId}</td>
			</tr>
			<tr>
				<td>活动编号</td>
				<td>${washCarActivity.activityId}</td>
				<td>活动名称</td>
				<td>${washCarActivity.activityName}</td>
			</tr>
			<tr>
				<td>洗车周期</td>
				<td>${washCarActivity.washCarCycleName}</td>
				<td>总次数</td>
				<td>${washCarActivity.totalNum}</td>
			</tr>		
			<tr>
				<td>限制每月次数</td>
				<td>${washCarActivity.limitMonthNum}</td>
				<td>当月不洗是否作废</td>
				<td>${washCarActivity.washTherInvalName}</td>
			</tr>
			<tr>
				<td>更新用户名</td>
				<td>${washCarActivity.updateUser}</td>
				<td>更新时间</td>
				<td><s:date name="washCarActivity.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr> 
			<tr>
			    <td>录入时间</td>
				<td><s:date name="washCarActivity.insertTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td>备注</td>
				<td>${washCarActivity.remark}</td>
			</tr> 
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>