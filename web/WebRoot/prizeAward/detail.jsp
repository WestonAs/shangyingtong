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
		
		<!-- 奖品明细 -->
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>奖品明细<span class="caption_title"> | <f:link href="/prizeAward/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>抽奖活动编号</td>
				<td>${awardReg.drawId}</td>
				<td>奖券号</td>
				<td>${awardReg.awdTicketNo}</td>
			</tr>
			<tr>
				<td>卡号</td>
				<td>${awardReg.cardId}</td>
				<td>奖项编号</td>
				<td>${awardReg.prizeNo}</td>
			</tr>
			<tr>
				<td>中奖状态</td>
				<td>${awardReg.awdStatusName}</td>
				<td>交易类型</td>
				<td>${awardReg.transTypeName}</td>
			</tr>
			<tr>
				<td>交易流水</td>
				<td>${awardReg.transSn}</td>
				<td>中奖操作员</td>
				<td>${awardReg.awdOptr}</td>
			</tr>
			<tr>
				<td>兑奖操作员</td>
				<td>${awardReg.exchgOptr}</td>
				<td>中奖时间</td>
				<td><s:date name="awardReg.awdTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<td>兑奖时间</td>
				<td><s:date name="awardReg.exchgTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td></td>
				<td></td>
			</tr> 
		</table>
		</div>

		<!-- 中奖人信息 -->
		<div class="tablebox">
			<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>中奖人信息</caption>	
			<tr>
				<td>中奖人姓名</td>
				<td>${cardExtraInfo.custName}</td>
				<td>联系电话</td>
				<td>${cardExtraInfo.telNo}</td>
			</tr> 
			<tr>
				<td>证件类型</td>
				<td>${cardExtraInfo.credType}</td>
				<td>证件号</td>
				<td>${cardExtraInfo.credNo}</td>
			</tr>
			</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>