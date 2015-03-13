<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ page import="flink.util.Paginater"%>
<%@ include file="/common/taglibs.jsp" %>

<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
<thead>
<tr>
   <td align="center" nowrap class="titlebg">选择</td>
   <td align="center" nowrap class="titlebg">账号ID</td>
   <td align="center" nowrap class="titlebg">卡号</td>
   <td align="center" nowrap class="titlebg">卡种类</td>
   <td align="center" nowrap class="titlebg">状态</td>
   <td align="center" nowrap class="titlebg">生效日期</td>
   <td align="center" nowrap class="titlebg">失效日期</td>
</tr>
</thead>
<s:iterator value="page.data">
<tr>
  <td><label><input <s:if test="!radio">type="radio"</s:if><s:else>type="radio"</s:else> id="e_${cardId}" name="ids" value="${cardId}" onclick="Popupselector.addSelect('${cardId}','${cardId}');"/></label></td>
  <td nowrap>${acctId}</td>
  <td align="center" nowrap>${cardId}</td>
  <td align="center" nowrap>${cardClassName}</td>
  <td align="center" nowrap>${cardStatusName}</td>
  <td align="center" nowrap>${effDate}</td>
  <td align="center" nowrap>${expirDate}</td>
</tr>
</s:iterator>
</table>
<f:pagebutton name="page" href="javascript:searchCard('#');"/>
