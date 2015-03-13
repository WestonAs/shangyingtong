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
			$loadTree('/pages/role/initTreeByDeptDetail.do?deptId=${departmentInfo.deptId}', 'treebox_tree', false, true);
		});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>部门详细信息<span class="caption_title"> | <f:link href="/pages/dept/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>部门代码</td>
				<td>${departmentInfo.deptId}</td>
				<td>所属机构</td>
				<td>${departmentInfo.branchNo}-${fn:branch(departmentInfo.branchNo)}</td>
				<td>部门名称</td>
				<td>${departmentInfo.deptName}</td>
		  	</tr>
		  	<tr>
				<td>状态</td>
				<td>${departmentInfo.statusName}</td>
				<td>更新人</td>
				<td>${departmentInfo.updateBy}</td>
				<td>更新时间</td>
				<td><s:date name="departmentInfo.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
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