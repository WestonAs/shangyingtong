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
			<caption>${ACT.name}<span class="caption_title"> | <f:link href="/pointExchg/pointExcCoupon/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>积分兑换赠券编号</td>
				<td>${pointExcCouponReg.pointExcCouponRegId}</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>卡号</td>
				<td>${pointExcCouponReg.cardId}</td>
				<td>帐户</td>
				<td>${pointExcCouponReg.acctId}</td>
			</tr>
			<tr>
				<td>积分类型</td>
				<td>${pointExcCouponReg.ptClass}</td>
				<td>积分名称</td>
				<td>${pointExcCouponReg.ptName}</td>
			</tr>
			<tr>
				<td>赠券类型</td>
				<td>${pointExcCouponReg.couponClass}</td>
				<td>赠券名称</td>
				<td>${pointExcCouponReg.couponName}</td>
			</tr>
			<tr>
				<td>兑换积分</td>
				<td>${fn:amount(pointExcCouponReg.ptValue)}</td>
				<td>兑换赠券</td>
				<td>${fn:amount(pointExcCouponReg.couponAmt)}</td>
			<tr>
				<td>状态</td>
				<td>${pointExcCouponReg.statusName}</td>
				<td>备注</td>
				<td>${pointExcCouponReg.remark}</td>
			</tr>
		  	<tr>
				<td>更新用户名</td>
				<td>${pointExcCouponReg.updateBy}</td>
		  		<td>更新时间</td>
				<td><s:date name="pointExcCouponReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>