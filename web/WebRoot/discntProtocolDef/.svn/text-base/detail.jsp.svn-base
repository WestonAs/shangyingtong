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
			<caption>折扣协议详细信息<span class="caption_title"> | <f:link href="/discntProtocolDef/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>折扣协议号</td>
				<td>${discntProtclDef.discntProtclNo}</td>
				<td>折扣协议名</td>
				<td>${discntProtclDef.discntProtclName}</td>
				<td>联名机构类型</td>
				<td>${discntProtclDef.jinstTypeName}</td>
		  	</tr>
		  	<tr>
				<td>联名机构编号</td>
				<td>${fn:merch(discntProtclDef.jinstId)}${fn:branch(discntProtclDef.jinstId)}</td>
				<td>折扣类型ID - 名称</td>
				<td>${discntProtclDef.discntClass} - ${discntProtclDef.className}</td>
				<td>折扣特征码</td>
				<td>${discntProtclDef.discntLabelCode}</td>
		  	</tr>
		  	<tr>
				<td>折扣率</td>
				<td>${fn:perDecimal(discntProtclDef.discntRate)}</td>
				<td>暗折折扣率</td>
				<td>${fn:perDecimal(discntProtclDef.settDiscntRate)}</td>
				<td></td>
				<td></td>
			</tr>
		  	<tr>
				<td>折扣操作方法</td>
				<td>${discntProtclDef.discntOperMthdName}</td>
				<td>折扣排他标志</td>
				<td>${discntProtclDef.discntExclusiveFlagName}</td>
				<td>纸质协议号</td>
				<td>${discntProtclDef.protclPaperSn}</td>
		  	</tr>
		  	<tr>
				<td>规则状态</td>
				<td>${discntProtclDef.ruleStatusName}</td>
				<td>生效日期</td>
				<td>${discntProtclDef.effDate}</td>
				<td>失效日期</td>
				<td>${discntProtclDef.expirDate}</td>
		  	</tr>
		</table>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>