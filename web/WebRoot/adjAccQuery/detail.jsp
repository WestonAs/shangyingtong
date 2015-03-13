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
			<caption>${ACT.name}<span class="caption_title"> | <f:link href="/adjAccQuery/list.do?goBack=goBack">返回列表</f:link></span></caption>
			
			<tr>
				<td>卡调账ID</td>
				<td>${adjAccReg.adjAccId}</td>
				<td>卡号</td>
				<td>${adjAccReg.cardId}</td>
			</tr>
			<tr>
				<td>账户</td>
				<td>${adjAccReg.acctId}</td>
				<td>调账金额</td>
				<td>${fn:amount(adjAccReg.amt)}</td>
		  	</tr>
		  	<tr>
				<td>商户号</td>
				<td>${adjAccReg.merNo}</td>
				<td>商户名称</td>
				<td>${fn:merch(adjAccReg.merNo)}</td>
			</tr>
		  	<tr>
				<td>发起方</td>
				<td>${adjAccReg.initiator}</td>
				<td>发卡机构</td>
				<td>${adjAccReg.cardBranch}-${fn:branch(adjAccReg.cardBranch)}</td>
			</tr>
			<tr>
				<td>终端号</td>
				<td>${adjAccReg.termlId}</td>
				<td>发起平台</td>
				<td>${adjAccReg.platformName}</td>
			</tr>
			<tr>
				<td>处理时间</td>
				<td>${adjAccReg.procTime}</td>
				<td>处理状态</td>
				<td>${adjAccReg.procStatusName}</td>
			</tr>
			<tr>	
				<td>业务状态</td>
				<td>${adjAccReg.busiStatusName}</td>
				<td>退货状态</td>
				<td>${adjAccReg.goodsStatusName}</td>
		  	</tr>
		  	 <tr>
				<td>状态</td>
				<td>${adjAccReg.statusName}</td>
				<td>备注</td>
				<td>${adjAccReg.remark}</td>
			</tr>
			<tr>
				<td>操作员</td>
				<td>${adjAccReg.updateUser}</td>
				<td>操作时间</td>
				<td><s:date name="adjAccReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>