<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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
			// 原来没有密码的不需要填写旧密码
			var passWordHide = $('#idNewPassWord_hidden').val();
			if(isEmpty(passWordHide)){
				$('#passWord_TR').hide();
				$('#passWord_TD1').hide();
				$('#passWord_TD2').hide();
				$('#id_passWord').attr('disabled',true);
			} else{
				$('#passWord_TR').show();
				$('#passWord_TD1').show();
				$('#passWord_TD2').show();
				$('#id_passWord').removeAttr('disabled');
			}
			
			// 校验旧密码
			$('#id_passWord').blur(function(){
				var passWord = $('#idNewPassWord_hidden').val();
				var passWordInput = $('#id_passWord').val();
				// 原来没有密码的不需要校验
				if(isEmpty(passWord)||isEmpty(passWordInput)||passWordInput.length<6){
					return;
				}
				if(passWord!=passWordInput){
					showMsg("输入旧密码不正确，请重新输入。");
					$(':submit').attr('disabled', 'true');
					return;
				} else {
					$(':submit').removeAttr('disabled');
				}
			});
		
			$('#id_newPassWord').blur(function(){
				var passWord = $('#id_passWord').val();
				var newPassWord = $('#id_newPassWord').val();
				var newPassWordAgain = $('#id_newPassWord_again').val();
				var passWordHide = $('#idNewPassWord_hidden').val();
				if(isEmpty(passWordHide)){
					return;
				}
				// 先要填写旧密码
				if(isEmpty(passWord)){
					showMsg("请先输入旧密码。");
					$('#id_newPassWord').val('');
					return;
				}
				if(isEmpty(newPassWord)||newPassWord.length<6){
					return;
				}
				
				if(passWord==newPassWord){
					showMsg("新旧密码不能相同。");
					$(':submit').attr('disabled', 'true');
					return;
				} else {
					$(':submit').removeAttr('disabled');
				}
			});
			
			$('#id_newPassWord_again').blur(function(){
				var newPassWord = $('#id_newPassWord').val();
				var newPassWordAgain = $('#id_newPassWord_again').val();
				if(isEmpty(newPassWordAgain)){
					return;
				}
				if(isEmpty(newPassWord)){
					showMsg("请按顺序输入新密码。");
					$('#id_newPassWord_again').val('');
					return;
				}
				if(newPassWordAgain.length<6){
					return;
				}
				if(newPassWord!=newPassWordAgain){
					showMsg("两次输入的新密码不一致。");
					$(':submit').attr('disabled', 'true');
					return;
				} else {
					$(':submit').removeAttr('disabled');
				}
			});
			
		});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">卡号</td>
							<td>
								<s:textfield name="cardInfo.cardId" cssClass="readonly" readonly="true"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								<s:textfield name="cardInfo.cardIssuer" cssClass="readonly" readonly="true"/>
							</td>
						</tr>
						<tr id="passWord_TR">
							<td id="passWord_TD1" width="80" height="30" align="right">旧密码</td>
							<td id="passWord_TD2">
								<s:password id="id_passWord" cssClass="{required:true, minlength:6}" disabled="true" maxlength="6"></s:password>
								<span class="field_tipinfo">请输入6位旧密码</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">请输入新密码</td>
							<td>
								<s:password id="id_newPassWord" name="newPassWord" cssClass="{required:true, minlength:6}" maxlength="6"></s:password>
								<span class="field_tipinfo">必须与旧密码不同</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">再输入新密码</td>
							<td>
								<s:password id="id_newPassWord_again" name="newPassWordAgain" cssClass="{required:true, minlength:6}" maxlength="6"></s:password>
								<span class="field_tipinfo">请输入6位新密码</span>
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/intgratedService/changePassWord/list.do?goBack=goBack')" style="margin-left:30px;" />
								<s:hidden id="idNewPassWord_hidden" name="cardInfo.password"/>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_GIFT_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>