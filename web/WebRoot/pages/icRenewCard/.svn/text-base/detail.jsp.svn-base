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
			<caption>IC卡换卡登记明细信息<span class="caption_title"> | <f:link href="/pages/icRenewCard/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>换卡批次ID</td>
				<td>${icRenewCardReg.id}</td>
				<td>新卡卡号</td>
				<td>${icRenewCardReg.newCardId}</td>
				<td>旧卡卡号</td>
				<td>${icRenewCardReg.oldCardId}</td>
			</tr>
		  	<tr>
				<td>账号ID</td>
				<td>${icRenewCardReg.acctId}</td>
		  		<td>发卡机构编号</td>
				<td>${icRenewCardReg.cardBranch}</td>
				<td>发卡机构名</td>
				<td>${fn:branch(icRenewCardReg.cardBranch)}</td>
			<tr>
				<td>换卡类型</td>
				<td>${icRenewCardReg.renewTypeName}</td>
				<td>操作机构号</td>
				<td>${icRenewCardReg.branchCode}</td>
				<td>操作机构名</td>
				<td>${fn:branch(icRenewCardReg.branchCode)}${fn:dept(icRenewCardReg.branchCode)}</td>
			</tr>
		  	<tr>
		  		<td>是否写卡成功</td>
				<td>${icRenewCardReg.writeCardFlagName}</td>
				<td>冲正标志</td>
				<td>${icRenewCardReg.reversalFlagName}</td>
		  		<td>旧卡卡片余额</td>
				<td>${fn:amount(icRenewCardReg.oldBalanceAmt)}</td>
			</tr>
		  	<tr>
				<td>状态</td>
				<td>${icRenewCardReg.statusName}</td>
		  		<td>更新人</td>
				<td>${icRenewCardReg.updateUser}</td>
		  		<td>更新时间</td>
				<td><s:date name="icRenewCardReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		  	<tr>
				<td>备注</td>
		  		<td colspan="5">${icRenewCardReg.remark}</td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>