<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>WS客户端ip限制-明细</title>
		
		<f:css href="/css/page.css"/>
		<style type="text/css">
			html { overflow-y: scroll; }
			#tranEnableDiv table table .headcell { text-align: right; }
		</style>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
				<caption>${ACT.name}<span class="caption_title"> | <f:link href="/para/webServiceConf/wsClientIpLimit/listDtl.do?goBack=goBack">返回列表</f:link></span></caption>
				<tr>
					<td>机构编号-名称</td>
					<td>${wsClientIpLimitDtl.branchCode}-${fn:branch(wsClientIpLimitDtl.branchCode)}</td>
					<td>IP</td>
					<td>${wsClientIpLimitDtl.ip}</td>
					
					<td>状态</td>
					<td>
						<s:if test='wsClientIpLimitDtl.status=="1"'>启用</s:if>
						<s:else><font color="red">注销</font></s:else>
					</td>
				</tr>
				<tr>
			  		<td>备注</td>
					<td>${wsClientIpLimitDtl.remark }</td>
					<td>插入时间</td>
					<td><s:date name="wsClientIpLimitDtl.insertTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td>更新时间</td>
					<td><s:date name="wsClientIpLimitDtl.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				<tr>
					<td>更新用户名</td>
					<td>${wsClientIpLimitDtl.updateBy}</td>
			  	</tr>
			</table>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>