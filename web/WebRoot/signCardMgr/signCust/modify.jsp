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
						<tr>
							<td width="80" height="30" align="right">购卡客户ID</td>
							<td>
								<s:textfield name="signCust.signCustId" readonly="true" cssClass="readonly"/>
							</td>
							<td width="80" height="30" align="right">签单客户名称</td>
							<td>
								<s:textfield name="signCust.signCustName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入客户名称</span>
							</td>
						</tr>
						<tr>
<!-- 							<td width="80" height="30" align="right">透支总额度</td> -->
<!-- 							<td> -->
<!-- 								<s:textfield name="signCust.overdraftSum" cssClass="{required:true}"/> -->
<!-- 								<span class="field_tipinfo">请输入金额</span> -->
<!-- 							</td>	 -->
							<td width="80" height="30" align="right">团购卡号</td>
							<td>
								<s:textfield name="signCust.groupCardId" />		
								<span class="field_tipinfo">请输入团购卡号</span>							
							</td>
							<td width="80" height="30" align="right">传真号</td>
							<td>
								<s:textfield name="signCust.fax" cssClass="{digit:true}"/>
								<span class="field_tipinfo">请输入传真号</span>
							</td>	
						</tr>						
						<tr>							
							<td width="80" height="30" align="right">账单生成日期</td>
							<td>
								<s:textfield name="signCust.billDate" cssClass="{required:true, digit:true, maxlength:2, min:1, max:31}" />
								<span class="field_tipinfo">请输入</span>
							</td>
							<td width="80" height="30" align="right">还款间隔日期</td>
							<td>
								<s:textfield name="signCust.payDay" cssClass="{required:true, digit:true}" />		
								<span class="field_tipinfo">请输入</span>							
							</td>							
						</tr>
						<tr>
							<td width="80" height="30" align="right">开户银行</td>
							<td>
								<s:textfield name="signCust.bank" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入开户银行</span>
							</td>
							
							<td width="80" height="30" align="right">开户行号</td>
							<td>
								<s:textfield name="signCust.bankNo" cssClass="{required:true, digit:true}"/>
								<span class="field_tipinfo">请输入开户行号</span>
							</td>
						</tr>						
						<tr>
							<td width="80" height="30" align="right">银行账号</td>
							<td>
								<s:textfield name="signCust.bankAccNo" cssClass="{required:true, digit:true}"/>
								<span class="field_tipinfo">请输入银行账号</span>
							</td>
							
							<td width="80" height="30" align="right">联系人</td>
							<td>
								<s:textfield name="signCust.contact"/>
								<span class="field_tipinfo">请输入联系人</span>
							</td>
						</tr>						
						<tr>
							<td width="80" height="30" align="right">证件类型</td>
							<td>
								<s:select name="signCust.credType" list="credTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select>
								<span class="field_tipinfo">请选择证件类型</span>
							</td>
							<td width="80" height="30" align="right">证件号码</td>
							<td>
								<s:textfield name="signCust.credNo" />	
								<span class="field_tipinfo">请选择证件号码</span>							
							</td>									
						</tr>						
						<tr>
							<td width="80" height="30" align="right">邮编</td>
							<td>
								<s:textfield name="signCust.zip" cssClass="{digit:true}"/>
								<span class="field_tipinfo">请输入邮编</span>
							</td>
							
							<td width="80" height="30" align="right">地址</td>
							<td>
								<s:textfield name="signCust.address"/>
								<span class="field_tipinfo">请输入地址</span>
							</td>
						</tr>						
						<tr>
							
							<td width="80" height="30" align="right">联系电话</td>
							<td>
								<s:textfield name="signCust.phone" cssClass="{digit:true}"/>
								<span class="field_tipinfo">请输入联系电话</span>
							</td>
							<td width="80" height="30" align="right">手机号码</td>
							<td>
								<s:textfield name="signCust.mobile" cssClass="{digit:true}"/>
								<span class="field_tipinfo">请输入手机号码</span>
							</td>
						</tr>						
						<tr>
							<td width="80" height="30" align="right">Email</td>
							<td>
								<s:textfield name="signCust.email" cssClass="{email:true}"/>
								<span class="field_tipinfo">请输入Email</span>
							</td>
							<td></td>
							<td></td>
						</tr>			
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" style="margin-left:30px;" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/signCardMgr/signCust/list.do')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_SIGN_CUST_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>