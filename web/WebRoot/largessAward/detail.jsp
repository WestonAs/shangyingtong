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
		
		<!-- 派赠明细 -->
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>派赠详细信息<span class="caption_title"> | <f:link href="/largessAward/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>派赠编号</td>
				<td>${largessReg.largessRegId}</td>
				<td>赠品编号</td>
				<td>${largessReg.largessId}</td>
			</tr>
			<tr>
				<td>赠品名称</td>
				<td>${largessDef.largessName}</td>
				<td>赠品数量</td>
				<td>${largessReg.largessNum}</td>
			</tr>
			<tr>
				<td>赠送规则</td>
				<td>${largessDef.largessRule}</td>
				<td>客户姓名</td>
				<td>${largessReg.custName}</td>
			</tr>		
			<tr>
				<td>联系地址</td>
				<td>${largessReg.address}</td>
				<td>联系电话</td>
				<td>${largessReg.mobileNo}</td>
			</tr>
			<tr>
				<td>更新用户名</td>
				<td>${largessReg.updateBy}</td>
				<td>更新时间</td>
				<td><s:date name="largessReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr> 
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>