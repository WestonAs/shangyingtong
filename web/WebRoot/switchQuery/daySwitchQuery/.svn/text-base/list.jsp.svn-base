<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>${ACT.name}</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/datePicker/WdatePicker.js"/>

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
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">日切状态</td>
							<td>
								<s:select name="daySwitch.switchFlag" list="switchFlagList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td align="right">结算状态</td>
							<td>
								<s:select name="daySwitch.setFlag" list="setFlagList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_DAYSWITCHQUERY_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">日期</td>
			   <td align="center" nowrap class="titlebg">日切状态</td>
			   <td align="center" nowrap class="titlebg">商户手续费计费状态</td>
			   <td align="center" nowrap class="titlebg">结算状态</td>
			   <td align="center" nowrap class="titlebg">日终开始时间</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td align="center" nowrap>${currDate}</td>
			  <td align="center" nowrap>${switchFlagName}</td>
			  <td align="center" nowrap>${feeFlagName}</td>
			  <td align="center" nowrap>${setFlagName}</td>
			  <td align="center" nowrap><s:date name="strTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/switchQuery/daySwitchQuery/detail.do?daySwitch.currDate=${currDate}">查看</f:link>
			  	</span>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>