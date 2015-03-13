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
			<caption>${ACT.name}<span class="caption_title"> | <f:link href="/couponbatAward/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>批量派赠编号</td>
				<td>${couponBatReg.couponBatRegId}</td>
				<td>派赠方</td>
				<td>${fn:branch(couponBatReg.branchCode)}${fn:merch(couponBatReg.branchCode)}</td>
			</tr>
			<tr>
				<td>发卡机构</td>
				<td>${fn:branch(couponBatReg.cardIssuer)}</td>
				<td>状态</td>
				<td>${couponBatReg.statusName}</td>
			</tr>
			<tr>
				<td>起始卡号</td>
				<td>${couponBatReg.startCard}</td>
				<td>卡数量</td>
				<td>${couponBatReg.cardNum}</td>
			</tr>
			<tr>
				<td>初始面值</td>
				<td>${fn:amount(couponBatReg.faceValue)}</td>
				<td>备注</td>
				<td>${couponBatReg.remark}</td>
			</tr>
			<tr>
				<td>更新用户名</td>
				<td>${couponBatReg.updateBy}</td>
				<td>更新时间</td>
				<td><s:date name="couponBatReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr> 
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>