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
		<table class="detail_grid" width="98%" border="1" cellspacing="0"
			cellpadding="1">
			<caption>	历史风险交易监控详细信息<span class="caption_title"> | <f:link href="/pages/nonrealtimemonitor/histrans/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>清算日期</td>
				<td>${trans.settDate}</td>
				<td>商户号</td>
				<td>${trans.merNo}</td>
			</tr>
			<tr>
				<td>商户名称</td>
				<td>${fn:merch(trans.merNo)}</td>
				<td>卡号</td>
				<td>${trans.cardId}</td>
			</tr>
			<tr>
				<td>检索参考号</td>
				<td>${trans.posSn}</td>
				<td>流水号</td>
				<td>${trans.transSn}</td>
			</tr>
			<tr>
				<td>交易日期</td>
				<td><s:date name ="trans.rcvTime" format="yyyy-MM-ddHH:mm:ss" />
				</td>
				<td>交易响应码</td>
				<td>${trans.respCode}</td>
			</tr>
			<tr>
				<td>风险类型</td>
				<td>${trans.riskTypeName}</td>
				<td>风险标志</td>
				<td>${trans.riskFlagName}</td>
			</tr>
			<tr>
				<td>交易金额</td>
				<td>${trans.transAmt}</td>
				<td>累计交易笔数</td>
				<td>${trans.tradeCnt}</td>
			</tr>
			<tr>
				<td>累计交易金额</td>
				<td>${trans.amount}</td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>