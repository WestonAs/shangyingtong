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
			<caption>会员资料关联详细信息<span class="caption_title"> | <f:link href="/vipCard/membAppointReg/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td width="80" height="30" align="right">会员登记资料关联ID</td>
				<td>${membAppointReg.membAppointRegId}</td>
				<td width="80" height="30" align="right">发卡机构</td>
				<td>${membAppointReg.cardIssuer}-${fn:branch(membAppointReg.cardIssuer)}</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">起始卡号</td>
				<td>${membAppointReg.startCardId}</td>
				<td width="80" height="30" align="right">结束卡号</td>
				<td>${membAppointReg.endCardId}</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">卡号</td>
				<td>${membAppointReg.cardId}</td>
				<td width="80" height="30" align="right">会员登记资料ID</td>
				<td>${membAppointReg.membInfoRegId}</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">更新时间</td>
				<td><s:date name="membAppointReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td width="80" height="30" align="right">更新人</td>
				<td>${membAppointReg.updateBy}</td>
			</tr>
			<tr>
			    <td width="80" height="30" align="right">是否批量</td>
				<td>${membAppointReg.appointTypeName}</td>
				<td width="80" height="30" align="right">会员资料登记批次ID</td>
				<td>${membAppointReg.saleBatchId}</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">备注</td>
				<td>${membAppointReg.remark}</td>
				<td></td>
				<td></td>
			</tr>
		</table>
		</div>
		
		<!-- 明细 -->
		<s:if test="showMembDetail">
		<div class="tablebox">
			<b><s:label>批量会员关联登记簿明细 </s:label></b>
			<form id="id_detail_bat" method="post" action="detail.do?membAppointReg.membAppointRegId=${membAppointRegId}">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">			
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">明细ID</td>
			   <td align="center" nowrap class="titlebg">登记薄ID</td>
			   <td align="center" nowrap class="titlebg">批次号</td>
			   <td align="center" nowrap class="titlebg">关联会员ID</td>
			   <td align="center" nowrap class="titlebg">卡号</td>			   
			   <td align="center" nowrap class="titlebg">发卡机构</td>			   
			   <td align="center" nowrap class="titlebg">状态</td>			  
			</tr>
			</thead>
			<s:iterator value="membAppointDetailRegPage.data"> 
			<tr>
			  <td align="center" nowrap>${membAppointDetailRegId}</td>
			  <td align="center" nowrap>${membAppointRegId}</td>
			  <td align="center" nowrap>${saleBatchId}</td>
			  <td align="center" nowrap>${membInfoId}</td>			  
			  <td align="center" nowrap>${cardId}</td>
			  <td align="center" nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>
			  <td align="center" nowrap>${statusName}</td>
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