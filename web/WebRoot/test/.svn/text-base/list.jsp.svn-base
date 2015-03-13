<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ page import="flink.util.Paginater"%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>

		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="list.do">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="100" height="30" align="right"><s:textfield name="testLeave.leaveTime" /></td>
							<td height="30" colspan="3">
								<input style="margin-left:30px;" type="button" value="录入" onclick="javascript:gotoUrl('/test/showAdd.do');" id="input_btn3"  name="escape" />
							</td>
						</tr>
					</table>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">编号</td>
			   <td align="center" nowrap class="titlebg">名称</td>
			   <td align="center" nowrap class="titlebg">时间</td>
			   <td align="center" nowrap class="titlebg">类型</td>
			</tr>
			</thead>
			<s:iterator value="list"> 
			<tr>
			  <td nowrap>${id}</td>
			  <td align="center" nowrap>${name}</td>
			  <td align="center" nowrap>${leavetime}</td>
			  <td align="center" nowrap><s:if test="state==0">待审核</s:if><s:if test="state==1">已审核</s:if><s:if test="state==2">审核不通过</s:if></td>
			</tr>
			</s:iterator>
			</table>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>