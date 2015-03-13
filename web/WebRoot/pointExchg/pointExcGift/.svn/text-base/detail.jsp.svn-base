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
			<caption>积分兑换礼品记录详细信息<span class="caption_title"> | <f:link href="/pointExchg/pointExcGift/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>兑换编号</td>
				<td>${giftExcReg.giftExcId}</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>机构类型</td>
				<td>${giftExcReg.jinstTypeName}</td>
				<td>机构编号</td>
				<td>${giftExcReg.jinstId}</td>
			</tr>
			<tr>
				<td>卡号</td>
				<td>${giftExcReg.cardId}</td>
				<td>账号</td>
				<td>${giftExcReg.acctId}</td>
			</tr>
			<tr>
				<td>礼品代码</td>
				<td>${giftExcReg.giftId}</td>
				<td>礼品名称</td>
				<td>${giftExcReg.giftName}</td>
			<tr>
				<td>积分类型</td>
				<td>${giftExcReg.ptClass}</td>
				<td>兑换积分</td>
				<td>${fn:amount(giftExcReg.ptValue)}</td>
			</tr>
			<tr>
				<td>状态</td>
				<td>${giftExcReg.statusName}</td>
				<td>备注</td>
				<td>${giftExcReg.remark}</td>
			</tr>
		  	<tr>
				<td>更新用户名</td>
				<td>${giftExcReg.updateUser}</td>
		  		<td>更新时间</td>
				<td><s:date name="giftExcReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>