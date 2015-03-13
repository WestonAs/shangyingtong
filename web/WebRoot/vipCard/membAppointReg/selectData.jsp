<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
<thead>
<tr>
   <td align="center" nowrap class="titlebg"><s:if test="!radio" ><input id="selectAll" type="checkbox" onclick="Popupselector.addAllSelect(this, 'ids')" /></s:if>选择</td>
   <td align="center" nowrap class="titlebg">会员ID</td>
   <td align="center" nowrap class="titlebg">会员名称</td>
   <td align="center" nowrap class="titlebg">会员录入批次</td>
   <td align="center" nowrap class="titlebg">会员级别</td>
</tr>
</thead>
<s:iterator value="page.data">
<tr>
  <td><label><input <s:if test="!radio">type="checkbox"</s:if><s:else>type="radio"</s:else> id="e_${membInfoRegId}" name="ids" value="${membInfoRegId}" onclick="Popupselector.addSelect('${membInfoRegId}','${custName}');"/></label></td>
  <td nowrap>${membInfoRegId}</td>
  <td align="center" nowrap>${custName}</td>
  <td align="center" nowrap>${membInfoId}</td>
  <td align="center" nowrap>${membLevel}</td>
</tr>
</s:iterator>
</table>
<f:pagebutton name="page" href="javascript:searchMemb('#');"/>
