<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="gnete.etc.WorkflowConstants"%>
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
			<caption>运营机构卡BIN明细<span class="caption_title"> | <f:link href="/pages/cardBinMgr/cardBinFenzhi/fenzhiBinList.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>卡BIN</td>
				<td>${fenZhiCardBinMgr.cardBin}</td>
				<td>卡BIN前三位</td>
				<td>${fenZhiCardBinMgr.cardBinPrex}</td>
		  	</tr>
			<tr>
				<td>当前所属运营机构</td>
				<td>${fenZhiCardBinMgr.currentBranch}-${fn:branch(fenZhiCardBinMgr.currentBranch)}</td>
				<td>上次所属运营机构</td>
				<td>${fenZhiCardBinMgr.lastBranch}-${fn:branch(fenZhiCardBinMgr.lastBranch)}</td>
		  	</tr>
			<tr>
				<td>是否已使用</td>
				<td>${fenZhiCardBinMgr.useFlagName}</td>
				<td>状态</td>
				<td>${fenZhiCardBinMgr.statusName}</td>
		  	</tr>
			<tr>
				<td>更新人</td>
				<td>${fenZhiCardBinMgr.updateBy}</td>
				<td>更新时间</td>
				<td><s:date name="fenZhiCardBinMgr.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>