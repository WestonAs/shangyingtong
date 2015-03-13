<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>修改用户密码</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/md5.js?v=20140604"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			$('#idNewPass').blur(checkPWD);
		});
		/**
		 * 检查是否包含中文字符
		 */
		function strlen2(str){
			var len = 0;
			var i;

			for (i=0;i<str.length;i++){
				if (str.charCodeAt(i)>255) return true;
			}
			return false;
		}
		
		function checkPWD() {
			var pass = $("#idNewPass").val();
			
			if(strlen2(pass)){
				$('#error_tip_Id').html('新密码包含了非法字符').addClass('error_tipinfo').show();
				$('#input_btn2').attr('disabled', 'true');
				return false;
			} 
			var pattern = /^(?!\D+$)(?![^a-zA-Z]+$)\S{6,16}$/;
			if(!pattern.test(pass) || pass == $("#idOldPass").val()){
				$('#error_tip_Id').html("6～16位字母、数字、特殊字符的组合，必须含有数字与字母，新旧密码不能相同。").addClass('error_tipinfo').show();
				$('#input_btn2').attr('disabled', 'true');
				return false;
			}
			
			$('#error_tip_Id').removeClass('error_tipinfo').html('6～16位字母、数字、特殊字符的组合，必须含有数字与字母，新旧密码不能相同。');
			$('#input_btn2').removeAttr('disabled');
			return true;
		}
		
		function dealWithPwd(){
			
			if(!checkPWD()){
				return false;
			}
			if( $("#idNewPass").val()!=$("#idConfirmPass").val() ){
				$("#idConfirmPassErrTip").show();
				return false;
			}else{
				$("#idConfirmPassErrTip").hide();
			}
			
			if($('#inputForm').validate().form()){
				var encryptOldPwd = b64_md5($("#random").val()+b64_md5($("#idOldPass").val()).toUpperCase()).toUpperCase();
				var encryptNewPwd = b64_md5($("#idNewPass").val()).toUpperCase();
				$("#encryptOldPass").val(encryptOldPwd);
				$("#encryptNewPass").val(encryptNewPwd);
				$("#idOldPass").attr("disabled","disabled");
				$("#idNewPass").attr("disabled","disabled");
				$("#idConfirmPass").attr("disabled","disabled");
				$('#inputForm').submit();
				$("#idOldPass").removeAttr("disabled");
				$("#idNewPass").removeAttr("disabled");
				$("#idConfirmPass").removeAttr("disabled");
			}else{
				return false;
			}
		}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modifyPass.do" id="inputForm" cssClass="validate">
					<s:hidden id="random" name="formMap.random" disabled="true"/>
	            	<input type="hidden" id="encryptOldPass" name="oldPass" />
	            	<input type="hidden" id="encryptNewPass" name="newPass" />
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						
						<tr>
							<td width="150" height="30" align="right">用户旧密码</td>
							<td>
								<s:password id="idOldPass" name="" cssClass="{required:true, minlength:6}" maxlength="16"/>
								<span class="field_tipinfo">请输入旧密码</span>
								<span class="error_tipinfo">旧密码至少6位</span>
							</td>
						</tr>
						<tr>
							<td width="150" height="30" align="right">用户新密码</td>
							<td>
								<s:password id="idNewPass" name="" cssClass="{required:true, minlength:6}" maxlength="16"/>
								<span id="error_tip_Id" class="field_tipinfo">6～16位字母、数字、特殊字符的组合，必须含有数字与字母，新旧密码不能相同。</span>
								<span class="error_tipinfo">新密码至少6位</span>
							</td>
						</tr>
						<tr>
							<td width="150" height="30" align="right">请再输一次新密码</td>
							<td>
								<s:password id="idConfirmPass" cssClass="{required:true, minlength:6, equalTo:'#idNewPass'}"/>
								<span class="field_tipinfo">请再次输入新密码</span>
								<span id="idConfirmPassErrTip" class="error_tipinfo">两次输入需一致</span>
							</td>
						</tr>
						
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="提交" id="input_btn2"  name="ok" onclick="dealWithPwd()" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_USER_MODIFY_PASS"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>