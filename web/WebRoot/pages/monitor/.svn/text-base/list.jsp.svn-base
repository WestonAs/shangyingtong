<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 transRevocationitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transRevocationitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>${ACT.name}</title>
		 <meta http-equiv="refresh" content="15" URL="#" /> 
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

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
					<s:hidden name="transRevocation.revcSn"></s:hidden>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">系统跟踪号</td>
							<td>
								<s:textfield name="trans.sysTraceNum" />
							</td>
							<td align="right">交易类型</td>
							<td>
								<s:select name="trans.transType" list="transTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_TRANS_MONITOR_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">交易日期</td>
			   <td align="center" nowrap class="titlebg">交易类型</td>
			   <td align="center" nowrap class="titlebg">卡号</td>
			   <td align="center" nowrap class="titlebg">系统跟踪号</td>
			   <td align="center" nowrap class="titlebg">交易金额</td>
			   <td align="center" nowrap class="titlebg">商户号</td>
			   <td align="center" nowrap class="titlebg">终端号</td>
			   <td align="center" nowrap class="titlebg">风控标志</td>
			   <td align="center" nowrap class="titlebg">风控类型</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap>${settDate}</td>
			  <td align="center" nowrap>${transTypeName}</td>
			  <td align="center" nowrap>${cardId}</td>
			  <td align="right" nowrap>${sysTraceNum}</td>
			  <td align="right" nowrap>${fn:amount(transAmt)}</td>	
			  <td align="center" nowrap>${merNo}</td>
			  <td align="center" nowrap>${termlId}</td>
			  <td align="center" nowrap>${riskFlagName}</td>
			  <td align="center" nowrap>${riskTypeName}</td>		  
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>