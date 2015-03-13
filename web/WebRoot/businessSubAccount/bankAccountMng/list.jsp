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
							<td class="formlabel">账号</td>
							<td><s:textfield name="bankAcct.acctNo" maxlength="32"></s:textfield> </td>
							<td class="formlabel">户名</td>
							<td><s:textfield name="bankAcct.acctName" maxlength="32"></s:textfield> </td>
						<tr> 
						<td></td>
							<td height="30" colspan="3">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="bank_acct_add">
									<input type="button" value="新增" id="input_btn2"  name="ok" style="margin-left:30px;" onclick="javascript:gotoUrl('/businessSubAccount/bankAccountMng/showAdd.do');"/>
								</f:pspan>
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
						<td align="center" nowrap class="titlebg">
							账号
						</td>
						<td align="center" nowrap class="titlebg">
							户名
						</td>
						<td align="center" nowrap class="titlebg">
							开户行
						</td>
						<td align="center" nowrap class="titlebg">
							开户行所在地
						</td>
						<td align="center" nowrap class="titlebg">
							操作
						</td>
					</tr>
				</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap>${acctNo}</td>
			  <td align="center" nowrap>${acctName}</td>
			  <td align="center" nowrap>${bankName}</td>
			  <td align="center" nowrap>${bankAdd}</td>
			  <td align="center" class="redlink">
			  <!-- 商户对自已的实体账户有修改权限 -->
			    <f:link href="/businessSubAccount/bankAccountMng/detail.do?bankAcct.acctNo=${acctNo}">查看</f:link>
			  	<f:pspan pid = "bank_acct_update" >
			  		<f:link href="/businessSubAccount/bankAccountMng/showModify.do?bankAcct.acctNo=${acctNo}">修改</f:link>
			  	</f:pspan>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>