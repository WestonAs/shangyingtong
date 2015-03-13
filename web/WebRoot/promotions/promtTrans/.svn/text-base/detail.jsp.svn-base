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
			<caption>促销活动交易明细详细信息<span class="caption_title"> | <f:link href="/promotions/promtTrans/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>交易流水</td>
				<td>${promtSettCostList.transSn}</td>
				<td>促销活动id</td>
				<td>${promtSettCostList.promtId}</td>

			</tr>
			<tr>
				<td>交易类型</td>
				<td>${promtSettCostList.transTypeName}</td>
				<td>活动赠送方</td>
				<td>${promtSettCostList.promtDonateId}</td>

			</tr>
			<tr>
				<td>发卡机构</td>
				<td>${promtSettCostList.cardIssuer}-${fn:branch(promtSettCostList.cardIssuer)}</td>
				<td>商户编号</td>
				<td>${promtSettCostList.merchId}-${fn:merch(promtSettCostList.merchId)}</td>

			</tr>
			<tr>
				<td>通用积分清算资金</td>
				<td>${promtSettCostList.issuerPointSettAmt}</td>
				<td>专属积分清算资金</td>
				<td>${promtSettCostList.merchPointSettAmt}</td>

			</tr>
			<tr>
				<td>通用赠券清算资金</td>
				<td>${promtSettCostList.issuerCouponAmt}</td>
				<td>专属赠券清算资金</td>
				<td>${promtSettCostList.merchCouponAmt}</td>

			</tr>
			<tr>
				<td>清算日期</td>
				<td>${promtSettCostList.settDate}</td>
				<td>状态</td>
				<td>${promtSettCostList.statusName}</td>

			</tr>
			<tr>
				<td>插入时间</td>
				<td><s:date name="promtSettCostList.insertTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td>备注</td>
				<td>${promtSettCostList.remark}</td>

			</tr>

		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>