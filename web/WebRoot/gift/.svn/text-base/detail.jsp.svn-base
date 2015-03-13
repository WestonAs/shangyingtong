<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="gnete.etc.Constants"%>
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
			<caption>${ACT.name}<span class="caption_title"> | <f:link href="/gift/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>礼品代码</td>
				<td>${giftDef.giftId}</td>
				<td>礼品名称</td>
				<td>${giftDef.giftName}</td>
				<td>礼品简称</td>
				<td>${giftDef.giftChain}</td>
		  	</tr>
		  	<tr>
				<td>联名机构类型</td>
				<td>${giftDef.jinstTypeName}</td>
				<td>联名机构编号</td>
				<td>${giftDef.jinstId}</td>
				<td>联名机构名称</td>
				<td>${fn:branch(giftDef.jinstId)}</td>
		  	</tr>
		  	<tr>
				<td>清算方法</td>
				<td>${giftDef.settMthdName}</td>
				<td>清算金额</td>
				<td>${fn:amount(giftDef.settAmt)}</td>
				<td>兑换分值</td>
				<td>${fn:amount(giftDef.ptValue)}</td>
		  	</tr>
		  	<tr>
		  		<td>积分兑换类型</td>
				<td>${giftDef.ptClass}</td>
				<td>生效日期</td>
				<td>${giftDef.effDate}</td>
				<td>失效日期</td>
				<td>${giftDef.expirDate}</td>
		  	</tr>
		  	<!--<tr>
				<td>礼品用途</td>
				<td>${giftDef.giftUse}</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
		  	</tr>
		  	
		--></table>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=Constants.WORKFLOW_GIFT_DEF%>"/>
			<jsp:param name="refId" value="${giftDef.giftId}"/>
		</jsp:include>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>