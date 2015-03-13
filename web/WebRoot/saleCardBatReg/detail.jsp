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
		<f:js src="/js/paginater.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>批量<s:text name="actionSubLabel"></s:text><s:text name="cardTypeCode.cardTypeName"></s:text>信息
				<span class="caption_title"> | <f:link href="/saleCardBatReg/list${actionSubName}.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>售卡批次ID</td>
				<td>${saleCardReg.saleBatchId}</td>
		  		<td>售卡方式</td>
				<td>${saleCardReg.presellName}</td>
		  		<td>售卡状态</td>
		  		<s:if test=" '02'.equals(saleCardReg.status)">
					<td class="redfont">${saleCardReg.statusName}</td> 
		  		</s:if>
		  		<s:else>
					<td>${saleCardReg.statusName}</td> 
		  		</s:else>
			</tr>
		  	<tr>
				<td>售卡日期</td>
				<td>${saleCardReg.saleDate}</td>
				<td>卡类型</td>
				<td>${cardTypeCode.cardTypeName}</td>
		  		<td>购卡客户</td>
				<td>${cardCustomer.cardCustomerName}</td>				
			</tr>
		  	<tr>
				<td>售卡返利规则</td>
				<td>${saleRebateRule.rebateName}</td>
				<td>返利方式</td>
				<td>${saleCardReg.rebateTypeName}</td>
				<td>返利卡卡号</td>
				<td>${saleCardReg.rebateCard}</td>
			</tr>
			<s:if test="saleCardReg.rebateType != null">
			<tr>
				<s:if test="depositTypeIsTimes==true" >
					<td>充值次数</td>
					<td>${saleCardReg.amt}</td>
				</s:if>
				<s:else>
					<td>售卡金额</td>
					<td>${fn:amount(saleCardReg.amt)}</td>
				</s:else>
		  		<td>返利金额</td>
				<td>${fn:amount(saleCardReg.rebateAmt)}</td>
				<td>手续费</td>
				<td>${fn:amount(saleCardReg.feeAmt)}</td>
			</tr>
			</s:if>
		  	<tr>
				<td>工本费</td>
				<td>${fn:amount(saleCardReg.expenses)}</td>
				<td>实收金额</td>
				<td class="bigred">${fn:amount(saleCardReg.realAmt)}</td>
				<td>手续费比例</td>
				<td>${fn:percent(saleCardReg.feeRate)}</td>
			</tr>
		  	<tr>
		  		<td>起始卡号</td>
				<td>${saleCardReg.strCardId}</td>
		  		<td>结束卡号</td>
				<td>${saleCardReg.endCardId}</td>
				<td>是否已经撤销</td>
				<td>${saleCardReg.cancelFlagName}</td>
		  	</tr>
		  	<tr>
		  		<td>售卡机构</td>
				<td>${saleCardReg.branchCode}-${fn:branch(saleCardReg.branchCode)}${fn:dept(saleCardReg.branchCode)}</td>
				<td>激活机构</td>
				<td>${saleCardReg.activateBranch}-${fn:branch(saleCardReg.activateBranch)}${fn:dept(saleCardReg.activateBranch)}</td>
				<td>售卡撤销标志</td>
				<td>${saleCardReg.saleCancelName}</td>
			</tr>
		  	<tr>
		  		<td>发卡机构编号</td>
		  		<td>${saleCardReg.cardBranch}-${fn:branch(saleCardReg.cardBranch)}</td>
		  		<td>付款户名</td>
		  		<td>${saleCardReg.payAccName}</td>
		  		<td>付款账号</td>
		  		<td>${saleCardReg.payAccNo}</td>
		  	</tr>
		  	<tr>
		  		<td>更新人</td>
				<td>${saleCardReg.updateUser}</td>
		  		<td >更新时间</td>
				<td><s:date name="saleCardReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
			<s:if test="saleCardReg.couponClass!=null && saleCardReg.couponAmt!=null">
			  	<tr>
					<td>赠券类型编号</td>
			  		<td>${saleCardReg.couponClass}</td>
			  		<td>赠券金额(每张卡)</td>
			  		<td>${fn:amount(saleCardReg.couponAmt)}</td>
			  		<td></td>
			  		<td></td>
				</tr>
			</s:if>
		  	<tr>
		  		<td>备注</td>
		  		<td colspan="5">${saleCardReg.remark}</td>
		  	</tr>
		</table>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=WorkflowConstants.WORKFLOW_SALE_CARD_BATCH%>"/>
			<jsp:param name="refId" value="${saleCardBatReg.saleBatchId}"/>
		</jsp:include>
		
		<br />
		<!-- 售卡返利规则分段比例明细 -->
		<div class="tablebox">
			<b><s:label>售卡返利规则分段比例明细 </s:label></b>
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">			
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">售卡返利规则ID</td>
			   <td align="center" nowrap class="titlebg">售卡返利分段号</td>
			   <td align="center" nowrap class="titlebg">售卡分段金额上限</td>
			   <td align="center" nowrap class="titlebg">售卡返利比（%）或返利金额</td>			   
			</tr>
			</thead>
			<s:iterator value="saleRebateRuleDetailList"> 
			<tr>
			  <td nowrap><s:text name="rebateId"></s:text></td>
			  <td align="center" nowrap><s:text name="rebateSect"></s:text></td>			  
			  <td align="center" nowrap><s:text name="rebateUlimit"></s:text></td>
			  <td align="center" nowrap><s:text name="rebateRate"></s:text></td>			  
			</tr>
			</s:iterator>
			</table>
		</div>
		
		<!-- 多张卡返利明细 -->
		<s:if test="rebateCardList.size() > 0">
		<div class="tablebox">
			<b><s:label>返利卡返利信息明细 </s:label></b>
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">			
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">登记簿ID</td>
			   <td align="center" nowrap class="titlebg">返利类型</td>
			   <td align="center" nowrap class="titlebg">返利卡号</td>
			   <td align="center" nowrap class="titlebg">返利金额</td>			   
			</tr>
			</thead>
			<s:iterator value="rebateCardList"> 
			<tr>
			  <td nowrap>${regId}</td>
			  <td align="center" nowrap>${rebateFromName}</td>			  
			  <td align="center" nowrap>${cardId}</td>
			  <td align="center" nowrap>${fn:amount(rebateAmt)}</td>			  
			</tr>
			</s:iterator>
			</table>
		</div>
		</s:if>

		<!-- 批量售卡登记簿明细 -->
		<div class="tablebox">
			<b><s:label>批量售卡登记簿明细 </s:label></b>
			<form id="id_detail_bat" method="post" action="detail.do?saleCardBatReg.saleBatchId=${saleCardBatReg.saleBatchId}">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">			
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">卡号</td>
			   <td align="center" nowrap class="titlebg">工本费</td>
			   <td align="center" nowrap class="titlebg"><s:if test="depositTypeIsTimes==true" >充值次数</s:if><s:else>售卡金额</s:else></td>
			   <td align="center" nowrap class="titlebg">返利金额</td>			   
			   <td align="center" nowrap class="titlebg">实收金额</td>			   
			   <td align="center" nowrap class="titlebg">预售标志</td>			   
			   <td align="center" nowrap class="titlebg">状态</td>			  
			</tr>
			</thead>
			<s:iterator value="saleCardBatPage.data"> 
			<tr>
			  <td nowrap><s:text name="cardId"></s:text></td>
			  <td align="right" nowrap>${fn:amount(expenses)}</td>			  
			  <td align="right" nowrap><s:if test="depositTypeIsTimes==true" >${amt}</s:if><s:else>${fn:amount(amt)}</s:else></td>
			  <td align="right" nowrap>${fn:amount(rebateAmt)}</td>
			  <td align="right" nowrap>${fn:amount(realAmt)}</td>
			  <td align="center" nowrap><s:text name="presellName"></s:text></td>
			  <td align="center" nowrap><s:text name="statusName"></s:text></td>	  
			</tr>
			</s:iterator>
			</table>
			</form>
			<f:paginate name="saleCardBatPage"/>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>