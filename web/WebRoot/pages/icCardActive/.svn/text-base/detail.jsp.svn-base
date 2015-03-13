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
			<caption>IC卡激活或余额补登明细信息<span class="caption_title"> | <f:link href="/pages/icCardActive/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>激活批次</td>
				<td>${icCardActive.activeBatchId}</td>
				<td>卡号</td>
				<td>${icCardActive.cardId}</td>
				<td>卡种类</td>
				<td>${icCardActive.cardClassName}</td>
			</tr>
		  	<tr>
		  		<td>发卡机构编号</td>
				<td>${icCardActive.cardBranch}</td>
				<td>发卡机构名</td>
				<td>${fn:branch(icCardActive.cardBranch)}</td>
				<td>卡类型号</td>
				<td>${icCardActive.cardSubClass}</td>
			<tr>
				<td>激活机构名</td>
				<td>${fn:branch(icCardActive.activeBranch)}</td>
				<td>激活机构号</td>
				<td>${icCardActive.activeBranch}</td>
				<td>余额</td>
				<td>${fn:amount(icCardActive.lastBalance)}</td>
			</tr>
		  	<tr>
		  		<td>是否写卡成功</td>
				<td>${icCardActive.writeCardFlagName}</td>
				<td>冲正标志</td>
				<td>${icCardActive.reversalFlagName}</td>
				<td>状态</td>
				<td>${icCardActive.statusName}</td>
			</tr>
		  	<tr>
		  		<td>更新人</td>
				<td>${icCardActive.updateUser}</td>
		  		<td>更新时间</td>
				<td colspan="3"><s:date name="icCardActive.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		  	<tr>
				<td>备注</td>
		  		<td colspan="5">${icCardActive.remark}</td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>