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
		function selPayeeAcct(){
			var payeeSystemId = $('#payeeSystemId').val();
			var payerAccountId = $('#payerAccountId').val();
			$.ajax({
				 type:'POST',
			     url:CONTEXT_PATH + '/businessSubAccount/transfer/getPayeeAccts.do',
			     async:false,
			     data:{payeeSystemId:payeeSystemId,payerAccountId:payerAccountId},
			     dataType: 'json',
				 success:function(data) {
			    	 var payeeAccts = data;
			    	 $('#payeeAccountId').empty();
			    	 for(var i=0; i<payeeAccts.length; i++){
			    		 var obj = payeeAccts[i];
			    		 var option = "<option value='"+obj.accountId+"'>"+obj.accountId+"--"+obj.custName+"</option>";
			    		 $('#payeeAccountId').append(option);
			    	 }
				 },
				 error:function(data){   
                     alert("连接服务器失败");
                     flag = 'error';
                 }       
			 });
		}
	    function setPayeeSystems(){
			var payerAccountId = $('#payerAccountId').val();
			var crossSystem = $('#crossSystem').val();
			$.ajax({
				 type:'POST',
			     url:CONTEXT_PATH + '/businessSubAccount/transfer/getPayeeSystems.do',
			     async:false,
			     data:{payerAccountId:payerAccountId,crossSystem:crossSystem},
			     dataType: 'json',
				 success:function(data) {
			    	 var payeeSystems = data;
			    	 $('#payeeSystemId').empty();
			    	 for(var i=0; i<payeeSystems.length; i++){
			    		 var obj = payeeSystems[i];
			    		 var option = "<option value='"+obj.systemId+"'>"+obj.systemName+"</option>";
			    		 $('#payeeSystemId').append(option);
			    	 }
				 },
				 error:function(data){   
                     alert("连接服务器失败");
                     flag = 'error';
                 }       
			 });
		}
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
			changeRecSystem();
		});
		function changeRecSystem(){
			setPayeeSystems();
			selPayeeAcct();
		}
		function changePayerAcct(){
			changeRecSystem();
			setPayerBalance();
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
									是否跨体系
								</td>
								<td>
									<select id="crossSystem" name="crossSystem" onchange="changeRecSystem()">
										<option value="Y">是</option>
										<option value="N">否</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									付款账户
								</td>
								<td>
									<s:select list="#request.payerAccts" listKey="accountId" listValue="acctDesc" name="transBill.payerAccountId" id="payerAccountId" cssClass="{required:true}" cssStyle="width:260px;" onchange="changePayerAcct()"></s:select>
									<span class="field_tipinfo">请选择付款虚户</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									收款账户所属体系
								</td>
								<td>
									<s:select list="#request.payeeSystems" listKey="systemId" listValue="systemName" id="payeeSystemId" onchange="selPayeeAcct()" cssStyle="width:260px;"></s:select>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									收款账户
								</td>
								<td>
									<select id="payeeAccountId" name="transBill.PayeeAccountId" class="{required:true}" style="width:260px;"></select>
									<span class="field_tipinfo">请选择收款账户</span>
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
									转账金额
								</td>
								<td>
									<s:textfield id="amount" name="transBill.amount" cssClass="{required:true,num:true,max:9999999999.99,min:0.01}"></s:textfield>
									<span class="field_tipinfo">请输入正确格式的金额</span>
									<span id="chineseMoney"></span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									备注
								</td>
								<td>
									<s:textfield id="remark" name="transBill.remark" maxlength="32"></s:textfield>
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
									<input type="button" value="返回" name="escape" onclick="gotoUrl('/businessSubAccount/transfer/list.do')" class="ml30" />
								</td>
							</tr>
						</table>
						<s:token name="_TOKEN_transBill_ADD" />
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>