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
		
		<div id="printArea" >
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>赠券赠送明细信息<span class="caption_title"> | <f:link href="/pages/couponDepositBat/checkList.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>赠送批次ID</td>
				<td>${depositCouponReg.depositBatchId}</td>
				<td>卡号</td>
				<td>${depositCouponReg.cardId}</td>
				<td>赠送日期</td>
				<td>${depositCouponReg.depositDate}</td>
			</tr>
		  	<tr>
		  		<td>发卡机构号</td>
		  		<td>${depositCouponReg.cardBranch}</td>
		  		<td>发卡机构名称</td>
		  		<td>${fn:branch(depositCouponReg.cardBranch)}</td>
				<td>状态</td>
				<td>${depositCouponReg.statusName}</td>
			</tr>
			<tr>
		  		<td>赠送赠券</td>
				<td>${fn:amount(depositCouponReg.couponAmt)}</td>
				<td>折算金额</td>
				<td>${fn:amount(depositCouponReg.refAmt)}</td>
				<td>起始卡号</td>
				<td>${depositCouponReg.strCardId}</td>
			</tr>
			<tr>
				<td>赠送机构号</td>
				<td>${depositCouponReg.depositBranch}</td>
				<td>赠送机构名</td>
				<td>${fn:branch(depositCouponReg.depositBranch)}</td>
				<td>结束卡号</td>
				<td>${depositCouponReg.endCardId}</td>
			</tr>
		  	<tr>
		  		<td>更新人</td>
				<td>${depositCouponReg.updateUser}</td>
		  		<td>更新时间</td>
				<td colspan="3"><s:date name="depositCouponReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		  	<tr>
				<td>备注</td>
		  		<td colspan="5">${depositCouponReg.remark}</td>
		  	</tr>
		</table>
		</div>
		</div>
		
		<div style="padding-left: 30px;margin: 0px;">
			<input type="button" value="打印" onclick="openPrint();"/>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=WorkflowConstants.WORKFLOW_DEPOSIT_COUPON%>"/>
			<jsp:param name="refId" value="${depositCouponReg.depositBatchId}"/>
		</jsp:include>
		<br />
		
		<s:if test="batPage.data.size > 0">
		<!-- 批量赠送登记簿明细 -->
		<div class="tablebox">
			<b><s:label>批量赠券赠送登记簿明细 </s:label></b>
			<form id="id_detail_bat" method="post" action="depositCouponReg.depositBatchId=${depositCouponReg.depositBatchId}">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">			
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">卡号</td>
			   <td align="center" nowrap class="titlebg">赠送赠券</td>
			   <td align="center" nowrap class="titlebg">赠券折算金额</td>			   
			   <td align="center" nowrap class="titlebg">状态</td>			   
			</tr>
			</thead>
			<s:iterator value="batPage.data"> 
			<tr>
			  <td nowrap>${cardId }</td>
			  <td align="right" nowrap>${fn:amount(couponAmt)}</td>
			  <td align="right" nowrap>${fn:amount(refAmt)}</td>
			  <td align="center" nowrap>${statusName}</td>	  
			</tr>
			</s:iterator>
			</table>
			</form>
			<f:paginate name="batPage"/>
		</div>
		</s:if>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>