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
			<caption>单机产品套餐详细信息<span class="caption_title"> | <f:link href="/pages/singleProduct/model/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>套餐编号</td>
				<td>${planDef.planId}</td>
				<td>套餐名称</td>
				<td>${planDef.planName}</td>
				<td>支持机型</td>
				<td>${planDef.terType}</td>
		  	</tr>
		  	<tr>
				<td>生效日期</td>
				<td>${planDef.effDate}</td>
				<td>失效日期</td>
				<td>${planDef.expirDate}</td>
				<td>套餐状态</td>
				<td>${planDef.statusName}</td>
		  	</tr>
		  	<tr>
				<td>收费标准</td>
				<td>${planDef.chargeTypeName}</td>
				<td>收费金额</td>
				<td>${fn:amount(planDef.chargeAmt)}</td>
				<td>标准卡制卡卡单价</td>
				<td>${fn:amount(planDef.defauleAmt)}</td>
		  	</tr>
		  	<tr>
				<td>自定义卡样制卡单价</td>
				<td>${fn:amount(planDef.customAmt)}</td>
				<td>套餐拥有权限</td>
				<td colspan="3">${planDef.authority}</td>
		  	</tr>
		  	<tr>
				<td>更新人</td>
				<td>${planDef.updateBy}</td>
				<td>更新时间</td>
				<td colspan="3"><s:date name="planDef.updateTime" format="yyyy-MM-dd hh:mm:ss"/></td>
		  	</tr>
		</table>
		</div>
		
		<!-- 套餐收费子规则 -->
		<s:if test="planSubRuleList.size() > 0">
		<div class="tablebox">
			<b><s:label>套餐收费子规则列表</s:label></b>
			<table class="" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">套餐编号</td>
			   <td align="center" nowrap class="titlebg">周期</td>
			   <td align="center" nowrap class="titlebg">参数（金额或百分比）</td>
			   <td align="center" nowrap class="titlebg">赠送卡数量</td>
			</tr>
			</thead>
			<s:iterator value="planSubRuleList"> 
			<tr>
			  <td nowrap>${planId}</td>
			  <td align="center" nowrap>${period}</td>
			  <td align="center" nowrap>${ruleParam}</td>
			  <td align="center" nowrap>${sendNum}</td>
			</tr>
			</s:iterator>
			</table>
		</div>
		</s:if>
		
		<!-- 套餐关联卡样列表 -->
		<s:if test="cardExampleList.size() > 0">
		<div class="tablebox">
			<b><s:label>套餐关联的卡样列表</s:label></b>
			<table class="" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">卡样编号</td>
			   <td align="center" nowrap class="titlebg">卡样名称</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>		   
			</tr>
			</thead>
			<s:iterator value="cardExampleList"> 
			<tr>
			  <td nowrap>${cardExampleId}</td>
			  <td align="center" nowrap>${cardExampleName}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<a href="javascript:void(0);" onclick="$(this).parent().parent().parent().next().toggle();">明细</a>
			  	</span>
			  </td>
			</tr>
			<tr style="display: none;">
			<td colspan="4">
			<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
				<tr>
					<td>卡样编号</td>
					<td>${cardExampleId}</td>
					<td>卡样名称</td>
					<td>${cardExampleName}</td>
					<td>卡样文件名</td>
					<td>${cardExamplePath}</td>
			  	</tr>
			  	<tr>
					<td>运营机构编号</td>
					<td>${branchCode}</td>
					<td>运营机构名称</td>
					<td>${fn:branch(branchCode)}</td>
					<td>状态</td>
					<td>${statusName}</td>
			  	</tr>
			  	<tr>
					<td>更新人</td>
					<td>${updateBy}</td>
					<td>更新时间</td>
					<td colspan="3"><s:date name="updateTime" format="yyyy-MM-dd hh:mm:ss"/></td>
			  	</tr>
			</table>
			</td>
			</tr>
			</s:iterator>
			</table>
		</div>
		</s:if>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>