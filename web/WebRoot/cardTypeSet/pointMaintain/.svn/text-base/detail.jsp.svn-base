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
		<script>
			$(function(){
				PointClass.ptUsageForDetail();
			});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>积分费率详细信息<span class="caption_title"> | <f:link href="/cardTypeSet/pointMaintain/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td width="80" height="30" align="right">发卡机构</td>
				<td>${merchPointRate.cardIssuer}</td>
				
				<td width="80" height="30" align="right">商户号码</td>
				<td>${merchPointRate.merNo}</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">积分类型</td>
				<td>${merchPointRate.ptClass}</td>
				
				<td width="80" height="30" align="right">积分返利兑换率</td>
				<td>${merchPointRate.ptDiscntRate}</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">入库时间</td>
				<td><s:date name="merchPointRate.insertTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				
				<td width="80" height="30" align="right">更新时间</td>
				<td><s:date name="merchPointRate.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">备注</td>
				<td>${merchPointRate.remark}</td>
				<td></td>
				<td></td>
			</tr>
		</table>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>