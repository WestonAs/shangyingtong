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
			<caption>用户详细信息<span class="caption_title"> | <f:link href="/pages/user/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>用户编号</td>
				<td>${userInfo.userId}</td>
				<td>用户名称</td>
				<td>${userInfo.userName}</td>
				<td>所属机构</td>
				<td>${userInfo.branchNo}</td>
		  	</tr>
			<tr>
				<td>所属部门</td>
				<td>${userInfo.deptId}</td>
				<td>所属商户</td>
				<td>${userInfo.merchantNo}</td>
				<td>联系电话</td>
				<td>${userInfo.phone}</td>
		  	</tr>
			<tr>
				<td>手机</td>
				<td>${userInfo.mobile}</td>
				<td>E-Mail</td>
				<td>${userInfo.email}</td>
				<td>状态</td>
				<td>${userInfo.statusName}</td>
		  	</tr>
		  	<tr>
				<td>更新人</td>
				<td>${userInfo.updateUser}</td>
				<td>更新时间</td>
				<td colspan="3"><s:date name="userInfo.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		  	<tr>
				<td style="padding-top: 10px;vertical-align: top;">拥有角色</td>
				<td colspan="5">
					<s:iterator value="roleList">
						<span>${roleName}</span>
					</s:iterator>
				</td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>