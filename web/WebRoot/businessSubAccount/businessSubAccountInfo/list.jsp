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
							<c:if test="${mer ne 'Y'}">
								<td align="right">虚账户体系</td>
								<td>
								<s:select list="#request.asiList" listKey="systemId" listValue="systemName" name="businessSubAccountInfo.systemId" headerKey="" headerValue="--请选择--"></s:select>
								<td align="right">客户编号</td>
								<td><s:textfield name="businessSubAccountInfo.custId"/></td>
							</c:if>	
								<td align="right">状态</td>
								<td>
									<s:select list="#request.acctStates" listKey="value" listValue="name" name="businessSubAccountInfo.state" headerKey="" headerValue="--请选择--"></s:select>
								</td> 
							</tr>
						<tr> 
						<td></td>
							<td height="30" colspan="3">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_ACCOUNT_SYSTEM_INFO_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">账户编号</td>
			   <c:if test="${mer ne 'Y'}">
			       <td align="center" nowrap class="titlebg">客户类型</td>
			   	   <td align="center" nowrap class="titlebg">客户编号/名称</td>
			   </c:if>
			   <td align="center" nowrap class="titlebg">所属虚户体系</td>
			   <td align="center" nowrap class="titlebg">可用余额</td>		
			   <td align="center" nowrap class="titlebg">冻结金额</td>
			   <td align="center" nowrap class="titlebg">状态</td>			   
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap>${accountId}</td>
			  <c:if test="${mer ne 'Y'}">
			      <td align="center" nowrap>
				  	<s:if test="subAccountType == 0">商户</s:if>
				  	<s:else>机构</s:else>
				  </td>
				  <td align="center" nowrap>${custId}/${custName}</td>
			  </c:if>
			  <td align="center" nowrap>${systemName}</td>
			  <td align="center" nowrap>${fn:amount(usableBalance)}</td>
			  <td align="center" nowrap>${fn:amount(freezeCashAmount)}</td>
			  <td align="center" nowrap>${stateName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/businessSubAccount/businessSubAccountInfo/detail.do?businessSubAccountInfo.accountId=${accountId}">查看</f:link>
			  		<c:if test="${'Y' eq canBind && '00' eq state}">
			  			<f:link href="/businessSubAccount/businessSubAccountInfo/showBankAcct.do?businessSubAccountInfo.accountId=${accountId}">实体账户</f:link>
			  		</c:if>
			  		<c:if test="${'Y' eq mySystem}">
			  			<c:if test="${'00' eq state}">
			  				<f:link href="/businessSubAccount/businessSubAccountInfo/changeState.do?businessSubAccountInfo.accountId=${accountId}&businessSubAccountInfo.state=04">冻结</f:link>
			  			</c:if>
			  			<c:if test="${'04' eq state}">
			  				<f:link href="/businessSubAccount/businessSubAccountInfo/changeState.do?businessSubAccountInfo.accountId=${accountId}&businessSubAccountInfo.state=00">解冻</f:link>
			  			</c:if>
			  			<c:if test="${'01' eq state}">
			  				<f:link href="/businessSubAccount/businessSubAccountInfo/showCheck.do?businessSubAccountInfo.accountId=${accountId}">审核</f:link>
			  			</c:if>
			  		</c:if>
			  		<!-- 审核不通过且为登录用户的账户 -->
			  		<c:if test="${('05' eq state) and ('Y' eq myAccount)}">
		  				<f:link href="/businessSubAccount/businessSubAccountInfo/delete.do?businessSubAccountInfo.accountId=${accountId}">删除</f:link>
		  			</c:if>
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