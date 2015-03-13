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
			<caption>写磁记录详细信息<span class="caption_title"> | <f:link href="/addMag/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>写磁ID</td>
				<td>${addMagReg.addMagId}</td>
				<td>卡号</td>
				<td>${addMagReg.cardId}</td>
				<td>证件类型</td>
				<td>${addMagReg.certTypeName}</td>
		  	</tr>
		  	<tr>	
				<td>证件号码</td>
				<td>${addMagReg.certNo}</td>
				<td>状态</td>
				<td>${addMagReg.statusName}</td>
				<td>操作员</td>
				<td>${addMagReg.updateUser}</td>
			</tr>
			<tr>
				<td>更新时间</td>
				<td><s:date name="addMagReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td>备注</td>
				<td>${addMagReg.remark}</td>
				<td></td>
				<td></td>
		  	</tr>		  	
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>

