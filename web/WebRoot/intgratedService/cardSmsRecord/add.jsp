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
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>

		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/validate.js"/>
		
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		
		<script type="text/javascript">
			function validateForm(){
				if( isEmpty($("#mobiles").val()) || isEmpty($("#smsSendContent").val()) ){
					showMsg("手机号 或 短信内容 不能为空！");
					return false;
				} else if( $("#mobiles").val().split("\n").length>5001 ){
					showMsg("输入的手机号不能大于5000行！");
					return false;
				}else if ($("#smsSendContent").val().replace(/[^\x00-\xff]/g,"aa").length>150){
					showMsg("短信内容不能超过75个中文字符 或 150个英文字符！");
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
				<s:form action="add.do" id="inputForm" enctype="multipart/form-data" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80px" height="30" align="right" valign="middle">手机号</td>
							<td width="120px" align="left">
								<s:textarea id="mobiles" name="formMap.mobiles" rows="8" cols="12" cssClass="{required:true}" cssStyle="width:150px;"/>
							</td>
							<td width="80px" height="30" align="right" valign="middle">短信内容</td>
							<td width="60%" align="left">
								<s:textarea id="smsSendContent" name="formMap.smsSendContent" rows="8" cssClass="{required:true}" cssStyle="width:450px;" />
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" onclick="return validateForm()" name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/intgratedService/cardSmsRecord/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARD_SMS_RECORD_ADD"/>
				</s:form>
				
				<span class="note_div">注释</span>
				<ul class="showli_div">
					<li class="showli_div">输入的手机号不能大于5000行！每输入一个手机号后，请加个回车</li>
					<li class="showli_div">短信内容不能超过75个中文字符或150个英文字符！</li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>