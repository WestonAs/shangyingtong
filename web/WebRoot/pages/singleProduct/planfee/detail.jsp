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
			<caption>单机产品发卡机构套餐费用详细信息<span class="caption_title"> | <f:link href="/pages/singleProduct/planfee/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>发卡机构编号</td>
				<td>${cardissuerPlanFee.issId}</td>
				<td>发卡机构名称</td>
				<td>${cardBranchName}</td>
				<td>套餐编号</td>
				<td>${cardissuerPlanFee.planId}</td>
		  	</tr>
		  	<tr>
				<td>状态</td>
				<td>${cardissuerPlanFee.statusName}</td>
				<td>收费标准</td>
				<td colspan="3">${cardissuerPlanFee.chargeTypeName}</td>
		  	</tr>
		  	<tr>
				<td>收费金额</td>
				<td>${fn:amount(cardissuerPlanFee.chargeAmt)}</td>
				<td>标准卡制卡卡单价</td>
				<td>${fn:amount(cardissuerPlanFee.defauleAmt)}</td>
				<td>定制卡制卡单价</td>
				<td>${fn:amount(cardissuerPlanFee.customAmt)}</td>
		  	</tr>
		  	<tr>
				<td>更新人</td>
				<td>${cardissuerPlanFee.updateBy}</td>
				<td>更新时间</td>
				<td colspan="3"><s:date name="cardissuerPlanFee.updateTime" format="yyyy-MM-dd hh:mm:ss"/></td>
		  	</tr>
		</table>
		</div>
		
		<!-- 单机产品发卡机构费用规则表 -->
		<s:if test="planFeeRuleList.size() > 0">
		<div class="tablebox">
			<b><s:label>发卡机构套餐费用子规则列表</s:label></b>
			<table class="" width="96%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">发卡机构编号</td>
			   <td align="center" nowrap class="titlebg">套餐编号</td>
			   <td align="center" nowrap class="titlebg">周期</td>
			   <td align="center" nowrap class="titlebg">参数（金额或百分比）</td>
			   <td align="center" nowrap class="titlebg">赠送卡数量</td>
			</tr>
			</thead>
			<s:iterator value="planFeeRuleList"> 
			<tr>
			  <td nowrap>${branchCode}</td>
			  <td align="center" nowrap>${planId}</td>
			  <td align="center" nowrap>第${period}年</td>
			  <td align="center" nowrap>${ruleParam}</td>
			  <td align="center" nowrap>${sendNum}</td>
			</tr>
			</s:iterator>
			</table>
		</div>
		</s:if>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>