<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
<thead>
<tr>
   <td align="center" nowrap class="titlebg">选择</td>
   <td align="center" nowrap class="titlebg">银行行别</td>
   <td align="center" nowrap class="titlebg">银行行号</td>
   <td align="center" nowrap class="titlebg">银行名称</td>
</tr>
</thead>
<s:iterator value="page.data">
<tr>
  <td><label><input <s:if test="!radio">type="checkbox"</s:if><s:else>type="radio"</s:else> id="e_${bankNo}" name="ids" value="${bankNo}" onclick="Popupselector.addSelect('${bankNo}','${bankName}');"/></label></td>
  <td align="center" nowrap>${bankTypeName}</td>
  <td align="center" nowrap>${bankNo}</td>
  <td nowrap>${bankName}</td>
</tr>
</s:iterator>
</table>
<f:pagebutton name="page" href="javascript:searchBank('#');"/>
