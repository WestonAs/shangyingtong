<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="gnete.etc.WorkflowConstants"%>
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
			<caption>转帐申请详细信息<span class="caption_title"> | <f:link href="/transAccReg/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>转账编号</td>
				<td>${transAccReg.transAccId}</td>
				<td>转出卡号</td>
				<td>${transAccReg.outCardId}</td>
				<td>转出账户</td>
				<td>${transAccReg.outAccId}</td>
		  	</tr>
			<tr>
				<td>转出子帐户类型</td>
				<td>${transAccReg.subacctTypeName}</td>
				<td>可用余额</td>
				<td>${fn:amount(transAccReg.avlbBal)}</td>
				<td>冻结金额</td>
				<td>${fn:amount(transAccReg.fznAmt)}</td>
		  	</tr>
			<tr>
				<td>转入子帐户类型</td>
				<td>${transAccReg.inSubacctTypeName}</td>
				<td>转入卡号</td>
				<td>${transAccReg.inCardId}</td>
				<td>转入账户</td>
				<td>${transAccReg.inAccId}</td>
		  	</tr>
		  	<tr>
				<td>转账金额</td>
				<td>${fn:amount(transAccReg.amt)}</td>
				<td>发卡机构</td>
				<td>${transAccReg.cardBranch}</td>
				<td>发起方</td>
				<td>${transAccReg.initiator}</td>
		  	</tr>
		  	<tr>
				<td>状态</td>
				<td>${transAccReg.statusName}</td>
				<td>操作员</td>
				<td>${transAccReg.updateUser}</td>
				<td>操作时间</td>
				<td><s:date name="transAccReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		  	<tr>
				<td>备注</td>
				<td colspan="5">${transAccReg.remark}</td>
		  	</tr>
		</table>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=WorkflowConstants.WORKFLOW_TRANSFER%>"/>
			<jsp:param name="refId" value="${transAccReg.transAccId}"/>
		</jsp:include>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>