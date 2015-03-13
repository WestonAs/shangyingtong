<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>
<select name="whiteCardInput.cardSubtype" style="width: 165px" class="{required:true}">
	<option value="">-请选择-</option>
<s:iterator value="cardSubtypeList">
	<option value="${cardSubclass}">${cardSubclass}--${cardSubclassName}</option>
</s:iterator>
</select>
<span class="field_tipinfo">若卡子类型为空请先定义</span>
