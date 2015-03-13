<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
<thead>
<tr>
   <td align="center" nowrap class="titlebg">
		<label><s:if test="!radio" ><input id="selectAll" type="checkbox" onclick="Popupselector.addAllSelect(this, 'ids')" /></s:if>选择</label>
   </td>
   <td align="center" nowrap class="titlebg">卡BIN号码</td>
   <td align="center" nowrap class="titlebg">卡BIN名称</td>
   <td align="center" nowrap class="titlebg">货币代码</td>
   <td align="center" nowrap class="titlebg">卡类型</td>
   <td align="center" nowrap class="titlebg">状态</td>
</tr>
</thead>
<s:iterator value="page.data">
<tr>
  <td>	
	<label>
  		<input <s:if test="!radio">type="checkbox"</s:if><s:else>type="radio"</s:else> id="e_${binNo}" name="ids" value="${binNo}" onclick="Popupselector.addSelect('${binNo}','${binName}');"/>
  	</label>
  </td>
  <td nowrap>${binNo}</td>
  <td align="center" nowrap>${binName}</td>
  <td align="center" nowrap>${currCode}</td>
  <td align="center" nowrap>${cardTypeName}</td>
  <td align="center" nowrap>${statusName}</td>
</tr>
</s:iterator>
</table>
<f:pagebutton name="page" href="javascript:searchCardBin('#');"/>
