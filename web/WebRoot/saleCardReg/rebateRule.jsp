<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
	<thead>
	<tr>
	   <td align="center" nowrap class="titlebg">请选择</td>
	   <td align="center" nowrap class="titlebg">返利规则ID</td>
	   <td align="center" nowrap class="titlebg">返利规则名称</td>
	   <td align="center" nowrap class="titlebg">计算方式</td>
	</tr>
	</thead>
	<s:if test="rebateRuleList != null && rebateRuleList.size > 0">
	<s:iterator value="rebateRuleList">
	<tr>
	  <td align="center" nowrap>
	  	<input type="radio" name="rebateIdRadio" value="${rebateId}" onclick="calRealAmt(false)"/>
	  	<!-- 返利规则返利模式 0一次性返利，1周期返利 -->
	  	<input id="rebateRuleRebateType${rebateId}" type="hidden" value="${rebateType}" /> 
	  </td>
	  <td align="center" nowrap>${rebateId}</td>
	  <td align="center" nowrap>${rebateName}</td>
	  <td align="center" nowrap>${calTypeName}</td>
	</tr>
	</s:iterator>
	</s:if>
	<s:else>
		<tr>
		  <td align="center" nowrap colspan="6">${message}</td>
		</tr>
	</s:else>
</table>