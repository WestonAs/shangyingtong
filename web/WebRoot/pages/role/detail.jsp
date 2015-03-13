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
		<f:css href="/js/tree/dhtmlxtree.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/tree/dhtmlxcommon.js"/>
		<f:js src="/js/tree/dhtmlxtree.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			$loadTree('/pages/role/initTreeByDetail.do?roleInfo.roleId=${roleInfo.roleId}', 'treebox_tree', false, true);
		});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>角色详细信息<span class="caption_title"> | <f:link href="/pages/role/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>角色编号</td>
				<td>${roleInfo.roleId}</td>
				<td>角色名称</td>
				<td>${roleInfo.roleName}</td>
				<td>角色类型</td>
				<td>${roleInfo.roleTypeName}</td>
		  	</tr>
		  	<tr>
				<td>所属机构</td>
				<td>${roleInfo.branchNo}</td>
				<td>所属部门</td>
				<td>${roleInfo.deptId}</td>
				<td>所属商户</td>
				<td>${roleInfo.merchantNo}</td>
		  	</tr>
		  	<tr>
				<td>更新人</td>
				<td>${roleInfo.updateUser}</td>
				<td>更新时间</td>
				<td colspan="3"><s:date name="roleInfo.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		  	<tr>
				<td style="padding-top: 10px;vertical-align: top;">拥有权限</td>
				<td colspan="5">
					<div id="treebox_tree" style="padding:5px;width:300px; background-color:#f5f5f5;border :1px solid Silver;">
						<img src="../../images/loading.gif" />正在加载...
					</div>
				</td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>