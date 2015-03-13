<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>
<select name="brushSet.prizeNo" style="width: 165px" class="{required:true}">
	<option value="">-请选择-</option>
<s:iterator value="prizeDefList">
	<option value="${prizeNo}">${prizeNo}--${prizeName}</option>
</s:iterator>
</select>
<span class="field_tipinfo">若抽奖活动名为空请先定义抽奖活动</span>
