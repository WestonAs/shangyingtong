<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>
<div class="tablebox">
	<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td align="center" nowrap class="titlebg">帐号</td>
	 	  	<td align="center" nowrap class="titlebg">积分类型</td>
			<td align="center" nowrap class="titlebg">可用积分</th>
			<td align="center" nowrap class="titlebg">参考积分</th>
			<td align="center" nowrap class="titlebg">兑换率</th>
			<td align="center" nowrap class="titlebg">操作</th>
		</tr>
		</thead>
		<s:iterator value="pointBalList"> 
			<tr>
				<td align="center" nowrap>${acctId}</td>
				<td align="center" nowrap>${ptClass}</td>
				<td align="center" nowrap>${fn:amount(ptAvlb)}</td>
				<td align="center" nowrap>${fn:amount(ptRef)}</td>
				<td align="center" nowrap>${ptDiscntRate}</td>
				<td align="center" nowrap>
					<span class="redlink">
			  		<f:pspan pid="pointexcmgr_add"><f:link href="/pointExchg/pointExc/showAdd.do?pointExcReg.acctId=${acctId}&pointExcReg.ptClass=${ptClass}&pointExcReg.cardId=${pointExcReg.cardId}">返利</f:link></f:pspan>
			  	</span>
				</td>
			</tr>
		</s:iterator>
	</table>
</div>