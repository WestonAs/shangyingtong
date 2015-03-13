<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="gnete.etc.Constants"%>
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
			<caption>${ACT.name}<span class="caption_title"> | <f:link href="/wxbusi/depositReco/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>编号</td>
				<td>${wxDepositReconReg.seqId}</td>
				<td>对账明细ID</td>
				<td>${wxDepositReconReg.reconDetailId}</td>
			</tr>
			<tr>
				<td>操作类型</td>
				<td>${wxDepositReconReg.opeTypeName}</td>
				<td>调整金额</td>
				<td>${fn:amount(wxDepositReconReg.transAmt)}</td>
			</tr>
			<tr>
				<td>发卡机构编号</td>
				<td>${wxDepositReconReg.issNo}</td>
				<td>发卡机构名称</td>
				<td>${fn:branch(wxDepositReconReg.issNo)}</td>
			</tr>
			<tr>
				<td>外部卡号</td>
				<td>${wxDepositReconReg.extCardId}</td>
				<td>卡号</td>
				<td>${wxDepositReconReg.cardId}</td>
			</tr>
			<tr>
				<td>状态</td>
				<td>${wxDepositReconReg.statusName}</td>
				<td>备注</td>
				<td>${wxDepositReconReg.remark}</td>
			</tr>
			<tr>
				<td>更新人</td>
				<td>${wxDepositReconReg.updateUser}</td>
				<td>更新时间</td>
				<td><s:date name="wxDepositReconReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
		</table>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>