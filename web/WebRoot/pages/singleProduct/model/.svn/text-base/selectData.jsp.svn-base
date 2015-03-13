<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
<thead>
<tr>
   <td align="center" nowrap class="titlebg">选择</td>
   <td align="center" nowrap class="titlebg">套餐编号</td>
   <td align="center" nowrap class="titlebg">套餐名称</td>
   <td align="center" nowrap class="titlebg">收费标准</td>
   <td align="center" nowrap class="titlebg">收费金额</td>
   <td align="center" nowrap class="titlebg">标准制卡单价</td>
   <td align="center" nowrap class="titlebg">定制制卡单价</td>
</tr>
</thead>
<s:if test="page.data.size() > 0 ">
<s:iterator value="page.data">
<tr>
  <td><label><input <s:if test="!radio">type="checkbox"</s:if><s:else>type="radio"</s:else> id="e_${planId}" name="ids" value="${planId}" onclick="Popupselector.addSelect('${planId}','${planName}');"/></label></td>
  <td nowrap>${planId}</td>
  <td align="center" nowrap>${planName}</td>
  <td align="center" nowrap>${chargeTypeName}</td>
  <td align="center" nowrap>${chargeAmt}</td>
  <td align="center" nowrap>${defauleAmt}</td>
  <td align="center" nowrap>${customAmt}</td>
</tr>
</s:iterator>
</s:if>
<s:else>
<tr>
	<td colspan="7" style="color: red;font-size: 14;padding-left: 30px;">没有符合条件的数据</td>
</tr>
</s:else>
</table>
<f:pagebutton name="page" href="javascript:searchModel('#');"/>
