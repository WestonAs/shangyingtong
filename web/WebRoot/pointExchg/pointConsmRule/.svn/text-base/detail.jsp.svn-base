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
			<caption>${ACT.name}<span class="caption_title"> | <f:link href="/pointExchg/pointConsmRule/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>规则编号</td>
				<td>${pointConsmRuleDef.ptExchgRuleId}</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>积分类型</td>
				<td>${pointConsmRuleDef.ptClass}</td>
				<td>积分名称</td>
				<td>${ptClassDef.className}</td>
			</tr>
			<tr>
				<td>赠券类型</td>
				<td>${pointConsmRuleDef.couponClass}</td>
				<td>赠券名称</td>
				<td>${cpClassDef.className}</td>
			</tr>
			<tr>
				<td>积分参数</td>
				<td>${fn:amount(pointConsmRuleDef.ptParam)}</td>
				<td>兑换赠券</td>
				<td>${fn:amount(pointConsmRuleDef.ruleParam1)}</td>
			</tr>
			<tr>
				<td>规则状态</td>
				<td>${pointConsmRuleDef.ruleStatusName}</td>
				<td>操作机构</td>
				<td>${fn:branch(pointConsmRuleDef.branchCode)}</td>
			</tr>
		  	<tr>
				<td>更新用户名</td>
				<td>${pointConsmRuleDef.updateBy}</td>
		  		<td>更新时间</td>
				<td><s:date name="pointConsmRuleDef.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>