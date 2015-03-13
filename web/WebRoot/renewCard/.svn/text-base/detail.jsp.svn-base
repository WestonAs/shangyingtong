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
			<caption>新旧卡号对照详细信息<span class="caption_title"> | <f:link href="/renewCard/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>新卡号</td>
				<td>${renewCardReg.newCardId}</td>
				<td>旧卡号</td>
				<td>${renewCardReg.cardId}</td>
			</tr>
			<tr>
				<td>账户</td>
				<td>${renewCardReg.acctId}</td>
		  		<td>持卡人姓名</td>
				<td>${renewCardReg.custName}</td>
			</tr>
				<td>证件类型</td>
				<td>${renewCardReg.certTypeName}</td>
				<td>证件号码</td>
				<td>${renewCardReg.certNo}</td>
			</tr>
			<tr>
				<td>换卡类型</td>
				<td>${renewCardReg.renewTypeName}</td>
				<td>状态</td>
				<td>${renewCardReg.statusName}</td>
			</tr>
				<td>备注</td>
				<td>${renewCardReg.remark}</td>
				<td>更新用户名</td>
				<td>${renewCardReg.updateUser}</td>
			</tr>
			<tr>
				<td>更新时间</td>
				<td><s:date name="renewCardReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td></td>
				<td></td>
		  	</tr>		  	
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>

