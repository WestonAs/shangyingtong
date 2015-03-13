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
							<td align="right">发卡机构</td>
							<s:if test="showAll">
							<td><s:textfield name="cardRiskBalance.branchName" /></td>
							</s:if>
							<s:else>
							<td><s:select name="cardRiskBalance.branchCode" list="branchList" listKey="branchCode" listValue="branchName"></s:select></td>
							</s:else>
							<td height="30" >
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARD_RISK_QUERY_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">资质信任额度</td>
			   <td align="center" nowrap class="titlebg">风险保证金</td>
			   <td align="center" nowrap class="titlebg">售卡充值总金额</td>			   
			   <td align="center" nowrap class="titlebg">已清算本金总金额</td>
			   <td align="center" nowrap class="titlebg">当前可用充值售卡金额</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td nowrap>${branchCode}-${fn:branch(branchCode)}</td>
			  <td align="right" nowrap>${fn:amount(trustAmt)}</td>
			  <td align="right" nowrap>${fn:amount(riskAmt)}</td>
			  <td align="right" nowrap>${fn:amount(sellAmt)}</td>
			  <td align="right" nowrap>${fn:amount(settleAmt)}</td>
			  <td align="right" nowrap>${fn:amount(availableAmt)}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/cardRisk/cardRiskQuery/cardRiskChgList.do?cardRiskBalance.branchCode=${branchCode}">查看变动记录</f:link>
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