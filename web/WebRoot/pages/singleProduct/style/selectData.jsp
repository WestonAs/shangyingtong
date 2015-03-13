<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
<thead>
<tr>
   <td align="center" nowrap class="titlebg">选择</td>
   <td align="center" nowrap class="titlebg">卡样编号</td>
   <td align="center" nowrap class="titlebg">卡样名称</td>
   <td align="center" nowrap class="titlebg">状态</td>
   <td align="center" nowrap class="titlebg">更新人</td>
   <td align="center" nowrap class="titlebg">更新时间</td>
</tr>
</thead>
<s:if test="page.data.size() > 0 ">
<s:iterator value="page.data">
<tr>
  <td><label><input <s:if test="!radio">type="checkbox"</s:if><s:else>type="radio"</s:else> id="e_${cardExampleId}" name="ids" value="${cardExampleId}" onclick="Popupselector.addSelect('${cardExampleId}','${cardExampleName}');"/></label></td>
  <td nowrap>${cardExampleId}</td>
  <td align="center" nowrap>${cardExampleName}</td>
  <td align="center" nowrap>${statusName}</td>
  <td align="center" nowrap>${updateBy}</td>
  <td align="center" nowrap><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss" /> </td>
</tr>
</s:iterator>
</s:if>
<s:else>
<tr>
	<td colspan="6" style="color: red;font-size: 14;padding-left: 30px;">没有符合条件的数据</td>
</tr>
</s:else>
</table>
<f:pagebutton name="page" href="javascript:searchStyle('#');"/>
