<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
<thead>
<tr>
   <td align="center" nowrap class="titlebg">选择</td>
   <td align="center" nowrap class="titlebg">地区代码</td>
   <td align="center" nowrap class="titlebg">地区名称</td>
   <td align="center" nowrap class="titlebg">省份</td>
   <td align="center" nowrap class="titlebg">城市</td>
</tr>
</thead>
<s:iterator value="page.data">
<tr>
  <td><label><input <s:if test="!radio">type="checkbox"</s:if><s:else>type="radio"</s:else> id="e_${areaCode}" name="ids" value="${areaCode}" onclick="Popupselector.addSelect('${areaCode}','${areaName}');"/></label></td>
  <td nowrap>${areaCode}</td>
  <td nowrap>${areaName}</td>
  <td align="center" nowrap>${parentName}</td>
  <td align="center" nowrap>${cityName}</td>
</tr>
</s:iterator>
</table>
<f:pagebutton name="page" href="javascript:searchArea('#');"/>
