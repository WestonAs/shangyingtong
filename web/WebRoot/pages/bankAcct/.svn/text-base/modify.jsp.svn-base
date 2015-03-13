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
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/biz/branch/add.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
				<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
						<td width="80" height="30" align="right">机构类型</td>
						<td>
							<s:hidden name="insBankacct.type" cssClass="{required:true}"></s:hidden>
							<s:textfield name="typeName" cssClass="{required:true} readonly" readonly="true"></s:textfield>
						</td>
						</tr>
						<tr>
							<s:if test="insBankacct.type==0">
							<td width="80" height="30" align="right">发卡机构</td>
							</s:if>
							<s:else>
							<td width="80" height="30" align="right">商户</td>
							</s:else>
							<td>
							<s:hidden name="insBankacct.insCode" cssClass="{required:true}" ></s:hidden>
							<s:textfield name="insBankacct.insName" cssClass="{required:true} readonly" readonly="true"></s:textfield>
							</td>
						</tr>
						<tr>
						<td width="80" height="30" align="right">银行账户类型</td>
						<td>
							<s:select id="id_bankAcctType" name="insBankacct.bankAcctType" list="bankAcctTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
						</td>
						</tr>
						<tr>
						<td width="80" height="30" align="right">账户类型</td>
						<td>
							<s:select id="id_acctType" name="insBankacct.acctType" list="acctTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo">请选择公司或者私人类型</span>
						</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">开户行名称</td>
							<td>
								<s:textfield id="idBank_sel" name="insBankacct.bankName" cssClass="{required:true}"/>
								<span class="field_tipinfo">点击选择</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">开户行号</td>
							<td>
								<s:textfield id="idBank" name="insBankacct.bankNo" cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo">点击行名选择</span>
							</td>
						</tr>

						<tr>
							<td width="80" height="30" align="right">账户地区</td>
							<td>
								<s:hidden id="idAccAreaCode" name="insBankacct.accAreaCode" />
								<s:textfield id="idAccAreaCode_sel" name="accAreaName" cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo">点击行名选择</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">账号</td>
							<td>
								<s:textfield name="insBankacct.accNo" cssClass="{required:true, digitOrAllowChars:'-'}" maxlength="32"/>
								<span class="field_tipinfo">为数字或“-”</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">账户户名</td>
							<td>
								<s:textfield id="idAccName" name="insBankacct.accName" maxlength="30" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入户名</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/bankAcct/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_INSBANKACCT_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>