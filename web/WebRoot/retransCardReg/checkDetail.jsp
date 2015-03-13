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
			<caption>卡补账详细信息<span class="caption_title"> | <f:link href="/retransCardReg/checkList.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>补账编号</td>
				<td>${retransCardReg.retransCardId}</td>
				<td>卡号</td>
				<td>${retransCardReg.cardId}</td>
				<td>账号</td>
				<td>${retransCardReg.acctId}</td>
			</tr>
			<tr>
				<td>商户号</td>
				<td>${retransCardReg.merchId}</td>
				<td>商户名称</td>
				<td>${retransCardReg.merchName}</td>
				<td>终端号</td>
				<td>${retransCardReg.termId}</td>
			</tr>
			<tr>
				<td>补账金额</td>
				<td>${fn:amount(retransCardReg.amt)}</td>
				<td>使用账户</td>
				<td>${retransCardReg.coupon}</td>
				<td>发起平台</td>
				<td>${retransCardReg.platform}</td>
			</tr>
			<tr>
				<td>状态</td>
				<td>${retransCardReg.statusName}</td>
				<td>备注</td>
				<td>${retransCardReg.remark}</td>
				<td>更新人</td>
				<td>${retransCardReg.updateUser}</td>
			</tr>
			<tr>
				<td>更新时间</td>
				<td><s:date name="retransCardReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td></td><td></td>
				<td></td><td></td>
			</tr>
		</table>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=Constants.WORKFLOW_RETRANS_CARD_REG%>"/>
			<jsp:param name="refId" value="${retransCardReg.retransCardId}"/>
		</jsp:include>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>