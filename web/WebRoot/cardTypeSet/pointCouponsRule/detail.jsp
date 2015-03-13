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
			<caption>商圈积分赠券控制规则详细信息<span class="caption_title"> | <f:link href="/cardTypeSet/pointCouponsRule/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>类型ID</td>
				<td>${merchGroupPointCouponLimit.limitId}-${merchGroupPointCouponLimit.ptName}${merchGroupPointCouponLimit.coupnName}</td>
				<td>限制类型</td>
				<td>${merchGroupPointCouponLimit.limitTypeName}</td>

			</tr>
			<tr>
				<td>商圈</td>
				<td>${merchGroupPointCouponLimit.groupId}-${merchGroupPointCouponLimit.groupName}</td>
				<td>商户编号</td>
				<td>${merchGroupPointCouponLimit.merchId}-${fn:merch(merchGroupPointCouponLimit.merchId)}</td>

			</tr>
			<tr>
				<td>赠送标志</td>
				<td>${merchGroupPointCouponLimit.sendFlagName}</td>
				<td>消费标志</td>
				<td>${merchGroupPointCouponLimit.consumeFlagName}</td>

			</tr>
			<tr>
				<td>状态</td>
				<td>${merchGroupPointCouponLimit.statusName}</td>
				<td>插入时间</td>
				<td><s:date name="merchGroupPointCouponLimit.insertTime" format="yyyy-MM-dd HH:mm:ss" /></td>

			</tr>
			<tr>
				<td>更新时间</td>
				<td><s:date name="merchGroupPointCouponLimit.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td>更新用户名</td>
				<td>${merchGroupPointCouponLimit.updateBy}</td>

			</tr>
			<tr>
				<td>备注</td>
				<td>${merchGroupPointCouponLimit.remark}</td>
				<td></td>
				<td></td>

			</tr>

		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>