<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
<thead>
<tr>
   <td align="center" nowrap class="titlebg">选择</td>
   <td align="center" nowrap class="titlebg">卡类型编号</td>
   <td align="center" nowrap class="titlebg">卡类型名称</td>
   <td align="center" nowrap class="titlebg">卡种</td>
   <td align="center" nowrap class="titlebg">卡BIN号码</td>
</tr>
</thead>
<s:iterator value="page.data">
<tr>
 <td><label><input <s:if test="!radio">type="checkbox"</s:if><s:else>type="radio"</s:else> id="e_${cardSubclass}" name="ids" value="${cardSubclass}" onclick="Popupselector.addSelect('${cardSubclass}','${cardSubclassName}');"/></label></td>
  <td nowrap>${cardSubclass}</td>
  <td align="center" nowrap>${cardSubclassName}</td>
  <td align="center" nowrap>${cardTypeName}</td>
  <td align="center" nowrap>${binNo}</td>
</tr>
</s:iterator>
</table>
<f:pagebutton name="page" href="javascript:searchCardSubclass('#');"/>
