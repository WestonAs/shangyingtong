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
							<td width="80" height="30" align="right">发卡机构或者商户</td>
							<td><s:textfield name="insBankacct.insName" /></td>
							<td width="80" height="30" align="right">银行账户类型</td>
							<td>
								<s:select name="insBankacct.bankAcctType" list="bankAcctTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td width="80" height="30" align="right">开户行账户</td>
							<td><s:textfield name="insBankacct.accNo"/></td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">账户类型</td>
							<td>
								<s:select name="insBankacct.acctType" list="acctTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="bankAcctMgr_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/pages/bankAcct/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_INSBANKACCT_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">机构/商户</td>
			   <td align="center" nowrap class="titlebg">机构类型</td>
			   <td align="center" nowrap class="titlebg">银行账户类型</td>
			   <td align="center" nowrap class="titlebg">账户类型</td>
			   <td align="center" nowrap class="titlebg">开户行账户</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="left" nowrap>${insCode}-${fn:branch(insCode)}${fn:merch(insCode)}</td>
			  <td align="center" nowrap>${typeName}</td>
			  <td align="center" nowrap>${bankAcctTypeName}</td>
			  <td align="center" nowrap>${acctTypeName}</td>
			  <td align="center" nowrap>${accNo}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pages/bankAcct/detail.do?insCode=${insCode}&bankAcctType=${bankAcctType}&type=${type}">查看</f:link>
			  	</span>
			  	<span class="redlink">
			  		<f:pspan pid="bankAcctMgr_modify"><f:link href="/pages/bankAcct/showModify.do?insCode=${insCode}&bankAcctType=${bankAcctType}&type=${type}">编辑</f:link></f:pspan>
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