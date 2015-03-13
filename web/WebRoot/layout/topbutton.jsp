<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<s:if test="menus != null">
	<s:iterator id="menu" value="menus">
		<f:link href="${menu.entry}" onfocus="this.blur()" target="main_area">${menu.name}</f:link>
	</s:iterator>
</s:if>
