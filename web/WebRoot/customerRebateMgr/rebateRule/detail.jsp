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
		
		<!-- 返利规则明细 -->
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>返利规则详细信息<span class="caption_title"> | <f:link href="/customerRebateMgr/rebateRule/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>返利规则ID</td>
				<td>${rebateRule.rebateId}</td>
				<td>返利规则名称</td>
				<td>${rebateRule.rebateName}</td>
				<td>计算方式</td>
				<td>${rebateRule.calTypeName}</td>
			</tr>
			<tr>
				<td>发卡机构编号</td>
				<td>${rebateRule.cardBranch}</td>
				<td>发卡机构名称</td>
				<td>${fn:branch(rebateRule.cardBranch)}</td>
				<td>录入机构</td>
				<td>${fn:branch(rebateRule.inputBranch)}</td>
			</tr>
			<s:if test='rebateRule.rebateType=="0"'>
				<tr>
					<td>返利方式</td>
					<td colspan="11">一次性返利</td>
				</tr>
			</s:if>
			<s:else>
				<tr>
					<td>返利方式</td>
					<td colspan="11">周期返利</td>
				</tr>
				<tr>
					<td>周期类型</td>
					<td>${rebateRule.periodTypeDesc}</td>
					
					<td>返利次数（期数）</td>
					<td>${rebateRule.periodNum}</td>

					<td>是否立即返利一次</td>
					<td><s:if test='rebateRule.periodImmedRebate=="1"'>是</s:if><s:else>否</s:else></td>
				</tr>
				<tr>
					<td>分期返利生效时间</td>
					<td><s:date name="rebateRule.periodStartTime" format="yyyyMMdd" /></td>
					
					<td>分期返利失效时间</td>
					<td><s:date name="rebateRule.periodEndTime" format="yyyyMMdd" /></td>

					<td>上期最低交易金额</td>
					<td>${rebateRule.periodTransAmt}</td>
				</tr>
			</s:else>
			<tr>
				<td>状态</td>
				<td>${rebateRule.statusName}</td>
				<td>更新人</td>
				<td>${rebateRule.updateUser}</td>
				<td>更新时间</td>
				<td><s:date name="rebateRule.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>

		<!-- 返利规则分段比例明细 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">返利规则ID</td>
			   <td align="center" nowrap class="titlebg">返利分段号</td>
			   <td align="center" nowrap class="titlebg">分段金额上限</td>
			   <td align="center" nowrap class="titlebg">每张卡返利比%/每张卡返利金额</td>			   
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${rebateId}</td>
			  <td align="center" nowrap>${rebateSect}</td>			  
			  <td align="center" nowrap>${rebateUlimit}</td>
			  <td align="center" nowrap>${rebateRate}</td>			  
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>