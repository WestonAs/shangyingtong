<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ page import="flink.util.Paginater"%>
<%@ include file="/common/taglibs.jsp" %>

<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
<thead>
<tr>
   <td align="center" nowrap class="titlebg">选择</td>
   <td align="center" nowrap class="titlebg">商圈代码</td>
   <td align="center" nowrap class="titlebg">商圈名称</td>
   <td align="center" nowrap class="titlebg">商圈类型</td>
   <td align="center" nowrap class="titlebg">创建机构编号</td>
</tr>
</thead>
<s:iterator value="page.data">
<tr>
  <td><label><input <s:if test="!radio">type="checkbox"</s:if><s:else>type="radio"</s:else> id="e_${groupId}" name="ids" value="${groupName}" onclick="Popupselector.addSelect('${groupId}','${groupName}');"/></label></td>
  <td nowrap>${groupId}</td>
  <td align="center" nowrap>${groupName}</td>
  <td align="center" nowrap>${groupTypeName}</td>
  <td align="center" nowrap>${createId}</td>
  <td align="center" nowrap>${statusName}</td>
</tr>
</s:iterator>
</table>
<f:pagebutton name="page" href="javascript:searchMerchGroup('#');"/>
