<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>
<div class="tablebox">
	<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
	 	  	<td align="center" nowrap class="titlebg">积分类型</td>
			<td align="center" nowrap class="titlebg">类型名称</th>
			<td align="center" nowrap class="titlebg">联名机构</th>
			<td align="center" nowrap class="titlebg">操作</th>
		</tr>
		</thead>
		<s:iterator value="pointClassDefList"> 
			<tr>
				<td align="center" nowrap>${ptClass}</td>
				<td align="center" nowrap>${className}</td>
				<td align="center" nowrap>${jinstId}</td>
				<td align="center" nowrap>
				<span class="redlink">
			  		<f:pspan pid="pointexcgiftreg_add"><f:link href="/pointExchg/pointExcGift/showAdd.do?giftExcReg.ptClass=${ptClass}&giftExcReg.cardId=${giftExcReg.cardId}">兑换礼品</f:link></f:pspan>
			  	</span>
				</td>
			</tr>
		</s:iterator>
	</table>
</div>