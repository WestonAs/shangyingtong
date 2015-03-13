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
		<f:js src="/js/paginater.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
						
		<div class="userbox">
			<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
				<caption>详细信息<span class="caption_title"> | <f:link href="/intgratedService/cardSmsRecord/list.do?goBack=goBack">返回列表</f:link></span></caption>

				<tr>
					<td>ID</td>
					<td>${cardSmsRecord.id}</td>
					<td>发卡机构</td>
					<td>${cardSmsRecord.issNo}-${fn:branch(cardSmsRecord.issNo)}</td>
					<td>手机号</td>
					<td>${cardSmsRecord.mobile}</td>
					<td>状态</td>
					<s:set name="flagMap" value='#{"1":"待处理", "0":"待发送", "2":"已发送", "3":"发送失败"}' />
					<td><s:property value="#flagMap[cardSmsRecord.flag]"/></td>
			  	</tr>
				<tr>
					<td>更新人</td>
					<td>${cardSmsRecord.updateBy}</td>
					<td>插入时间</td>
					<td><s:date name="cardSmsRecord.insertTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>发送时间</td>
					<td><s:date name="cardSmsRecord.sendTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td></td>
					<td></td>
			  	</tr>
				<tr>
					<td>短信发送内容</td>
					<td colspan="10"><s:property value="cardSmsRecord.smsSendContent" /></td>
			  	</tr>
			</table>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>