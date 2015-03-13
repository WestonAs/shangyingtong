<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>
<div class="tablebox">
	<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  
			<td align="center" nowrap class="titlebg">规则编号</td>
	 	  	<td align="center" nowrap class="titlebg">积分类型</td>
	 	  	<td align="center" nowrap class="titlebg">积分名称</td>
	 	  	<td align="center" nowrap class="titlebg">赠券类型</td>
	 	  	<td align="center" nowrap class="titlebg">赠券名称</td>
			<td align="center" nowrap class="titlebg">积分参数</th>
			<td align="center" nowrap class="titlebg">兑换赠券</th>
			<td align="center" nowrap class="titlebg">规则状态</th>
		</tr>
		</thead>
		<s:iterator value="pointConsmRuleList"> 
			<tr>
				<td align="center" nowrap>${ptExchgRuleId}</td>
				<td align="center" nowrap>${ptClass}</td>
				<td align="center" nowrap>${pointClassName}</td>
				<td align="center" nowrap>${couponClass}</td>
				<td align="center" nowrap>${couponClassName}</td>
				<td align="center" nowrap>${ptParam}</td>
				<td align="center" nowrap>${ruleParam1}</td>
				<td align="center" nowrap>${ruleStatusName}</td>
			</tr>
		</s:iterator>
	</table>
</div>