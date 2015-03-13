<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
	<thead>
		<tr>
			   <td align="center" nowrap class="titlebg">
					<label><s:if test="!radio" ><input id="selectAll" type="checkbox" onclick="Popupselector.addAllSelect(this, 'ids')" /></s:if>选择</label>
			   </td>
			   <td align="center" nowrap class="titlebg">终端编号</td>
			   <td align="center" nowrap class="titlebg">商户</td>
			   <td align="center" nowrap class="titlebg">输入方式</td>
			   <td align="center" nowrap class="titlebg">是否单机产品</td>
			   <td align="center" nowrap class="titlebg">状态</td>
		</tr>
	</thead>
	<s:iterator value="page.data">
		<tr>
			  <td><label><input <s:if test="!radio">type="checkbox"</s:if><s:else>type="radio"</s:else> id="e_${termId}" name="ids" value="${termId}" onclick="Popupselector.addSelect('${termId}',$('#e_${termId}').val());"/></label></td>
			  <td nowrap>${termId}</td>
			  <td align="center" nowrap>${merchId}-${merchName}</td>
			  <td align="center" nowrap>${entryModeName}</td>
			  <td align="center" nowrap>${singleProductName}</td>
			  <td align="center" nowrap>${posStatusName}</td>
		</tr>
	</s:iterator>
</table>
<f:pagebutton name="page" href="javascript:searchTerminal('#');"/>
