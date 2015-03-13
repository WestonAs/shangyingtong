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
			<caption>风险卡地点监控详细信息<span class="caption_title"> | <f:link href="/pages/nonrealtimemonitor/cardarea/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>交易流水</td>
				<td>${cardAreaRisk.transSn}</td>
				<td>卡号ID</td>
				<td>${cardAreaRisk.cardId}</td>

			</tr>
			<tr>
				<td>风险商户号</td>
				<td>${cardAreaRisk.merNo}-${fn:merch(cardAreaRisk.merNo)}</td>
				<td>交易地区码</td>
				<td>${cardAreaRisk.areaCode}</td>

			</tr>
			<tr>
				<td>交易类型</td>
				<td>${cardAreaRisk.transTypeName}</td>
				<td>交易时间</td>
				<td>${cardAreaRisk.rcvTime}</td>

			</tr>
			<tr>
				<td>交易结果</td>
				<td>${cardAreaRisk.proStatus}</td>
				<td>清算日期</td>
				<td>${cardAreaRisk.settDate}</td>

			</tr>
			<tr>
				<td>发卡机构</td>
				<td>${cardAreaRisk.cardIssuer}-${fn:branch(cardAreaRisk.cardIssuer)}</td>
				<td>插入时间</td>
				<td><s:date name="cardAreaRisk.isnertTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<td>备注</td>
				<td>${cardAreaRisk.remark}</td>
				<td></td>
				<td></td>

			</tr>

		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>