<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ page import="flink.util.Paginater"%>
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
			<caption>机构运行参数详细信息<span class="caption_title"> | <f:link href="/para/branchPara/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>参数代码</td>
				<td>${branchPara.paraCode}</td>
				<td>机构(商户)代码</td>
				<td>${branchPara.refCode}</td>
				<td>是否机构</td>
				<td><s:if test="branchPara.isBranch == Y">是</s:if><s:else>否</s:else></td>
		  	</tr>
		  	<tr>
		  		<td>参数名</td>
				<td>${branchPara.paraName}</td>
		  		<td>参数值</td>
				<td>${branchPara.paraValue}</td>
				<td>更新人</td>
				<td>${branchPara.updateUser}</td>
		  	</tr>
		  	<tr>
				<td>更新时间</td>
				<td colspan="5"><s:date name="branchPara.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>