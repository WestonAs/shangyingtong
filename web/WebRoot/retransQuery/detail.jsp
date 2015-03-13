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
			<caption>${ACT.name}<span class="caption_title"> | <f:link href="/retransQuery/list.do?goBack=goBack">返回列表</f:link></span></caption>
			
			<tr>
				<td>卡补账ID</td>
				<td>${retransCardReg.retransCardId}</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
		  		<td>卡号</td>
				<td>${retransCardReg.cardId}</td>
				<td>账户</td>
				<td>${retransCardReg.acctId}</td>
			</tr>
			<tr>
				<td>商户号</td>
				<td>${retransCardReg.merchId}</td>
				<td>商户名称</td>
				<td>${fn:merch(retransCardReg.merchId)}</td>
			</tr>
			<tr>
				<td>发起平台</td>
				<td>${retransCardReg.platformName}</td>
				<td>终端号</td>
				<td>${retransCardReg.termId}</td>
			</tr>
			<tr>
				<td>补账金额</td>
				<td>${fn:amount(retransCardReg.amt)}</td>
				<td>先用赠券子账户？</td>
				<td>${retransCardReg.couponName}</td>
		  	</tr>
			<tr>
				<td>发卡机构</td>
				<td>${retransCardReg.cardBranch}-${fn:branch(retransCardReg.cardBranch)}</td>
				<td>发起方编号</td>
				<td>${retransCardReg.initiator}</td>
		  	</tr>
		  	 <tr>
				<td>状态</td>
				<td>${retransCardReg.statusName}</td>
				<td>备注</td>
				<td>${retransCardReg.remark}</td>
			</tr>
			<tr>
				<td>操作员</td>
				<td>${retransCardReg.updateUser}</td>
				<td>操作时间</td>
				<td><s:date name="retransCardReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>