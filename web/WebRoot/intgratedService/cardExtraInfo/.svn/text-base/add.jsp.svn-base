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
			$('#id_cardId').blur(function(){
				var value = $(this).val();
				if(value == null || value == undefined || value == ""){
					return;
				}
				$.post(CONTEXT_PATH + '/intgratedService/cardExtraInfo/cardIdCheck.do', {'cardExtraInfo.cardId':value, 'callId':callId()}, 
				function(json){
					if (json.success){
						$('#id_cardCustomerId').val(json.cardCustomerId);
						$('#id_saleOrgId').val(json.saleOrgId);
						$('#id_cardBranch').val(json.cardBranch);
						$(':submit').removeAttr('disabled');
					} else {
						showMsg(json.error);
						$(':submit').attr('disabled', 'true');
					}
				}, 'json');
			});
		});

		function ajaxFindCardExtraInfos(type){
			if($('#id_cardId').val()==""  || $('#id_cardBranch').val()==""){
				showMsg("请先输入卡号检查");
				return;
			}

			var params;
			if(type=="credNo"){//证件号检查
				if($('#credNo').val()=="") return;
				params = {'formMap.cardBranch':$('#id_cardBranch').val(),
						'formMap.credNo':$('#credNo').val(),
						'callId':callId()};
			} else if(type=="mobileNo"){//手机号检查
				if($('#mobileNo').val()=="") return;
				params = {'formMap.cardBranch':$('#id_cardBranch').val(),
						'formMap.mobileNo':$('#mobileNo').val(),
						'callId':callId()};
			} else {
				return;
			}
			$.post(CONTEXT_PATH + '/ajax/ajaxFindCardExtraInfos.do', 
					params, 
					function(json){
						if (json.returnCode == 1){ //成功
							if(json.cnt>0){
								if(type=="credNo"){
									showMsg("提示信息：证件号["+$('#credNo').val()+"]已经关联了卡号");
								}else{
									showMsg("提示信息：手机号["+$('#mobileNo').val()+"]已经关联了卡号");
								}
							}
						} else {
							if(type=="credNo"){
								showMsg("检查证件号["+$('#credNo').val()+"]是否存在录入的持卡人信息 失败");
							}else{
								showMsg("检查手机号["+$('#mobileNo').val()+"]是否存在录入的持卡人信息 失败");
							}
						}
					}, 
					'json');
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
							<s:hidden id="id_saleOrgId" name="cardExtraInfo.saleOrgId" />
							<s:hidden id="id_cardBranch" name="cardExtraInfo.cardBranch" />
						<tr>
							<td width="80" height="30" align="right">卡号</td>
							<td>
								<s:textfield id="id_cardId" name="cardExtraInfo.cardId" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" 
									cssClass="{required:true, digit:true}" maxlength="19" />
								<span class="field_tipinfo">请输入数字</span>
							</td>
							<td width="80" height="30" align="right">持卡人姓名</td>
							<td>
								<s:textfield name="cardExtraInfo.custName" />
								<span class="field_tipinfo">请输入姓名</span>
							</td>
						</tr>	
						<tr>
							<td width="80" height="30" align="right">证件类型</td>
							<td>
								<s:select name="cardExtraInfo.credType" list="certTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" />
								<span class="field_tipinfo">请选择类型</span>
							</td>
							<td width="80" height="30" align="right">证件号码</td>
							<td>
								<s:textfield id="credNo" name="cardExtraInfo.credNo" onblur="ajaxFindCardExtraInfos('credNo');" cssClass="{digitOrLetter:true}"/>
								<span id="credNoFieldTip" class="field_tipinfo"></span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">证件有效期</td>
							<td>
								<s:textfield name="cardExtraInfo.credNoExpiryDate" onfocus="WdatePicker({dateFmt:'yyyyMMdd',autoPickDate:true,isShowOthers:false})"/>
								<span class="field_tipinfo">请选择日期，格式：yyyyMMdd</span>
							</td>
							<td width="80" height="30" align="right">职业</td>
							<td>
								<s:textfield name="cardExtraInfo.career" cssClass="{maxlength:16}"/>
								<span class="field_tipinfo"></span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">国籍</td>
							<td>
								<s:textfield name="cardExtraInfo.nationality" cssClass="{maxlength:16}"/>
								<span class="field_tipinfo"></span>
							</td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">联系地址</td>
							<td>
								<s:textfield name="cardExtraInfo.address" />
								<span class="field_tipinfo">请输入地址</span>
							</td>
							<td width="80" height="30" align="right">联系电话</td>
							<td>
								<s:textfield name="cardExtraInfo.telNo" cssClass="{digit:true}"/>
								<span class="field_tipinfo">请输入电话</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">手机号</td>
							<td>
								<s:textfield id="mobileNo" name="cardExtraInfo.mobileNo" onblur="ajaxFindCardExtraInfos('mobileNo');" cssClass="{digit:true}" maxlength="30" />
								<span id="mobileNoFieldTip" class="field_tipinfo">请输入手机号</span>
							</td>
							<td width="80" height="30" align="right">邮件地址</td>
							<td>
								<s:textfield name="cardExtraInfo.email" cssClass="{email:true}" />
								<span class="field_tipinfo">请输入邮件地址</span>
								<span class="error_tipinfo">邮件格式错误</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">是否开通短信通知</td>
							<td>
								<s:select name="cardExtraInfo.smsFlag" list="smsFlagList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" />
								<span class="field_tipinfo">请选择</span>
							</td>
							<td width="80" height="30" align="right">生日</td>
							<td>
								<s:textfield name="cardExtraInfo.birthday" onclick="WdatePicker()" />
								<span class="field_tipinfo">请选择生日</span>
							</td>
						</tr>

						<tr>
							<td width="80" height="30" align="right">购卡客户编号</td>
							<td>
								<s:textfield id="id_cardCustomerId" name="cardExtraInfo.cardCustomerId" cssClass="{required:true} readonly" maxlength="30" readonly="true"/>
								<span class="field_tipinfo">不能为空</span>
							</td>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield name="cardExtraInfo.remark"/>
								<span class="field_tipinfo">请输入备注</span>
							</td>
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="3">
							<input type="submit" value="提交" id="input_btn2"  name="ok" />
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/intgratedService/cardExtraInfo/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARDEXTRAINFO_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>