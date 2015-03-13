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
			$('#cardId').blur(function(){
				var value = $(this).val();
				if(value == null || value == undefined || value == ""){
					return;
				}
				$.post(CONTEXT_PATH + '/intgratedService/losscard/cardIdCheck.do', {'lossCardReg.cardId':value, 'callId':callId()}, 
				function(json){
					if (json.success){
						$('#idCustName_hidden').val(json.custName_hidden);
						$('#idCertType_hidden').val(json.certType_hidden);
						$('#idCertNo_hidden').val(json.certNo_hidden); 
						$('#id_CardStatus').val(json.cardStatus); 
						if(json.isAccu){
							$('#id_DepositAmt').val(json.depositAmt); 
						} else {
							$('#id_DepositAmt').val(fix(json.depositAmt)); 
						}
						$('#id_rebateAmt').val(fix(json.rebateAmt)); 
						$(':submit').removeAttr('disabled');
					} else {
						showMsg(json.error);
						$(':submit').attr('disabled', 'true');
						//$('#cardId').val('');
					}
				}, 'json');
			});
		});
		function doConfirm(){
			var value = $('#cardId').val();
			var custName = $('#idCustName').val();
			var certType = $('#idCertType').val();
			var certNo = $('#idCertNo').val();
			var custNameHidden = $('#idCustName_hidden').val();
			var certTypeHidden = $('#idCertType_hidden').val();
			var certNoHidden = $('#idCertNo_hidden').val();
			
			// 证件类型不为空的话，证件号码也必须不为空
			if(!isEmpty(certType)){
				if(isEmpty(certNo)){
					showMsg("请输入证件号码！");
					return false;
				}
			}

			// 证件号码不为空的话，证件类型也必须不为空
			if(!isEmpty(certNo)){
				if(isEmpty(certType)){
					showMsg("请选择证件类型！");
					return false;
				}
			}
			
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
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						
						<tr>
							<td width="80" height="30" align="right">卡号</td>
							<td>
								<s:textfield id="cardId" name="lossCardReg.cardId" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" 
									cssClass="{required:true}" maxlength="19" />
							</td>
							
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡状态</td>
							<td>
								<s:textfield id="id_CardStatus" name="cardStatus" cssClass="readonly" readonly="true" />
							</td>
							
							<td width="80" height="30" align="right">持卡人姓名</td>
							<td>
								<s:textfield id="idCustName" name="lossCardReg.custName" cssClass="{}" />
								<span class="field_tipinfo">持卡人姓名</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">充值资金（次数）余额</td>
							<td>
								<s:textfield id="id_DepositAmt" name="depositAmt" cssClass="readonly" readonly="true" />
								<span class="field_tipinfo">充值资金</span>
							</td>

							<td width="80" height="30" align="right">返利资金余额</td>
							<td>
								<s:textfield id="id_rebateAmt" name="rebateAmt" cssClass="readonly" readonly="true" />
								<span class="field_tipinfo">返利资金</span>
							</td>
						</tr>	
						<tr>
							<td width="80" height="30" align="right">证件类型</td>
							<td>
								<s:select id="idCertType" name="lossCardReg.certType" list="certTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" />
								<span class="field_tipinfo">选择证件类型</span>
							</td>
							
							<td width="80" height="30" align="right">证件号码</td>
							<td>
								<s:textfield id="idCertNo" name="lossCardReg.certNo" cssClass="{}"/>
								<span class="field_tipinfo">请输入证件号</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield name="lossCardReg.remark" />
								<span class="field_tipinfo">请输入备注</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return doConfirm();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/intgratedService/losscard/list.do?goBack=goBack')" class="ml30" />
								<s:hidden id="idCustName_hidden" />
								<s:hidden id="idCertType_hidden" />
								<s:hidden id="idCertNo_hidden" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_LOSSCARD_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>