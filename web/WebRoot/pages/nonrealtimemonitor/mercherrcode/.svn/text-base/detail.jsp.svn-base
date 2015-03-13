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
			<caption>商户错误码监控详细信息<span class="caption_title"> | <f:link href="/pages/nonrealtimemonitor/mercherrcode/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>风险商户号</td>
				<td>${merchRespCode.merNo}-${fn:merch(merchRespCode.merNo)}</td>
				<td>清算日期</td>
				<td>${merchRespCode.settDate}</td>

			</tr>
			<tr>
				<td>返回码</td>
				<td>${merchRespCode.respCode}</td>
				<td>交易笔数</td>
				<td>${merchRespCode.transNum}</td>

			</tr>
			<tr>
				<td>插入时间</td>
				<td><s:date name="merchRespCode.insertTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td></td>
				<td></td>

			</tr>

		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>