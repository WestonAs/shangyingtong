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
			<caption>参数详细信息<span class="caption_title"> | <f:link href="/para/sysparm/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>参数代码</td>
				<td>${sysparm.paraCode}</td>
				<td>参数名称</td>
				<td>${sysparm.paraName}</td>
				<td>参数值</td>
				<td>${sysparm.paraValue}</td>
		  	</tr>
		  	<tr>
				<td>是否显示</td>
				<td><s:if test="sysparm.showFlag == 1">是</s:if><s:else>否</s:else></td>
				<td>是否修改</td>
				<td><s:if test="sysparm.modifyFlag == 1">是</s:if><s:else>否</s:else></td>
				<td>更新人</td>
				<td>${sysparm.updateUser}</td>
		  	</tr>
		  	<tr>
				<td>更新时间</td>
				<td><s:date name="sysparm.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td>备注</td>
				<td colspan="3">${sysparm.note}</td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>