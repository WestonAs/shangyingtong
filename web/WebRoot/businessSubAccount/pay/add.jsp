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
	    //根据收款账户体系选择收款账户
		
	    function setPayerBalance(){
			var payerAccountId = $('#payerAccountId').val();
			if(payerAccountId == "" || payerAccountId == null){
				alert("没有可用的账户");
				return;
			}
			$.ajax({
				 type:'POST',
			     url:CONTEXT_PATH + '/businessSubAccount/transfer/getUsableAmount.do',
			     async:false,
			     data:{payerAccountId:payerAccountId},
			     dataType: 'json',
				 success:function(data) {
			    	 $('#usableAmount').text(data.usableAmount);
				 },
				 error:function(data){   
                     alert("连接服务器失败");
                     flag = 'error';
                 }       
			 });
	    }
		$().ready(function(){
			setPayerBalance();
		});
		function selectBank(){
			var bankType = $('#idSelectBankType').val();
			if(bankType == ''){
				alert('请先选择行别');
				return;
			}
			Selector.selectBank('payeeBankName','payeeBankCode',true);
		}
	</script>
	</head>

	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>

		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<div>
						<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
							<caption>
								${ACT.name}
							</caption>
							<tr>
								<td class="formlabel">
									付款账户
								</td>
								<td>
									<s:select list="#request.payerAccts" listKey="accountId" listValue="acctDesc" name="payBill.payerAccountId" id="payerAccountId" cssClass="{required:true}" cssStyle="width:260px;" onchange="setPayerBalance()"></s:select>
									<span class="field_tipinfo">请选择付款虚户</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									收款行别
								</td>
								<td>
									<s:select list="#request.bankTypes" listKey="bankType" listValue="bankTypeName" name="payBill.payeeBankType" cssClass="{required:true}" id="idSelectBankType"></s:select>
									<span class="field_tipinfo">请选择收款行别</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									收款开户行
								</td>
								<td>
									<s:textfield name="payBill.payeeBankName" readonly="true" id="payeeBankName" onfocus="selectBank()"></s:textfield>
									<s:hidden name="payBill.payeeBankCode" id="payeeBankCode"/>
									<span class="field_tipinfo"></span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									收款银行账号
								</td>
								<td>
									<s:textfield name="payBill.payeeAcctNo" id="payeeAcctNo" maxlength="32" cssClass="{required:true,minlength:10,maxlength:32}"></s:textfield>
									<span class="field_tipinfo">请输入收款银行账号</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									收款银行户名
								</td>
								<td>
									<s:textfield name="payBill.payeeAcctName" id="payeeAcctName" maxlength="32" cssClass="{required:true}"></s:textfield>
									<span class="field_tipinfo">请输入收款银行户名</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									可用金额
								</td>
								<td id="usableAmount">
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									支付金额
								</td>
								<td>
									<s:textfield id="amount" name="payBill.amount" cssClass="{required:true,num:true,max:9999999999.99,min:0.01}"></s:textfield>
									<span class="field_tipinfo">请输入正确格式的金额</span>
									<span id="chineseMoney"></span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									备注
								</td>
								<td>
									<s:textfield id="remark" name="payBill.remark" maxlength="32"></s:textfield>
									<span class="field_tipinfo"></span>
								</td>
							</tr>
							<tr>
								<td width="80" height="30" align="right">
									&nbsp;
								</td>
								<td height="30" colspan="3">
									<input type="submit" value="提交" id="input_btn2" name="ok" />
									<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
									<input type="button" value="返回" name="escape" onclick="gotoUrl('/businessSubAccount/pay/list.do')" class="ml30" />
								</td>
							</tr>
						</table>
						<s:token name="_TOKEN_payBill_ADD" />
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>