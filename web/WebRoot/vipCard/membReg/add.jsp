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
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<f:js src="/js/biz/vipCard/membReg.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">客户姓名</td>
							<td>
								<s:textfield name="membReg.custName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入客户姓名</span>
							</td>
							<td width="80" height="30" align="right">卡号</td>
							<td>
								<s:textfield id="cardId" name="membReg.cardId" cssClass="{required:true, digit: true}"  maxlength="19" onblur="cardIdCheck()"/>
								<span class="field_tipinfo">请输入卡号</span>
							</td>
						</tr>
						<tr>	
							<td width="80" height="30" align="right">性别</td>
							<td>
								<s:select name="membReg.sex" list="sexTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select>
								<span class="field_tipinfo">请选择性别</span>
							</td>
							<td width="80" height="30" align="right">年龄</td>
							<td>
								<s:textfield name="membReg.age" />
								<span class="field_tipinfo">请输入年龄</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">学历</td>
							<td>
								<s:select name="membReg.education" list="educationTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select>
								<span class="field_tipinfo">请选择学历</span>
							</td>
							<td width="80" height="30" align="right">会员类型</td>
							<td>
								<input type="hidden" id="membClass" name="membReg.membClass" type="text" />
								<input id="className_memb" type="text" class="{required:true}  readonly" readonly="readonly" />
								<span class="field_tipinfo">自动匹配卡号</span>
							</td>	
						</tr>
						<tr>	
							<td width="80" height="30" align="right">证件类型</td>
							<td>
								<s:select id="membReg.credType" name="membReg.credType" list="credTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select>
								<span class="field_tipinfo">请选择证件类型</span>
							</td>
							<td width="80" height="30" align="right">证件号码</td>
							<td>
								<s:textfield name="membReg.credNo" />
								<span class="field_tipinfo">请输入证件号码</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">联系电话</td>
							<td>
								<s:textfield name="membReg.telNo" />
								<span class="field_tipinfo">请输入联系电话</span>
							</td>		
							<td width="80" height="30" align="right">联系地址</td>
							<td>
								<s:textfield name="membReg.address" />
								<span class="field_tipinfo">请输入联系地址</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">手机号</td>
							<td>
								<s:textfield name="membReg.mobileNo" />
								<span class="field_tipinfo">请输入手机号</span>
							</td>
							
							<td width="80" height="30" align="right">邮件地址</td>
							<td>
								<s:textfield name="membReg.email" />
								<span class="field_tipinfo">请输入邮件地址</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">行业</td>
							<td>
								<s:textfield name="membReg.job" />
								<span class="field_tipinfo">请输入行业</span>
							</td>
							<td width="80" height="30" align="right">月收入</td>
							<td>
								<s:textfield name="membReg.salary" />
								<span class="field_tipinfo">请输入月收入</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">生日</td>
							<td>
								<s:textfield name="membReg.birthday" onclick="WdatePicker({dateFmt:'MM-dd'})"  />
								<span class="field_tipinfo">请输入生日</span>
							</td>
							<td></td>
							<td></td>
						</tr>
						<s:if test='@gnete.card.service.mgr.BranchBizConfigCache@isNeedMembBankAcctInfo(loginBranchCode)'>
							<tr>
								<td width="80" height="30" align="right">开户行名称</td>
								<td>
									<s:textfield id="bankName" name="membReg.bankName" cssClass="{required:true}"/>
									<span class="field_tipinfo">点击选择</span>
								</td>
								<td width="80" height="30" align="right">开户行号</td>
								<td>
									<s:textfield id="bankNo" name="membReg.bankNo" cssClass="{required:true, digit:true, minlength:12} readonly" maxlength="12" readonly="true"/>
									<span class="field_tipinfo">请点击开户行名</span>
								</td>
							</tr>
							<tr>
								<td width="80" height="30" align="right">账户户名</td>
								<td>
									<s:textfield id="accName" name="membReg.accName" maxlength="30" cssClass="{required:true}"/>
									<span class="field_tipinfo">请输入户名</span>
								</td>
								<td width="80" height="30" align="right">账号</td>
								<td>
									<s:textfield name="membReg.accNo" cssClass="{required:true, digit:true}" maxlength="32"/>
									<span class="field_tipinfo">请输入账号</span>
								</td>
							</tr>
							<tr>
								<td width="80" height="30" align="right">账户地区</td>
								<td>
									<s:hidden id="accAreaCode" name="membReg.accAreaCode" />
									<s:textfield id="accAreaName" name="accAreaName" cssClass="{required:true} readonly" readonly="true"/>
									<span class="field_tipinfo">请点击开户行名</span>
								</td>
								<td width="80" height="30" align="right">账户类型</td>
								<td>
									<s:select id="acctType" name="membReg.acctType" list="@gnete.card.entity.type.AcctType@getAll()" headerKey="" headerValue="--请选择--" 
										listKey="value" listValue="name" cssClass="{required:true}" />
									<span class="field_tipinfo">请选择账户类型</span>
								</td>
							</tr>
							<tr>
								<td width="80" height="30" align="right">账户介质类型</td>
								<td>
									<s:select id="acctMediaType" name="membReg.acctMediaType" list="@gnete.card.entity.type.AcctMediaType@getAll()" headerKey="" headerValue="--请选择--" 
										listKey="value" listValue="name" cssClass="{required:true}" />
									<span class="field_tipinfo">请选择</span>
								</td>
								<td></td>
								<td></td>
							</tr>
						</s:if>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="注册" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/vipCard/membReg/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MEMBREG_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>