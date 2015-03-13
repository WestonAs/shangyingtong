<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%
	response.setHeader("Cache-Control", "no-cache");
%>
<%@ include file="/common/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp"%>
		<%@ include file="/common/sys.jsp"%>
		<title>${ACT.name}</title>

		<f:css href="/css/page.css" />
		<f:css href="/css/multiselctor.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/plugin/jquery.metadata.js" />
		<f:js src="/js/plugin/jquery.validate.js" />
		<f:js src="/js/plugin/jquery.multiselector.js" />
		<f:js src="/js/date/WdatePicker.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />

		<style type="text/css">
html {
	overflow-y: scroll;
}
</style>
	<script type="text/javascript">
		function selectBank(){
			Selector.selectBank('bankName','bankNo',true);
		}
	</script>
	</head>

	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<div>
						<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
							<caption>
								${ACT.name}
							</caption>
							<tr>
								<td class="formlabel nes">
									账号
								</td>
								<td>
									<s:textfield name="bankAcct.acctNo" cssClass="{digitOrAllowChars:'-',required:true,minlength:10,maxlength:32}" readonly="true"></s:textfield>
									<span class="field_tipinfo">请输入合法的账号</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel nes">
									户名
								</td>
								<td>
									<s:textfield name="bankAcct.acctName" cssClass="{required:true}"></s:textfield>
									<span class="field_tipinfo">请输入户名</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel nes">
									开户行
								</td>
								<td>
									<s:textfield name="bankAcct.bankName" id="bankName" cssClass="{required:true}" onfocus="selectBank()"></s:textfield>
									<s:hidden name="bankAcct.bankNo" id="bankNo"></s:hidden>
									<span class="field_tipinfo">请输入开户行</span>
									<span class="error_tipinfo">开户行不能为空</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									备注
								</td>
								<td>
									<s:textfield name="bankAcct.remark" id="bankName" onclick="selectBank()" maxlength="50"></s:textfield>
								</td>
							</tr>
							<tr>
								<td width="80" height="30" align="right">
									&nbsp;
								</td>
								<td height="30" colspan="3">
									<input type="submit" value="提交" id="input_btn2" name="ok" />
									<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
									<input type="button" value="返回" name="escape" onclick="gotoUrl('/businessSubAccount/bankAccountMng/list.do')" style="margin-left:30px;" />
								</td>
							</tr>
						</table>
						<s:token name="_TOKEN_BANK_INFO_ADD" />
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>