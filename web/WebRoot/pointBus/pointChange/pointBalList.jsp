<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>
<div class="tablebox">
	<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
	 	  	<td align="center" nowrap class="titlebg">积分类型</td>
			<td align="center" nowrap class="titlebg">联名机构类型</th>
			<td align="center" nowrap class="titlebg">联名机构</th>
			<td align="center" nowrap class="titlebg">可用积分</th>
			<td align="center" nowrap class="titlebg">操作</th>
		</tr>
		</thead>
		<s:iterator value="pointBalList"> 
			<tr>
				<td align="center" nowrap>${ptClassName}</td>
				<td align="center" nowrap>${jinstTypeName}</td>
				<td align="center" nowrap>${jinstId}</td>
				<td align="right" nowrap>${fn:amount(ptAvlb)}</td>
				<td align="center" nowrap>
					<span class="redlink">
			  		<f:pspan pid="pointchange_add"><f:link href="/pointBus/pointChange/showAdd.do?pointChgReg.acctId=${acctId}&pointChgReg.ptClass=${ptClass}&pointChgReg.cardId=${pointChgReg.cardId}">调整</f:link></f:pspan>
			  	</span>
				</td>
			</tr>
		</s:iterator>
	</table>
</div>