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
			<caption>单机产品卡样详细信息<span class="caption_title"> | <f:link href="/pages/singleProduct/style/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>卡样编号</td>
				<td>${cardExampleDef.cardExampleId}</td>
				<td>卡样名称</td>
				<td>${cardExampleDef.cardExampleName}</td>
				<td>卡样文件名</td>
				<td>${cardExampleDef.cardExamplePath}</td>
		  	</tr>
		  	<tr>
				<td>运营机构编号</td>
				<td>${cardExampleDef.branchCode}</td>
				<td>运营机构名称</td>
				<td>${fn:branch(cardExampleDef.branchCode)}</td>
				<td>发卡机构</td>
				<td>${cardExampleDef.cardIssuer}-${fn:branch(cardExampleDef.cardIssuer)}</td>
		  	</tr>
		  	<tr>
				<td>状态</td>
				<td>${cardExampleDef.statusName}</td>
				<td>更新人</td>
				<td>${cardExampleDef.updateBy}</td>
				<td>更新时间</td>
				<td><s:date name="cardExampleDef.updateTime" format="yyyy-MM-dd hh:mm:ss"/></td>
		  	</tr>
		</table>
		</div>
		
		<!-- 促销活动范围 -->
		<s:if test="planList.size() > 0">
		<div class="tablebox">
			<b><s:label>关联的套餐列表</s:label></b>
			<table class="" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">套餐编号</td>
			   <td align="center" nowrap class="titlebg">套餐名称</td>
			   <td align="center" nowrap class="titlebg">生效日期</td>
			   <td align="center" nowrap class="titlebg">失效日期</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>		   
			</tr>
			</thead>
			<s:iterator value="planList"> 
			<tr>
			  <td nowrap>${planId}</td>
			  <td align="center" nowrap>${planName}</td>
			  <td align="center" nowrap>${effDate}</td>
			  <td align="center" nowrap>${expirDate}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<a href="javascript:void(0);" onclick="$(this).parent().parent().parent().next().toggle();">明细</a>
			  	</span>
			  </td>
			</tr>
			<tr style="display: none;">
			<td colspan="6">
			<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
				<tr>
					<td>套餐编号</td>
					<td>${planId}</td>
					<td>套餐名称</td>
					<td>${planName}</td>
					<td>支持机型</td>
					<td>${terType}</td>
			  	</tr>
			  	<tr>
					<td>生效日期</td>
					<td>${effDate}</td>
					<td>失效日期</td>
					<td>${expirDate}</td>
					<td>套餐状态</td>
					<td>${statusName}</td>
			  	</tr>
			  	<tr>
					<td>收费标准</td>
					<td>${chargeTypeName}</td>
					<td>收费金额</td>
					<td>${fn:amount(chargeAmt)}</td>
					<td>标准卡制卡卡单价</td>
					<td>${fn:amount(defauleAmt)}</td>
			  	</tr>
			  	<tr>
					<td>自定义卡样制卡单价</td>
					<td>${fn:amount(customAmt)}</td>
					<td>套餐拥有权限</td>
					<td colspan="3">${authority}</td>
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