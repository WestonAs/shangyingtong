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

		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
			$(function(){
				$('#Id_SignCust').change(function(){
					var value = $(this).val();
					if(value != null && value != undefined){
						$('#Id_signRule').load(CONTEXT_PATH + '/signCardMgr/saleSignCardReg/getSignRuleList.do', {'signCustId':value,'callId':callId()})
					}
				});
				$('#cardId').blur(function(){
					//$('#idCardId_field').html('请输入19位卡号');
					var cardId = $(this).val();
					if(!checkCardId(cardId)){
						return false;
					}
					$.post(CONTEXT_PATH + '/signCardMgr/saleSignCardReg/getExpensesAmt.do', {'cardId':cardId, 'callId':callId()}, 
						function(data){
							if(data.checkCardId){
								$('#id_expenses').val(data.expenses);
								clearCardIdError();
							} else {
								//$('#idCardId_field').html('找不到该签单卡号').addClass('error_tipinfo').show();
								showMsg(data.error);
								$('#input_btn2').attr('disabled', 'true');
							}
						}, 'json');
				});
				$('#Id_signRule').change(function(){
					var value = $(this).val();
					if(value == '' || value == undefined){
						return false;
					}
					$.post(CONTEXT_PATH + '/signCardMgr/saleSignCardReg/getOverdraftAmt.do', {'signRuleId':value, 'callId':callId()},
						function(data){
							$('#id_overdraft').val(data.overdraft);
						}, 'json');
				});
			});
			function clearCardIdError(){
				//$('#idCardId_field').removeClass('error_tipinfo').html('请输入19位卡号');
				$('#input_btn2').removeAttr('disabled');
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
							<td width="80" height="30" align="right">卡号</td>
							<td>
								<s:textfield id="cardId" name="saleSignCardReg.cardId" cssClass="{required:true, minlength:19}" maxlength="19" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
								<span id="idCardId_field" class="field_tipinfo">请输入19位卡号</span>
							</td>							
							
							<td width="80" height="30" align="right">签单卡客户</td>
							<td>								
								<s:select id="Id_SignCust" name="saleSignCardReg.signCustId" list="signCustList" 
									headerKey="" headerValue="--请选择--" listKey="signCustId" listValue="signCustName" 
									cssClass="{required:true}" />
								<span class="field_tipinfo">请选择签单卡客户</span>								
							</td>							
						</tr>
						<tr>
							<td width="80" height="30" align="right">签单规则</td>
							<td>
								<select id="Id_signRule" name="saleSignCardReg.signRuleId" class="{required:true}"></select>									
								<span class="field_tipinfo">请选择签单规则</span>
							</td>
							<td width="80" height="30" align="right">工本费</td>
							<td>
								<s:textfield id="id_expenses" name="saleSignCardReg.expenses" cssClass="{required:true}" />
								<span class="field_tipinfo">请输入工本费</span>
							</td>
							
						</tr>
						<tr>
							<td width="80" height="30" align="right">透支金额</td>
							<td>
								<s:textfield id="id_overdraft" name="saleSignCardReg.overdraft" cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo">请输入透支金额</span>
							</td>
							
							<td width="80" height="30" align="right">下一年费结算日</td>
							<td>
								<s:textfield id="id_nextBalDate" name="saleSignCardReg.nextBalDate" onclick="WdatePicker({minDate:'%y-%M-%d'})" cssClass="{required:true}" />
							</td>
						</tr>							
						<tr>
							<td width="80" height="30" align="right">持卡人姓名</td>
							<td>
								<s:textfield id="custName" name="saleSignCardReg.custName"/>
								<span class="field_tipinfo">请输入持卡人姓名</span>
							</td>							
							<td width="80" height="30" align="right">证件类型</td>
							<td>
								<s:select id="certType" name="saleSignCardReg.certType" list="certTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select>
								<span class="field_tipinfo">请选择证件类型</span>
							</td>
						</tr>							
						<tr>
							<!-- 
							 -->
							<td width="80" height="30" align="right">证件号码</td>
							<td>
								<s:textfield id="certNo" name="saleSignCardReg.certNo" />
							</td>
							
							<td width="80" height="30" align="right">邮编</td>
							<td>
								<s:textfield id="zip" name="saleSignCardReg.zip"/>
								<span class="field_tipinfo">请输入邮编</span>
							</td>
						</tr>						
						<tr>							
							<td width="80" height="30" align="right">地址</td>
							<td>
								<s:textfield id="address" name="saleSignCardReg.address"/>
								<span class="field_tipinfo">请输入地址</span>
							</td>
							
							<td width="80" height="30" align="right">联系电话</td>
							<td>
								<s:textfield id="phone" name="saleSignCardReg.phones" cssClass="{digit:true}"/>
								<span class="field_tipinfo">请输入联系电话</span>
							</td>
							
						</tr>						
						<tr>
							
							<td width="80" height="30" align="right">Email</td>
							<td>
								<s:textfield id="email" name="saleSignCardReg.email" cssClass="{email:true}"/>
								<span class="field_tipinfo">请输入Email</span>
							</td>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield id="remark" name="saleSignCardReg.remark" />
							</td>
						
						</tr>			
						
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm');clearCardIdError();" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/signCardMgr/saleSignCardReg/list.do')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_SALE_SIGN_CARD_REG_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>