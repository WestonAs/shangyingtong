<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		
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

		<!-- 数据列表区 -->
		<div class="tablebox">
			<form id="id_check_cardBin" method="post" action="/addCardBin/cardBinCheckDetail.do">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
				<td align="center" nowrap class="titlebg"><input type="checkbox" onclick="FormUtils.selectAll(this, 'ids')" />全选</td>
			   <td align="center" nowrap class="titlebg">卡BIN号码</td>
			   <td align="center" nowrap class="titlebg">卡BIN名称</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">货币代码</td>
			   <td align="center" nowrap class="titlebg">卡种</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
				<td><input type="checkbox" name="ids" value="${id}"/></td>
			  <td nowrap>${binNo}</td>
			  <td align="center" nowrap>${binName}</td>
			  <td nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>
			  <td align="center" nowrap>${currCode}</td>
			  <td align="center" nowrap>${cardTypeName}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/addCardBin/cardBinCheckDetail.do?cardBinReg.id=${id}">明细</f:link>
			  	</span>
			  </td>
			</tr>
			</s:iterator>
			</table>
			</form>
			<f:paginate name="page" formIndex="0"/>
		</div>
		
		<f:workflow adapter="cardBinAdapter" returnUrl="/addCardBin/cardBinCheckList.do"><s:token name="_TOKEN_CARDBIN_CHECKLIST"/></f:workflow>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>