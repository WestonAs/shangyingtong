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
			<caption>售卡撤销明细信息<span class="caption_title"> | <f:link href="/saleCardCancel/checkList.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>撤销批次</td>
				<td>${saleCardReg.saleBatchId}</td>
		  		<td>原售卡方式</td>
				<td>${saleCardReg.presellName}</td>
		  		<td>状态</td>
				<td>${saleCardReg.statusName}</td> 
			</tr>
		  	<tr>
				<td>卡号</td>
				<td>${saleCardReg.cardId}</td>
				<td>卡类型</td>
				<td>${saleCardReg.cardClassName}</td>
		  		<td>购卡客户</td>
				<td>${saleCardReg.cardCustomerName}</td>
			</tr>
		  	<tr>
		  		<td>售卡返利规则</td>
				<td>${saleRebateRule.rebateName}</td>
				<td>返利方式</td>
				<td>${saleCardReg.rebateTypeName}</td>
				<td>返利卡卡号</td>
				<td>${saleCardReg.rebateCard}</td>
			</tr>
		  	<tr>
		  		<td><s:if test="depositTypeIsTimes==true" >充值次数</s:if><s:else>售卡金额</s:else></td>
				<td><s:if test="depositTypeIsTimes==true" >${saleCardReg.amt}</s:if><s:else>${fn:amount(saleCardReg.amt)}</s:else></td>
				<td>工本费</td>
				<td>${fn:amount(saleCardReg.expenses)}</td>
		  		<td>返利金额</td>
				<td>${fn:amount(saleCardReg.rebateAmt)}</td>
			</tr>
			<tr>
				<td>撤销金额</td>
				<td class="bigred">${fn:amount(saleCardReg.realAmt)}</td>
				<td>售卡日期</td>
				<td>${saleCardReg.saleDate}</td>
				<td>备注</td>
		  		<td>${saleCardReg.remark}</td>
			</tr>
		  	<tr>
		  		<td>售卡撤销机构</td>
				<td>${fn:branch(saleCardReg.branchCode)}</td>
				<td>售卡撤销标志</td>
				<td>${saleCardReg.saleCancelName}</td>
				<td>原售卡批次</td>
				<td>${saleCardReg.oldSaleBatch}</td>
			</tr>
			<tr>
		  		<td>起始卡号</td>
				<td>${saleCardReg.strCardId}</td>
		  		<td>结束卡号</td>
				<td>${saleCardReg.endCardId}</td>
		  		<td>手续费比例</td>
				<td>${fn:percent(saleCardReg.feeRate)}</td>
		  	</tr>
		  	<tr>
		  		<td>更新人</td>
				<td>${saleCardReg.updateUser}</td>
		  		<td>更新时间</td>
				<td ><s:date name="saleCardReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td>手续费</td>
				<td>${fn:amount(saleCardReg.feeAmt)}</td>
		  	</tr>
		</table>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=WorkflowConstants.WORKFLOW_SALECARD_CANCEL%>"/>
			<jsp:param name="refId" value="${saleCardReg.saleBatchId}"/>
		</jsp:include>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>