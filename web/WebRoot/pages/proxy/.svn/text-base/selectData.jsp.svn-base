<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ page import="flink.util.Paginater"%>
<%@ include file="/common/taglibs.jsp" %>

<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
<thead>
<tr>
   <td align="center" nowrap class="titlebg">选择</td>
   <td align="center" nowrap class="titlebg">机构编号</td>
   <td align="center" nowrap class="titlebg">机构简称</td>
   <td align="center" nowrap class="titlebg">联系人</td>
   <td align="center" nowrap class="titlebg">联系电话</td>
   <td align="center" nowrap class="titlebg">状态</td>
</tr>
</thead>
<s:iterator value="page.data">
<tr>
  <td><label><input <s:if test="!radio">type="checkbox"</s:if><s:else>type="radio"</s:else> id="e_${branchCode}" name="ids" value="${branchCode}" onclick="Popupselector.addSelect('${branchCode}','${branchAbbname}');"/></label></td>
  <td nowrap>${branchCode}</td>
  <td align="center" nowrap>${branchAbbname}</td>
  <td align="center" nowrap>${contact}</td>
  <td align="center" nowrap>${phone}</td>
  <td align="center" nowrap>${statusName}</td>
</tr>
</s:iterator>
</table>
<f:pagebutton name="page" href="javascript:searchBranch('#');"/>
