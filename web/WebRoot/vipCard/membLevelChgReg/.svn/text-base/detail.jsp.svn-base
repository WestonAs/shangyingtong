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
			<caption>会员级别变更详细信息<span class="caption_title"> | <f:link href="/vipCard/membLevelChgReg/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td width="80" height="30" align="right">会员级别变更ID</td>
				<td>${membLevelChgReg.id}</td>
				<td width="80" height="30" align="right">卡号</td>
				<td>${membLevelChgReg.cardId}</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">原级别</td>
				<td>${membLevelChgReg.origLevel}</td>
				<td width="80" height="30" align="right">新级别</td>
				<td>${membLevelChgReg.curLevel}</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">会员类型</td>
				<td>${membLevelChgReg.membClass}</td>
				<td width="80" height="30" align="right">发卡机构</td>
				<td>${membLevelChgReg.cardIssuer}-${fn:branch(membLevelChgReg.cardIssuer)}</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">状态</td>
				<td>${membLevelChgReg.procStatusName}</td>
				<td width="80" height="30" align="right">备注</td>
				<td>${membLevelChgReg.remark}</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">更新时间</td>
				<td><s:date name="membLevelChgReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td width="80" height="30" align="right">更新人</td>
				<td>${membLevelChgReg.updateBy}</td>
			</tr>
			
		</table>
		</div>
		
		<!-- 明细 -->
		<s:if test="showMembDetail">
		<div class="tablebox">
			<b><s:label>批量会员关联登记簿明细 </s:label></b>
			<form id="id_detail_bat" method="post" action="detail.do?membLevelChgReg.membLevelChgRegId=${membLevelChgRegId}">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">			
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">登记明细ID</td>
			   <td align="center" nowrap class="titlebg">登记ID</td>
			   <td align="center" nowrap class="titlebg">批次号</td>
			   <td align="center" nowrap class="titlebg">关联会员ID</td>
			   <td align="center" nowrap class="titlebg">卡号</td>			   
			   <td align="center" nowrap class="titlebg">发卡机构</td>			   
			   <td align="center" nowrap class="titlebg">状态</td>			  
			</tr>
			</thead>
			<s:iterator value="membAppointDetailRegPage.data"> 
			<tr>
			  <td nowrap>${membAppointDetailRegId}</td>
			  <td align="center" nowrap>${membLevelChgRegId}</td>
			  <td align="center" nowrap>${saleBatchId}</td>
			  <td align="center" nowrap>${membInfoId}</td>			  
			  <td align="center" nowrap>${cardId}</td>
			  <td align="center" nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>
			  <td align="center" nowrap>${status}</td>
			</tr>
			</s:iterator>
			</table>
			</form>
			<f:paginate name="membAppointDetailRegPage"/>
		</div>
		</s:if>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>