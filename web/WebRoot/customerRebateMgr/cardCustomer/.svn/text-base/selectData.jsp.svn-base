<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
<thead>
<tr>
   <td align="center" nowrap class="titlebg">选择</td>
   <td align="center" nowrap class="titlebg">购卡客户编号</td>
   <td align="center" nowrap class="titlebg">购卡客户名称</td>
   <td align="center" nowrap class="titlebg">发卡机构</td>
   <td align="center" nowrap class="titlebg">返利方式</td>
</tr>
</thead>
<s:iterator value="page.data">
<tr>
  <td><label><input <s:if test="!radio">type="checkbox"</s:if><s:else>type="radio"</s:else> id="e_${cardCustomerId}" name="ids" value="${cardCustomerId}" onclick="Popupselector.addSelect('${cardCustomerId}','${cardCustomerName}');"/></label></td>
  <td nowrap>${cardCustomerId}</td>
  <td align="center" nowrap>${cardCustomerName}</td>
  <td nowrap>${cardBranch}-${fn:branch(cardBranch)}</td>
  <td align="center" nowrap>${rebateTypeName}</td>
</tr>
</s:iterator>
</table>
<f:pagebutton name="page" href="javascript:searchCardCustomerOther('#');"/>
