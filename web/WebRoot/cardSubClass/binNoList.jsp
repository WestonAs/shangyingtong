<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>
<select name="cardSubClassDef.binNo" style="width: 165px" class="{required:true}" onmouseover="FixWidth(this);">
	<s:iterator value="cardBinList">
		<option value="${binNo}">${binNo}-${binName}</option>
	</s:iterator>
</select>
<span class="field_tipinfo">卡BIN不为空</span>
