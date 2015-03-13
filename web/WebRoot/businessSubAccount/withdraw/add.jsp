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
		function setRecInfo(){
			var accountId = $('#accountId').val();
			if(accountId == "" || accountId == null){
				alert("没有可用的账户");
				return;
			}
			$.ajax({
				 type:'POST',
			     url:CONTEXT_PATH + '/businessSubAccount/withdraw/getBankAccts.do',
			     async:false,
			     data:{accountId:accountId},
			     dataType: 'json',
				 success:function(data) {
			    	 $('#bankCardNo').empty();
			    	 var bankAccts = data.bankAccts;
			    	 for(var i=0; i<bankAccts.length; i++){
			    		 var obj = bankAccts[i];
			    		 var option = "<option value='"+obj.acctNo+"'>"+obj.acctDesc+"</option>";
			    		 $('#bankCardNo').append(option);
			    	 }
			    	 $('#usableAmount').text(data.usableAmount);
				 },
				 error:function(data){   
                     alert("连接服务器失败");
                     flag = 'error';
                 }       
			 });
		}
		$().ready(function(){
			setRecInfo();
		});
		
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
									提现账户
								</td>
								<td>
									<s:select list="#request.subAccts" listKey="accountId" listValue="acctDesc" name="withDrawBill.accountId" onchange="setRecInfo()" id="accountId" cssClass="{required:true}" cssStyle="width:260px;"></s:select>
									<span class="field_tipinfo">请选择需要提现的虚户</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									收款账户
								</td>
								<td>
									<select id="bankCardNo" name="withDrawBill.bankCardNo" class="{required:true}" style="width:260px;"></select>
									<span class="field_tipinfo">请选择收款账户</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									可提金额
								</td>
								<td id="usableAmount">
									
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									提现金额
								</td>
								<td>
									<s:textfield id="amount" name="withDrawBill.amount" cssClass="{required:true,num:true,max:9999999999.99,min:0.01}"></s:textfield>
									<span class="field_tipinfo">请输入正确格式的金额</span>
									<span id="chineseMoney"></span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									备注
								</td>
								<td>
									<s:textfield id="remark" name="withDrawBill.remark" maxlength="32"></s:textfield>
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
									<input type="button" value="返回" name="escape" onclick="gotoUrl('/businessSubAccount/withdraw/list.do')" class="ml30" />
								</td>
							</tr>
						</table>
						<s:token name="_TOKEN_WITHDRAW_ADD" />
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>