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
			<table class="detail_grid" width="80%" border="1" cellspacing="0" cellpadding="1">
				<caption>${ACT.name}<span class="caption_title"> | <f:link href="/unfreezeBat/list.do?goBack=goBack">返回列表</f:link></span></caption>
				<tr>
					<td>卡账户解付编号</td>
					<td>${unfreezeReg.unfreezeId}</td>
					<td>解付金额</td>
					<td>${unfreezeReg.unfznAmt}</td>
				</tr>
				<tr>
					<td>开始卡号</td>
					<td>${unfreezeReg.startCard}</td>
					<td>结束卡号</td>
					<td>${unfreezeReg.endCard}</td>
				
				</tr>
				<tr>
					<td>状态</td>
					<td>${unfreezeReg.statusName}</td>	
					<td>操作员</td>	
					<td>${unfreezeReg.updateUser}</td>	
				</tr>
				<tr>
					<td>操作机构</td>	
					<td>${unfreezeReg.branchCode }</td>	
					<td>机构名称</td>
					<td>${unfreezeReg.branchName}</td>	
				</tr>
				<tr>
					<td>操作时间</td>
					<td><s:date name = "unfreezeReg.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>	
					<td>备注</td>	
					<td>${unfreezeReg.remark}</td>	
				</tr>
			</table>
			</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>
