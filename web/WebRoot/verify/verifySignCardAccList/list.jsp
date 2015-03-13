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
				<s:form action="list.do" id="searchForm" cssClass="validate-tip">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>签单卡账单核销</caption>
						<tr>
							<td width="80" height="30" align="right">卡号</td>
							<td>
								<s:textfield  id="cardId" name="card.cardId" ></s:textfield>
							</td>
							<td width="80" height="30" align="right">月份</td>
							<td>
								<s:textfield id="accMonth" name="card.accMonth"></s:textfield>
							</td>
						</tr>
						<tr>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" class="ml30"/>
								<input type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" class="ml30"/>
							</td>
						</tr>
					</table>
					<s:token/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
				<td align="center" nowrap class="titlebg">卡号</td>
				<td align="center" nowrap class="titlebg">账单月份</td>
				<td align="center" nowrap class="titlebg">签单客户ID</td>
				<td align="center" nowrap class="titlebg">本期账单额</td>
				<td align="center" nowrap class="titlebg">年费</td>
				<td align="center" nowrap class="titlebg">还款到期日期</td>
				<td align="center" nowrap class="titlebg">还款金额</td>
				<td align="center" nowrap class="titlebg">还款日期</td>
				<td align="center" nowrap class="titlebg">状态</td>
				<td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap>${cardId}</td>
			  <td align="center" nowrap>${accMonth}</td>
			  <td align="center" nowrap>
			  	${signCustId}
			  </td>
			  <td align="center" nowrap>
			  	${fn:amount(curAmt)}
			  </td>
			  <td align="center" nowrap>
			  	${fn:amount(yearAmt)}
			  </td>
			  <td align="center" nowrap>
			  	${expDate}
			  	<s:if test="expFlag==1">
			  		<font color="red">(已到期)</font>
			  	</s:if>
			  </td>
			  <td align="center" nowrap>
			  	${fn:amount(payAmt)}
			  </td>
			  <td align="center" nowrap>
			  	${payDate}
			  </td>
			  <td align="center" nowrap>${stateName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
				  	<f:link href="/verify/verifySignCardAccList/detail.do?card.accMonth=${accMonth}&card.cardId=${cardId}">查看</f:link>
				  	<s:if test="status==00">
					  	<span class="redlink">
					  		<f:pspan pid="verifysigncardacclist_verify"><f:link href="/verify/verifySignCardAccList/showVerify.do?accMonth=${accMonth}&cardId=${cardId}">核销</f:link></f:pspan>
					  	</span>
					</s:if>
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