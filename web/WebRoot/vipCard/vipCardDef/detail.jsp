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
			<caption>${ACT.name}<span class="caption_title"> | <f:link href="/vipCard/vipCardDef/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>会员类型</td>
				<td>${membClassDef.membClass}</td>
				<td>会员名称</td>
				<td>${membClassDef.className}</td>
		  	</tr>
		  	<tr>
				<td>发卡机构</td>
				<td>${fn:branch(membClassDef.cardIssuer)}</td>
				<td>会员级别数</td>
				<td>${membClassDef.membLevel}</td>
		  	</tr>
		  	<tr>
				<td>会员级别名称</td>
				<td>${membClassDef.membClassName}</td>
				<td></td>
				<td></td>
		  	</tr>
		  	<tr>
				<td>会员升级方式</td>
				<td>${membClassDef.membUpgradeMthdName}</td>
				
				<td>会员升级阈值</td>
				<td>${membClassDef.membUpgradeThrhd}</td>
		  	</tr>
		  		
		  	<tr>
				<td>会员保级方式</td>
				<td>${membClassDef.membDegradeMthdName}</td>
				<td>会员保级阈值</td>
				<td>${membClassDef.membDegradeThrhd}</td>
		  	</tr>
		  	<tr>
		  		<td>绝对失效日期</td>
				<td>${membClassDef.mustExpirDate}</td>
				<td>关联外部卡号</td>
				<td>
					<s:set name="externalCardType" value='#{"":"未关联", "1":"手机号码"}'/>
					${externalCardType[membClassDef.reserved1]}
				</td>
				<!--
				<td>审核状态</td>
				<td>${membClassDef.statusName}</td>
		  		-->
		  	</tr>
		  	<tr>
		  		<td>更新用户名</td>
				<td>${membClassDef.updateBy}</td>
		  		<td>更新时间</td>
				<td><s:date name="membClassDef.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				
		  	</tr>
		</table>
		</div><!--
		
		 流程相关信息 
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=Constants.WORKFLOW_MEMB_CLASS_DEF%>"/>
			<jsp:param name="refId" value="${membClassDef.membClass}"/>
		</jsp:include>

		--><jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>