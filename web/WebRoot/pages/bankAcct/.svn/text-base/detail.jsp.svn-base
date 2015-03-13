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
			<caption>银行账户详细信息<span class="caption_title"> | <f:link href="/pages/bankAcct/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>机构或商户编号</td>
				<td>${insBankacct.insCode}</td>
				<td>机构名称</td>
				<td>${fn:branch(insBankacct.insCode)}${fn:merch(insBankacct.insCode)}</td>
		  	</tr>
			<tr>
				<td>机构类型</td>
				<td>${insBankacct.typeName}</td>
				<td>银行账户类型</td>
				<td>${insBankacct.bankAcctTypeName}</td>
		  	</tr>
			<tr>
				<td>开户行号</td>
				<td>${insBankacct.bankNo}</td>
				<td>开户行名称</td>
				<td>${insBankacct.bankName}</td>
		  	</tr>
		  	<tr>
				<td>开户行账户</td>
				<td>${insBankacct.accNo}</td>
				<td>开户行户名</td>
				<td>${insBankacct.accName}</td>
		  	</tr>
			<tr>
				<td>账号地区码</td>
				<td>${insBankacct.accAreaCode}</td>
				<td>账户类型</td>
				<td>${insBankacct.acctTypeName}</td>
		  	</tr>
			<tr>
				<td>更新人</td>
				<td>${insBankacct.updateBy}</td>
				<td>更新时间</td>
				<td><s:date name="insBankacct.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>