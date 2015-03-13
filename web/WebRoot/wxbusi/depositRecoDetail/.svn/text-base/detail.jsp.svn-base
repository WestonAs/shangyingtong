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
		
		<!-- 卡补账明细 -->
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>${ACT.name}<span class="caption_title"> | <f:link href="/wxbusi/depositRecoDetail/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>对账明细ID</td>
				<td>${wxReconciliationDetail.id}</td>
				<td>银联商户编号</td>
				<td>${wxReconciliationDetail. merchNo}</td>
			</tr>
			<tr>
				<td>交易清算日期</td>
				<td>${wxReconciliationDetail.settDate}</td>
				<td>商盈通交易流水</td>
				<td>${wxReconciliationDetail.sytTranSn}</td>
			</tr>
			<tr>
				<td>差错类型</td>
				<td>${wxReconciliationDetail.errorTypeName}</td>
				<td>处理状态</td>
				<td>${wxReconciliationDetail.proStatusName}</td>
			</tr>
			<tr>
				<td>商盈通对账金额</td>
				<td>${fn:amount(wxReconciliationDetail.sytAmt)}</td>
				<td>增值平台对账金额</td>
				<td>${fn:amount(wxReconciliationDetail.zzptAmt)}</td>
			</tr>
			<tr>
				<td>对账文件记录流水</td>
				<td>${wxReconciliationDetail.cfFileSeq}</td>
				<td>相关登记簿ID</td>
				<td>${wxReconciliationDetail.refRegid}</td>
			</tr>
			<tr>
				<td>更新人</td>
				<td>${wxReconciliationDetail.proUser}</td>
				<td>更新时间</td>
				<td><s:date name="wxReconciliationDetail.proTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
		</table>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>