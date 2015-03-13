<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>
<table class="form_grid" width="60%" border="0" cellspacing="3" cellpadding="0">
	<tr>
		<td align="center" nowrap class="titlebg">序号</td>
		<td align="center" nowrap class="titlebg">分段金额上限</td>
		<td align="center" nowrap class="titlebg">费率(百分比)</td>
	</tr>
	<s:iterator value="merchFeeTemplateDetailList" status="mcnStuts">
	   <tr id="id_subsection_tr_<s:property value="#mcnStuts.index"/>">
	     <td align="center" class="u_half" >
	     	<s:property value="#mcnStuts.index+1"/>
	     </td>
	     <td align="center" >
		<input id="id_ulMoney_tds_<s:property value="#mcnStuts.index"/>" type="text" name="" value="${ulMoney}" readonly="readonly" class="readonly" />
	  </td>
	     <td align="center" >
		<input id="id_feeRate_tds_<s:property value="#mcnStuts.index"/>" type="text" name="" value="${feeRate}" readonly="readonly" class="readonly" />%
	  </td>
	   </tr>
	</s:iterator>
</table>

