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
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
	
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">
			   		<input type="checkbox" onclick="FormUtils.selectAll(this, 'ids')" />全选
			   </td>
			   <td align="center" nowrap class="titlebg">卡类型</td>
			   <td align="center" nowrap class="titlebg">卡子类型</td>
			   <td align="center" nowrap class="titlebg">入库卡数量</td>
			   <td align="center" nowrap class="titlebg">入库日期</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td><input type="checkbox" name="ids" value="${id}"/></td>
			  <td nowrap>${cardTypeName}</td>
			  <td align="center" nowrap>${cardSubType}</td>
			  <td align="center" nowrap>${inputNum}</td>
			  <td align="center" nowrap>${inputDate}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
		  			<f:link href="/cardStock/whiteCard/checkDetail.do?cardInput.id=${id}">编辑</f:link>
			  	</span>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>
		<f:workflow adapter="whiteCardStockAdapter" returnUrl="/cardStock/whiteCard/checkList.do"><s:token /></f:workflow>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>