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
			<caption><s:text name="cardTypeCode.cardTypeName"/>充值信息<span class="caption_title"> | <f:link href="/depositRegSign/list.do">返回列表</f:link></span></caption>
			<tr>
				<td>充值批次ID</td>
				<td>${depositReg.depositBatchId}</td>
				<td>卡号</td>
				<td>${depositReg.cardId}</td>
				<td>账户ID</td>
				<td>${cardInfo.acctId}</td>
			</tr>
		  	<tr>
				<td>售卡机构</td>
				<td>${depositReg.depositBranch}</td>
				<td>卡类型</td>
				<td>${cardTypeCode.cardTypeName}</td>
		  		<td>签单客户</td>
				<td>${signCust.signCustName}</td>
			</tr>
		  	<tr>
				<td>返利方式</td>
				<!-- 
				<td>${signCust.rebateTypeName}</td>
				 -->
				<td>无</td>
				<td>卡BIN</td>
				<td>${cardBin.binNo}</td>
				<td>卡BIN名称</td>
				<td>${cardBin.binName}</td>
			</tr>
		  	<tr>
		  		<td>充值返利规则</td>
		  		<!--  
				<td>${rebateRule.rebateName}</td>
				<td>计算方式</td>
				<td>${rebateRule.calTypeName}</td>
				-->
				<td>无</td>
				<td>计算方式</td>
				<td>不计算</td>
				<td>充值金额</td>
				<td>${fn:amount(depositReg.amt)}</td>
			</tr>
		  	<tr>
		  		<td>返利金额</td>
				<!-- <td>${fn:amount(depositReg.rebateAmt)}</td>  -->
				<td>无</td>
				<td>实收金额</td>
				<td>${fn:amount(depositReg.realAmt)}</td>
				<td>持卡人</td>
				<td>${depositReg.custName}</td>
			</tr>
		  	<tr>
				<td>状态</td>
				<td>${depositReg.statusName}</td> 
				<td>备注</td>
		  		<td>${depositReg.remark}</td>
		  		<td>更新人</td>
				<td>${depositReg.updateUser}</td>
		  	</tr>
		  	<tr>
		  		<td>更新时间</td>
				<td><s:date name="depositReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				
		  	</tr>
		</table>
		</div>
		
		<!-- 充值返利规则分段比例明细 -->
		<!-- 
		<div class="tablebox">
			<b><s:label>充值返利规则分段比例明细 </s:label></b>
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">			
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">充值返利规则ID</td>
			   <td align="center" nowrap class="titlebg">充值返利分段号</td>
			   <td align="center" nowrap class="titlebg">充值分段金额上限</td>
			   <td align="center" nowrap class="titlebg">充值返利比（%）或返利金额</td>			   
			</tr>
			</thead>
			<s:iterator value="rebateRuleDetailList"> 
			<tr>
			  <td nowrap><s:text name="rebateId"></s:text></td>
			  <td align="center" nowrap><s:text name="rebateSect"></s:text></td>			  
			  <td align="center" nowrap><s:text name="rebateUlimit"></s:text></td>
			  <td align="center" nowrap><s:text name="rebateRate"></s:text></td>			  
			</tr>
			</s:iterator>
			</table>
		</div>
		 -->

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>