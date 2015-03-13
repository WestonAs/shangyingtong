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
							<s:hidden name="cardExtraInfo.saleOrgId" />
							<s:hidden name="cardExtraInfo.cardCustomerId" />
							<s:hidden name="cardExtraInfo.cardBranch" />
						<tr>
							<td width="80" height="30" align="right">卡号</td>
							<td>
								<s:textfield name="cardExtraInfo.cardId" cssClass="{digit:true} readonly" readonly="true" />
								<span class="field_tipinfo">请输入数字卡号</span>
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
								<span class="field_tipinfo">请选择证件类型</span>
							</td>
							<td width="80" height="30" align="right">证件号码</td>
							<td>
								<s:textfield name="cardExtraInfo.credNo" />
								<span class="field_tipinfo">请输入证件号</span>
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
								<s:textfield name="cardExtraInfo.telNo" />
								<span class="field_tipinfo">请输入电话</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">手机号</td>
							<td>
								<s:textfield name="cardExtraInfo.mobileNo" cssClass="{digit:true}" maxlength="30" />
								<span class="field_tipinfo">请输入手机号</span>
								<span class="error_tipinfo">手机号为数字</span>
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
					<s:token name="_TOKEN_CARDEXTRAINFO_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>