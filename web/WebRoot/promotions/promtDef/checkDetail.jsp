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
			<caption>促销活动详细信息<span class="caption_title"> | <f:link href="/promotions/promtDef/checkList.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>促销活动ID</td>
				<td>${promtDef.promtId}</td>
				<td>促销活动名</td>
				<td>${promtDef.promtName}</td>
				<td>交易类型</td>
				<td>${promtDef.transTypeName}</td>
		  	</tr>
		  	<tr>
				<td>发行机构类型</td>
				<td>${promtDef.issTypeName}</td>
				<td>发行机构编号</td>
				<td>${promtDef.issId}</td>
				<td>发行机构名</td>
				<td>${fn:branch(promtDef.issId)}</td>
		  	</tr>
		  	<tr>
				<td>参与机构类型</td>
				<td>${promtDef.pinstTypeName}</td>
				<td>参与机构编号</td>
				<td>${promtDef.pinstId}</td>
				<td>参与机构名</td>
				<td>${fn:branch(promtDef.pinstId)}${fn:merch(promtDef.pinstId)}</td>
		  	</tr>
		  	<tr>
				<td>赠送机构类型</td>
				<td>${promtDef.sendTypeName}</td>
				<td>赠送机构编号</td>
				<td>${promtDef.sendId}</td>
				<td>赠送机构名</td>
				<td>${fn:branch(promtDef.sendId)}${fn:merch(promtDef.sendId)}</td>
		  	</tr>
		  	<tr>
				<td>卡bin范围</td>
				<td colspan="3">${promtDef.cardBinScope}</td>
				<td>状态</td>
				<td>${promtDef.statusName}</td>
		  	</tr>
		  	<tr>
				<td>生效日期</td>
				<td>${promtDef.effDate}</td>
				<td>失效日期</td>
				<td>${promtDef.expirDate}</td>
				<td>时间段（HHmm）</td>
				<td>
					<s:if test='promtDef.timeFlag == "1"'>
						${promtDef.effTime} - ${promtDef.expirTime}
					</s:if>
					<s:else>
						不使用时间段
					</s:else>
				</td>
			</tr>
			<tr>
				<td>是否有附加范围</td>
				<td>
					<s:if test="promtDef.addScope == 1">是</s:if>
					<s:else>否</s:else>
				</td>
		  	</tr>
		</table>
		</div>
		
		<!-- 促销活动范围 -->
		<div class="tablebox">
			<table class="" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">ID</td>
			   <td align="center" nowrap class="titlebg">范围类型</td>
			   <td align="center" nowrap class="titlebg">生效日期</td>
			   <td align="center" nowrap class="titlebg">失效日期</td>			   
			   <td align="center" nowrap class="titlebg">操作</td>			   
			</tr>
			</thead>
			<s:iterator value="promtScopeList"> 
			<tr>
			  <td nowrap>${id}</td>
			  <td align="center" nowrap>${scopeType}</td>			  
			  <td align="center" nowrap>${effDate}</td>
			  <td align="center" nowrap>${expirDate}</td>			  
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<a href="javascript:void(0);" onclick="$(this).parent().parent().parent().next().toggle();">明细</a>
			  	</span>
			  </td>
			</tr>
			<tr style="display: none;">
			<td colspan="5">
			<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
				<tr>
					<td>ID</td>
					<td>${id}</td>
					<td>范围类型</td>
					<td>${scopeTypeName}</td>
					<td>商户号</td>
					<td>${merNo}</td>
			  	</tr>
			  	<tr>
					<td>卡bin范围</td>
					<td colspan="5">${cardBinScope}</td>
			  	</tr>
			  	<tr>
					<td>卡子类型</td>
					<td>${cardSubclass}</td>
					<td>积分下限</td>
					<td>${ptLlimit}</td>
					<td>积分上限</td>
					<td>${ptUlimit}</td>
			  	</tr>
			  	<tr>
					<td>会员级别</td>
					<td>${membLevel}</td>
					<td>生效日期</td>
					<td>${effDate}</td>
					<td>失效日期</td>
					<td>${expirDate}</td>
			  	</tr>
			</table>
			</td>
			</tr>
			</s:iterator>
			</table>
		</div>

		<!-- 促销活动规则表 -->
		<div class="tablebox">
			<table class="" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">促销规则ID</td>
			   <td align="center" nowrap class="titlebg">金额类型</td>
			   <td align="center" nowrap class="titlebg">参考金额</td>
			   <td align="center" nowrap class="titlebg">促销规则类型</td>			   
			   <td align="center" nowrap class="titlebg">操作</td>			   
			</tr>
			</thead>
			<s:iterator value="promtRuleList"> 
			<tr>
			  <td nowrap>${promtRuleId}</td>
			  <td align="center" nowrap>${amtTypeName}</td>			  
			  <td align="center" nowrap>${fn:amount(amtRef)}</td>
			  <td align="center" nowrap>${promtRuleTypeName}</td>			  
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<a href="javascript:void(0);" onclick="$(this).parent().parent().parent().next().toggle();">明细</a>
			  	</span>
			  </td>
			</tr>
			<tr style="display: none;">
			<td colspan="5">
			<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
				<tr>
					<td>促销规则ID</td>
					<td>${promtRuleId}</td>
					<td>促销活动ID</td>
					<td>${promtId}</td>
					<td>金额类型</td>
					<td>${amtTypeName}</td>
			  	</tr>
			  	<tr>
					<td>参考金额</td>
					<td>${fn:amount(amtRef)}</td>
					<td>促销规则类型</td>
					<td>${promtRuleTypeName}</td>
					<td>金额参数1</td>
					<td>${fn:amount(ruleParam1)}</td>
			  	</tr>
			  	<tr>
					<td>金额参数2</td>
					<td>${fn:amount(ruleParam2)}</td>
					<td>倍率参数</td>
					<td>${ruleParam3}</td>
					<td>积分参数</td>
					<td>${ruleParam4}</td>
			  	</tr>
			  	<tr>
					<td>子类型参数</td>
					<td>${ruleParam5}</td>
					<td>仅生日有效？</td>
					<td>${birthdayFlagName}</td>
					<td></td>
					<td></td>
			  	</tr>
			</table>
			</td>
			</tr>
			</s:iterator>
			</table>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=WorkflowConstants.PROMOTIONS_DEFINE%>"/>
			<jsp:param name="refId" value="${promtDef.promtId}"/>
		</jsp:include>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>