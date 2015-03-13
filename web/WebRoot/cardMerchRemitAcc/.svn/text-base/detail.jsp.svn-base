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
			<table class="detail_grid" width="80%" border="1" cellspacing="0" cellpadding="1">
				<caption>${ACT.name}<span class="caption_title"> | <f:link href="/cardMerchRemitAcc/list.do?goBack=goBack">返回列表</f:link></span></caption>
				<tr>
					<td>机构编号</td>
					<td>${cardMerchRemitAccount.branchCode}</td>	
					<td>机构名称</td>	
					<td>${cardMerchRemitAccount.branchName}</td>	
				</tr>
				<tr>
					<td>商户编号</td>
					<td>${cardMerchRemitAccount.merchId}</td>	
					<td>商户名称</td>	
					<td>${cardMerchRemitAccount.merchName}</td>	
				</tr>
				<tr>
					<td>商户交易币种</td>
					<td>${cardMerchRemitAccount.curCode}</td>
					<td>划账方式</td>
					<td>${cardMerchRemitAccount.xferTypeName}</td>
				</tr>
				<tr>
					<td>周期日期参数</td>
					<td>${cardMerchRemitAccount.dayOfCycleName}</td>
					<td>金额上限参数</td>
					<td>${fn:amount(cardMerchRemitAccount.ulMoney)}</td>
				</tr>
				<tr>
					<td>生效日期</td>
					<td>${cardMerchRemitAccount.effDate}</td>
					<td>更新用户名</td>
					<td>${cardMerchRemitAccount.updateBy}</td>
				</tr>
				<tr>
					<td>更新时间</td>
					<td><s:date name = "cardMerchRemitAccount.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td></td>	
					<td></td>	
				</tr>
			</table>
			</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>
