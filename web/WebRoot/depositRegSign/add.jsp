<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>

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
		<f:js src="/js/validate.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			$('#cardId').focus();
			$(':submit').attr('disabled', 'true');
			$('#cardId').change(function(){
				var cardId = $(this).val();
				if(!checkCardId(cardId)){ return; }
				if (!validator.isDigit(cardId)) {
					return;
				}
				$.post(CONTEXT_PATH + '/depositRegSign/searchSignCardInfo.do', 
					{'depositReg.cardId':cardId}, 
					function(data){
						if(data.passCheck){
							$('#acctId').val(data.acctId);
							$('#cardClass').val(data.cardClass);
							$('#cardClassName').val(data.cardClassName);
							$('#cardSubClassName').val(data.cardSubClassName);
							$('#sign_CustId').val(data.signCustId);
							$('#signCustIdName').val(data.signCustIdName);
							$('#overdraftId').val(data.overdraft);
							$('#custName').val(data.custName);
							$('#certTypeName').val(data.certTypeName);
							$('#certNo').val(data.certNo);
							$('#avblBal').val(data.avblBal);
							$('#idDepositTbl').show();
							$(':submit').removeAttr('disabled');
						} else {
							showMsg(data.errorMsg);
						}
					}, 'json');
			});
			$('#amt').change(function(){
				var value = $(this).val();
				if(!isEmpty(value)){
					$('#realAmt').val(value);
				}
			});
		});
		
		function checkField(){
			// 卡号有效性检查   （临时处理方式）
			var cardId = $("#cardId").val();
			var signCustId = $("#sign_CustId").val();
			var amt = $("#amt").val();
			var realAmt = $("#realAmt").val();
			
			if(isEmpty(cardId)){
				showMsg("卡号不能为空！");
				return false;
			}
			if(isEmpty(signCustId)){
				showMsg("签单客户不能为空！");
				return false;
			}
			return true;
		}
		
		function submitForm(){
			try{
				if(!checkField()){
					return false;
				}
				$("#inputForm").submit();
				return true;
			}catch(err){
				txt="本页中存在错误。\n\n 错误描述：" + err.description + "\n\n"
				showMsg(txt)
			}	
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
						<caption>${ACT.name}</caption>
						
						<tr>
							<td width="70" height="30" align="right">卡号</td>
							<td>
								<s:textfield id="cardId" name="depositReg.cardId" cssClass="{required:true, digit:true, minlength:19}" maxlength="19"/>
								<span class="field_tipinfo">请输入19位卡号</span>
							</td>
							<td width="90" height="30" align="right">充值金额</td>
							<td>
								<s:textfield id="amt" name="depositReg.amt" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入充值金额</span>
							</td>
						</tr>						
					</table>
					<hr style="width: 98%; margin: 10px 0px 10px 0px;" size="1" />
					<table id="idDepositTbl" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0" style="display: none;">
						<tr>
							<td width="70" height="30" align="right">账户</td>
							<td>
								<s:textfield id="acctId" readonly="true" cssClass="readonly"/>
							</td>
							<td width="70" height="30" align="right">充值类型</td>
							<td>
								<s:textfield id="depositType" name="depositType" value="按金额充值" readonly="true" cssClass="readonly"/>
							</td>
							<td width="70" height="30" align="right">卡类型</td>
							<td>
								<s:textfield id="cardClassName" readonly="true" cssClass="readonly"/>
								<s:hidden id="cardClass" name="depositReg.cardClass"  />
							</td>							
						</tr>
						<tr>
							<td width="70" height="30" align="right">卡子类型</td>
							<td>
								<s:textfield id="cardSubClassName" readonly="true" cssClass="readonly"/>
							</td>
							<td width="70" height="30" align="right">签单客户</td>
							<td>
								<s:textfield id="signCustIdName" readonly="true" cssClass="readonly"/>
								<s:hidden id="sign_CustId" name="depositReg.cardCustomerId"/>
							</td>							
							<td width="70" height="30" align="right">透支金额</td>
							<td>
								<s:textfield id="overdraftId" readonly="true" cssClass="readonly"/>
							</td>							
						</tr>
						<tr>
							<td width="70" height="30" align="right">持卡人</td>
							<td>
								<s:textfield id="custName" name="depositReg.custName" readonly="true" cssClass="readonly"/>
							</td>							
							<td width="70" height="30" align="right">证件名称</td>
							<td>
								<s:textfield id="certTypeName" readonly="true" cssClass="readonly"/>
							</td>							
							<td width="70" height="30" align="right">证件号码</td>
							<td>
								<s:textfield id="certNo" readonly="true" cssClass="readonly"/>
							</td>							
						</tr>
						<tr>
							<td width="70" height="30" align="right">实收金额</td>
							<td>
								<s:textfield id="realAmt" name="depositReg.realAmt" cssClass="{required:true} readonly bigred" readonly="true" />
							</td>
							<td width="70" height="30" align="right">前期余额</td>
							<td>
								<s:textfield id="avblBal" name="depositReg.avblBal" readonly="true" cssClass="readonly"/>
							</td>
							<td width="70" height="30" align="right">备注</td>
							<td>
								<s:textfield id="remark" name="depositReg.remark" />
							</td>
						</tr>
					</table>														
					
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td width="70" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok"  onclick="return submitForm();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/depositRegSign/list.do')" class="ml30" />
							</td>
							<td></td><td></td>
						</tr>
					</table>
					<s:token name="_TOKEN_DEPOSIT_REG_SIGN_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>