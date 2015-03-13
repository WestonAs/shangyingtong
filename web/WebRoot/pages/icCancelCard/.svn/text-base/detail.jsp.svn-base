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
			<caption>IC卡销卡登记明细信息<span class="caption_title"> | <f:link href="/pages/icCancelCard/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>换卡批次ID</td>
				<td>${icCancelCardReg.id}</td>
				<td>卡号</td>
				<td>${icCancelCardReg.cardId}</td>
				<td>账号ID</td>
				<td>${icCancelCardReg.acctId}</td>
			</tr>
		  	<tr>
				<td>销卡类型</td>
				<td>${icCancelCardReg.cancelTypeName}</td>
		  		<td>发卡机构编号</td>
				<td>${icCancelCardReg.cardBranch}</td>
				<td>发卡机构名</td>
				<td>${fn:branch(icCancelCardReg.cardBranch)}</td>
			<tr>
		  		<td>是否写卡成功</td>
				<td>${icCancelCardReg.writeCardFlagName}</td>
				<td>操作机构号</td>
				<td>${icCancelCardReg.branchCode}</td>
				<td>操作机构名</td>
				<td>${fn:branch(icCancelCardReg.branchCode)}${fn:dept(icCancelCardReg.branchCode)}</td>
			</tr>
		  	<tr>
				<td>冲正标志</td>
				<td>${icCancelCardReg.reversalFlagName}</td>
		  		<td>旧卡卡片余额</td>
				<td>${fn:amount(icCancelCardReg.balanceAmt)}</td>
		  		<td>手续费</td>
				<td>${fn:amount(icCancelCardReg.feeAmt)}</td>
			</tr>
		  	<tr>
				<td>状态</td>
				<td>${icCancelCardReg.statusName}</td>
		  		<td>更新人</td>
				<td>${icCancelCardReg.updateUser}</td>
		  		<td>更新时间</td>
				<td><s:date name="icCancelCardReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		  	<tr>
				<td>备注</td>
		  		<td colspan="5">${icCancelCardReg.remark}</td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>