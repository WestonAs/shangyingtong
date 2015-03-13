<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
<thead>
<tr>
   <td align="center" nowrap class="titlebg">选择</td>
   <td align="center" nowrap class="titlebg">购卡客户编号</td>
   <td align="center" nowrap class="titlebg">购卡客户名称</td>
   <td align="center" nowrap class="titlebg">返利方式</td>
   <td align="center" nowrap class="titlebg">联系人</td>
   <td align="center" nowrap class="titlebg">联系电话</td>
</tr>
</thead>
<s:if test="page == null || page.data == null || page.data.size() == 0">
<tr><td colspan="6" style="color: red;">${msg}</td></tr>
</s:if>
<s:else>
<s:iterator value="page.data">
<tr>
  <td><label><input <s:if test="!radio">type="checkbox"</s:if><s:else>type="radio"</s:else> id="e_${cardCustomerId}" name="ids" value="${cardCustomerId}" onclick="Popupselector.addSelect('${cardCustomerId}','${cardCustomerName}');"/></label></td>
  <td nowrap>${cardCustomerId}</td>
  <td align="center" nowrap>${cardCustomerName}</td>
  <td align="center" nowrap>${rebateTypeName}</td>
  <td align="center" nowrap>${contact}</td>
  <td align="center" nowrap>${phone}</td>
</tr>
</s:iterator>
</s:else>
</table>
<s:if test="page == null || page.data == null || page.data.size() == 0">
</s:if>
<s:else>
<f:pagebutton name="page" href="javascript:searchCardCustomer('#');"/>
</s:else>