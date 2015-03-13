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

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			/*var showCardCell = $('#id_showCardCell').val();
			if(showCardCell=='true'){
				$('#id_cardId_tr').show();
				$('#id_cardId').removeAttr('disabled');
				$('#id_acctId_tr_1').show();
				$('#id_acct_td_1').show();
				$('#id_acctId_1').removeAttr('disabled');
				$('#id_acctId_tr_2').hide();
				$('#id_acct_td_2').hide();
				$('#id_acctId_2').attr('disabled',true);
			}
			$('#id_acctId_2').blur(checkAcctId);
			$('#idPassword').change(validatePassword);*/
			$('#id_cardId').blur(checkCardId);
		});
		
		function checkCardId(){
			var cardId = $('#id_cardId').val();
			
			if(isEmpty(cardId)){
				return;
			}
			$.post(CONTEXT_PATH + '/intgratedService/cancelCard/checkCardId.do', {'cancelCardReg.cardId':cardId}, 
				function(json){
					if (json.success){
						//$('#id_acctId_1').val(json.resultAcctId);
						$('#id_returnAmt').val(json.returnAmt);
						$(':submit').removeAttr('disabled');
					} else {
						showMsg(json.error);
						$(':submit').attr('disabled', 'true');
					}
				}, 'json');
		}
		
		function checkAcctId(){
			var acctId = $('#id_acctId_2').val();
			if(!checkCardNum(acctId)){
				return false;
			}
			$.post(CONTEXT_PATH + '/intgratedService/cancelCard/checkAcctId.do', {'cancelCardReg.acctId':acctId}, 
				function(json){
					if (json.success){
						$('#id_acctId_1').val(json.resultAcctId);
						$(':submit').removeAttr('disabled');
					} else {
						showMsg(json.error);
						$(':submit').attr('disabled', 'true');
					}
				}, 'json');
		}

		function validatePassword(){
			var showCardCell = $('#id_showCardCell').val();
			var cardId = "";
			var acctId = "";
			// 售卡代理只能退卡
			if(showCardCell=='true'){
				cardId = $('#id_cardId').val();
				if(isEmpty(cardId)){
					showMsg("请先输入卡号。");
					//$('#idPassword').val('');
					return;
				}
			}
			else{
				var flag = $('#idFlagType2').val();
				// 退卡
				if(flag == '00'){
					cardId = $('#id_cardId').val();
					if(isEmpty(cardId)){
					showMsg("请先输入卡号。");
					//$('#idPassword').val('');
					return;
				}
				}
				// 销户
				else if(flag == '01'){
					acctId = $('#id_acctId_2').val();
					if(isEmpty(acctId)){
						showMsg("请先输入账号。");
						//$('#idPassword').val('');
						return;
					}
				}
				else {
					showMsg("请先选择类型。");
					//$('#idPassword').val('');
					return;
				}
			}
			/*var password = $('#idPassword').val();
			if((trim(password)).length<6){
				showMsg("请先输入至少6位密码。");
				return;
			}
			$.post(CONTEXT_PATH + '/intgratedService/cancelCard/validatePassword.do', {'password':password, 'cancelCardReg.cardId':cardId, 'cancelCardReg.acctId':acctId}, 
				function(json){
					if (json.success){
						showMsg("密码正确");
						$(':submit').removeAttr('disabled');
					} else {
						showMsg(json.error);
						$(':submit').attr('disabled', 'true');
					}
				}, 'json');*/
		}
		
		function changeFlag(){
			var flag = $('#idFlagType2').val();
			// 退卡
			if(flag=='00'){
				$('#id_cardId_tr').show();
				$('#id_cardId').removeAttr('disabled');
				$('#id_acctId_tr_1').show();
				$('#id_acct_td_1').show();
				$('#id_acctId_1').removeAttr('disabled');
				$('#id_acctId_tr_2').hide();
				$('#id_acct_td_2').hide();
				$('#id_acctId_2').attr('disabled',true);
			}
			// 销户
			else if(flag=='01'){
				$('#id_cardId_tr').hide();
				$('#id_cardId').attr('disabled',true);
				$('#id_acctId_tr_1').hide();
				$('#id_acct_td_1').hide();
				$('#id_acctId_1').attr('disabled',true);
				$('#id_acctId_tr_2').show();
				$('#id_acct_td_2').show();
				$('#id_acctId_2').removeAttr('disabled');
			}
			else {
				$('#id_cardId_tr').hide();
				$('#id_cardId').attr('disabled',true);
				$('#id_acctId_tr_1').hide();
				$('#id_acct_td_1').hide();
				$('#id_acctId_1').attr('disabled',true);
				$('#id_acctId_tr_2').hide();
				$('#id_acct_td_2').hide();
				$('#id_acctId_2').attr('disabled',true);
			}
			
		}
		
		function doConfirm(){
			var value = $('#cardId').val();
			var custName = $('#idCustName').val();
			var certType = $('#idCertType').val();
			var certNo = $('#idCertNo').val();
			var custNameHidden = $('#idCustName_hidden').val();
			var certTypeHidden = $('#idCertType_hidden').val();
			var certNoHidden = $('#idCertNo_hidden').val();
			
			if(isEmpty(custNameHidden) && isEmpty(certTypeHidden) && isEmpty(certNoHidden)){
				return confirm("卡号持卡人未保存姓名、证件信息，是否继续？");
			} else {
				if(!isEmpty(custNameHidden)&&custName != custNameHidden){
					return confirm('持卡人姓名不符，是否继续？');
				}
				if(!isEmpty(certTypeHidden)&&certType != certTypeHidden){
					return confirm('持卡人证件类型不符，是否继续？');
				}
				if(!isEmpty(certNoHidden)&&certNo != certNoHidden){
					return confirm('持卡人证件号码不符，是否继续？');
				}
			}
			
			return true;
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
					<s:hidden id="id_showCardCell" name="showCardCell"></s:hidden>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						
						<%--
						<tr>
							<td width="80" height="30" align="right">类型</td>
							<td>
								<s:if test="showCardCell">
								<s:select id="idFlagType1" name="cancelCardReg.flag" list="flagList" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								</s:if>
								<s:else>
								<s:select id="idFlagType2" onchange="changeFlag()" name="cancelCardReg.flag" headerKey="" headerValue="--请选择--" list="flagList" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								</s:else>
								<span class="field_tipinfo">请选择类型</span>
							</td>
						</tr>
						--%>
						<tr>
							<td width="80" height="30" align="right">卡号</td>
							<td>
							<s:textfield id="id_cardId" name="cancelCardReg.cardId" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" 
							cssClass="{required:true}" maxlength="19" ></s:textfield>
							</td>
						</tr>
						<%--<tr id="id_acctId_tr_1" style="display: none">
							<td width="80" height="30" align="right">账号</td>
							<td id="id_acct_td_1" style="display: none">
							<s:textfield id="id_acctId_1" name="cancelCardReg.acctId" cssClass="readonly" readonly="true" disabled="true"></s:textfield>
							</td>
						</tr>
						<tr id="id_acctId_tr_2" style="display: none">
							<td width="80" height="30" align="right">账号</td>
							<td id="id_acct_td_2" style="display: none">
							<s:textfield id="id_acctId_2" name="cancelCardReg.acctId" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
							cssClass="{required:true, minlength:19}" maxlength="19" disabled="true"></s:textfield>
							<span class="field_tipinfo">请输入19位账号</span>
							</td>
						</tr>
						--%><tr>
							<td width="80" height="30" align="right">持卡人姓名</td>
							<td>
							<s:textfield id="idCustName" name="cancelCardReg.custName" ></s:textfield>
								<span class="field_tipinfo">请输入持卡人姓名</span>
							</td>
						</tr>	
						<%--<tr>
							<td width="80" height="30" align="right">查询密码</td>
							<td>
								<s:password id="idPassword" name="password" cssClass="{required:true, minlength:6}"/>
								<span id="id" class="field_tipinfo">验证密码</span>
							</td>
						</tr>
						--%>
						<tr>
							<td width="80" height="30" align="right">证件类型</td>
							<td>
								<s:select id="idCertType" name="cancelCardReg.certType" list="certTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select>
								<span class="field_tipinfo">请选择证件类型</span>
							</td>
						</tr>
						<tr>	
							<td width="80" height="30" align="right">证件号码</td>
							<td><s:textfield id="idCertNo" name="cancelCardReg.certNo" />
								<span class="field_tipinfo">请输入证件号码</span>
						</tr>
						<tr>
							<td width="80" height="30" align="right">手续费</td>
							<td>
							<s:textfield name="cancelCardReg.expenses" cssClass="{required:true, decimal:'14,4'}"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">返还金额</td>
							<td>
							<s:textfield id="id_returnAmt" name="cancelCardReg.returnAmt" cssClass="{required:true} readonly" readonly="true"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td>
							<s:textfield name="cancelCardReg.remark" />
								<span class="field_tipinfo">请输入备注</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/intgratedService/cancelCard/list.do?goBack=goBack')" class="ml30" />
								<s:hidden id="idCustName_hidden" />
								<s:hidden id="idCertType_hidden" />
								<s:hidden id="idCertNo_hidden" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CANCELCARD_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>