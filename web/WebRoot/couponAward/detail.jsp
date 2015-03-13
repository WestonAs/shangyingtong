<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ page import="flink.util.Paginater"%>
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
		
		<!-- 派赠明细 -->
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>派赠详细信息<span class="caption_title"> | <f:link href="/couponAward/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>派赠编号</td>
				<td>${couponReg.couponRegId}</td>
				<td>卡号</td>
				<td>${couponReg.cardId}</td>
			</tr>
			<tr>
				<td>小票号</td>
				<td>${couponReg.ticketNo}</td>
				<td>交易金额</td>
				<td>${couponReg.transAmt}</td>
			</tr>
			<tr>
				<td>返利金额</td>
				<td>${couponReg.backAmt}</td>
				<td>客户姓名</td>
				<td>${couponReg.custName}</td>
			</tr>		
			<tr>
				<td>联系地址</td>
				<td>${couponReg.address}</td>
				<td>联系电话</td>
				<td>${couponReg.mobileNo}</td>
			</tr>
			<tr>
				<td>更新时间</td>
				<td><s:date name="couponReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td></td>
				<td></td>
			</tr> 
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>