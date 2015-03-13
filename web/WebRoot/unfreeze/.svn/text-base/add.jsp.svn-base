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
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			$('#idCardId').blur(function(){
				$('#idSubAcctType').html('');
				$('#fznAmtId').val('');
				getSubAcctList();
			});
			$('#idSubAcctType').change(gerFreezeAmt);
		});
		
		function gerFreezeAmt(){
			var cardIdValue = $('#idCardId').val();
			var subAcctTypeValue = $('#idSubAcctType').val();
			if(!checkCardNum(cardIdValue)){
					return;
				}
			if(subAcctTypeValue == null||subAcctTypeValue == undefined || subAcctTypeValue == ""){
				return;
			}
			$.post(CONTEXT_PATH + '/unfreeze/getFznAmt.do', {'unfreezeReg.cardId':cardIdValue, 'unfreezeReg.subacctType':subAcctTypeValue , 'callId':callId()},
				function(json){
					if (json.success){
						$('#fznAmtId').val(fix(json.fznAmt));
						$(':submit').removeAttr('disabled');
					} else {
						showMsg(json.error);
						$(':submit').attr('disabled', 'true');
						$('#fznAmtId').val('')
					}
				}, 'json');
		}
		
		// 根据卡种类获取不同的子账户列表		
		function getSubAcctList(){
			var cardIdValue = $('#idCardId').val();
			if(cardIdValue == null || cardIdValue == undefined || cardIdValue == ""){
				return;
			}
			$.post(CONTEXT_PATH + '/unfreeze/cardIdCheck.do', {'unfreezeReg.cardId':cardIdValue, 'callId':callId()},
			 function(json){
					if (json.success){
						$(':submit').removeAttr('disabled');
					} else {
						showMsg(json.error);
						$(':submit').attr('disabled', 'true');
					}
			}, 'json');
			$("#idSubAcctType").load(CONTEXT_PATH + "/unfreeze/getSubAcctList.do",{'unfreezeReg.cardId':cardIdValue, 'callId':callId()});
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
							<s:textfield id="idCardId" name="unfreezeReg.cardId" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
							 cssClass="{required:true}"  maxlength="19" />
								<span class="field_tipinfo" id="">请输入卡号</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">子账户类型</td>
							<td>
							<select name="unfreezeReg.subacctType" id="idSubAcctType"
								 class="{required:true}"></select>
								<span class="field_tipinfo">请选择子账户类型</span>
							</td>
						</tr>	
						<tr>
							<td width="80" height="30" align="right" >冻结金额: </td>
							<td>
								<s:textfield id="fznAmtId" name="" readonly="true" cssClass="readonly"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">解付金额</td>
							<td>
							<s:textfield name="unfreezeReg.unfznAmt" cssClass="{required:true, num:true, decimal:'14,2'}" maxlength="14" ></s:textfield>
								<span class="field_tipinfo">小于等于冻结金额</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td>
							<s:textfield name="unfreezeReg.remark" />
								<span class="field_tipinfo">请输入备注</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/unfreeze/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_UNFREEZE_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>