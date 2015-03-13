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
			<caption>机构详细信息<span class="caption_title"> | <f:link href="/branch/list.do">返回列表</f:link></span></caption>
			<tr>
				<td>机构编号</td>
				<td>${branch.branchCode}</td>
				<td>机构名称</td>
				<td>${branch.branchName}</td>
				<td>机构简称</td>
				<td>${branch.branchAbbname}</td>
		  	</tr>
		  	<tr>
				<td>所属地区</td>
				<td>${branch.areaCode}</td>
				<td>上级机构</td>
				<td>${branch.parent}</td>
				<td>开户行行号</td>
				<td>${branch.bankNo}</td>
		  	</tr>
		  	<tr>
				<td>开户行名称</td>
				<td>${branch.bankName}</td>
				<td>开户行账号</td>
				<td>${branch.accNo}</td>
				<td>开户行户名</td>
				<td>${branch.accName}</td>
		  	</tr>
		  	<tr>
				<td>联系人</td>
				<td>${branch.contact}</td>
				<td>地址</td>
				<td>${branch.address}</td>
				<td>邮编</td>
				<td>${branch.zip}</td>
		  	</tr>
		  	<tr>
				<td>联系电话</td>
				<td>${branch.phone}</td>
				<td>传真号码</td>
				<td>${branch.fax}</td>
				<td>Email</td>
				<td>${branch.email}</td>
		  	</tr>
		  	<tr>
				<td>状态</td>
				<td>${branch.statusName}</td>
				<td>更新人</td>
				<td>${branch.updateUser}</td>
				<td>更新时间</td>
				<td><s:date name="branch.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>