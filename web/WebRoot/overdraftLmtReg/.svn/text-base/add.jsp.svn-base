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
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/validate.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		
		<f:js src="/js/biz/gift/addGift.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		
		$(function(){
			$('#cardId').blur(function(){
				var value = $(this).val();
				if(!checkCardNum(value)){
					return;
				}
				$.post(CONTEXT_PATH + '/overdraftLmtReg/getOverdraft.do', {'overdraftLmtReg.cardId':value, 'callId':callId()}, 
				function(json){			
					if (json.success){
						$('#idOverdraft').val(json.overdraft);
						$(':submit').removeAttr('disabled');
					} else {
						showMsg(json.error);
						$(':submit').attr('disabled', 'true');
						$('#idOverdraft').val('');
					}
				}, 'json');
			});
			
			$('#id_newOverdraft').blur(function(){
				var value = $(this).val();
				if(isEmpty(value)){
					return;
				}
				if(value<0){
					showMsg("调整透支金额不能小于0.");
					$(':submit').attr('disabled', 'true');
				} else {
					$(':submit').removeAttr('disabled');
				}
			});
			
		});

		/** 表单域校验 */
		function validateForm() {
			var signatureReg = $('#needSignatureReg').val();
			if (signatureReg == 'true' && !CheckUSBKey()) { // 证书签名失败
				return false;
			}
			return true;
		}
		
		function CheckUSBKey() {
			// 检查飞天的key
			var isOnline = document.all.FTUSBKEYCTRL.IsOnline;
			if (isOnline == 0) {
				if (FTDoSign()) { // 调用FT的签名函数
					return true;
				} else {
					return false;
				}
			} else {
				showMsg("请检查USB Key是否插入或USB Key是否正确！");
				return false;
			}
			return true;
		}

		/* 飞天的Key的签名函数 */
		function FTDoSign() {
			var SetCertResultRet = document.all.FTUSBKEYCTRL.SetCertResult; // 选择证书
			if (SetCertResultRet == 0) {
				var serialNumber = document.all.FTUSBKEYCTRL.GetCertSerial;
				$('#serialNo').val(serialNumber);
			} else {
				showMsg("选择证书失败");
				return false;
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

						<tr>
							<td width="80" height="30" align="right">卡号</td>
							<td>
							<s:textfield id="cardId" name="overdraftLmtReg.cardId" cssClass="{required:true, minlength:18}"  maxlength="19" />
								<span class="field_tipinfo" id="">请输入18位或者19位卡号</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right" >原透支金额: </td>
							<td>
								<s:textfield id="idOverdraft" name="" readonly="true" cssClass="readonly"/>
							</td>
						</tr>	
						<tr>	
							<td width="80" height="30" align="right">调整透支金额</td>
							<td>
								<s:textfield id="id_newOverdraft" name="overdraftLmtReg.newOverdraft" cssClass="{required:true, decimal:'14,2'}" maxlength="14"/>
								<span class="field_tipinfo">请输入调整透支金额</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">备注</td>
								<td>
								<s:textfield name="overdraftLmtReg.remark" />
								<span class="field_tipinfo">请输入备注</span>
							</td>
						</tr>

						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="申请" id="input_btn2"  name="ok" onclick="return validateForm()" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/overdraftLmtReg/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_OVERDRAFTLMTREG_ADD"/>
					<s:hidden id='needSignatureReg' name="formMap.needSignatureReg" />
					<s:hidden id="serialNo" name="formMap.serialNo"/>
					<jsp:include flush="true" page="/common/certJsIncluded.jsp"></jsp:include>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>