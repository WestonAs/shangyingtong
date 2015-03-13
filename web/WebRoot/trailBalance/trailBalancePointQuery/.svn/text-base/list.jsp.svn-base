<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ page import="flink.util.Paginater"%>
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
		<f:js src="/js/date/WdatePicker.js"/>

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
							<s:if test="!showMerch">
							<td align="right">发行机构</td>
							<td><s:textfield name="trailBalancePoint.cardIssuer"/></td>
							</s:if>	
							<td align="right">平衡类型</td>
							<td>
							<s:select name="trailBalancePoint.balanceFlag" list="balanceFlagList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td align="right">试算日期</td>
							<td><s:textfield name="trailBalancePoint.settDate" onclick="WdatePicker()"/></td>	
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_TRAILBALANCEPOINT_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">试算日期</td>
			   <td align="center" nowrap class="titlebg">发行机构</td>
			   <td align="center" nowrap class="titlebg">积分子类型</td>
			   <td align="center" nowrap class="titlebg">变动金额</td>
			   <td align="center" nowrap class="titlebg">上一日积分账户余额</td>
			   <td align="center" nowrap class="titlebg">当前积分账户余额</td>
			   <td align="center" nowrap class="titlebg">平衡标志</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>	
			  <td align="center" nowrap>${settDate}</td>	
			  <td align="center" nowrap>${cardIssuer}-${fn:branch(cardIssuer)}${fn:merch(cardIssuer)}</td>
			  <td align="center" nowrap>${ptClass}-${className}</td>
			  <td align="right" nowrap>${fn:amount(deltaAmt)}</td>
			  <td align="right" nowrap>${fn:amount(lastBal)}</td>
			  <td align="right" nowrap>${fn:amount(curBal)}</td>
			  <td align="center" nowrap>${balanceFlagName}</td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>