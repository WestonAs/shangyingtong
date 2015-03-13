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
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/validate.js"/>
		<f:js src="/js/biz/addCardBin/addCardBin.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div id="loadingBarDiv"	style="display: none; width: 100%; height: 100%">
			<table width=100% height=100%>
				<tr>
					<td align=center >
						<center>
							<img src="${CONTEXT_PATH}/images/ajax_saving.gif" align="middle" />
							<div id="processInfoDiv"
								style="font-size: 15px; font-weight: bold">
								正在处理中，请稍候...
							</div>
							<br/>
							<br/>
							<br/>
						</center>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="contentDiv" class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="addCardBin.do" id="inputForm" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>申请新卡BIN</caption>
						
						<s:hidden id="id_loginBranchCode" name="loginBranchCode" />
						<s:hidden id="id_loginRoleType" name="loginRoleType" />
						<tr>
							<td width="80" height="30" align="right">卡种</td>
							<td>
								<s:select id="idCardType" name="cardBinReg.cardType" list="cardTypeList" headerKey="" headerValue="--请选择--" listKey="cardTypeCode" listValue="cardTypeName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择卡种</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">卡BIN名称</td>
							<td>
								<s:textfield id="idCardBinName" name="cardBinReg.binName" cssClass="{required:true}" maxlength="20"/>
								<span class="field_tipinfo">请输入卡BIN名称</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								<s:if test="fenzhiRoleLogined">
									<s:hidden id="id_CardBranch" name="cardBinReg.cardIssuer" />
									<s:textfield id="id_CardBranch_sel" name="cardIssuerName" cssClass="{required:true}" />
								</s:if>
								<s:elseif test="cardOrCardDeptRoleLogined">
									<s:select name="cardBinReg.cardIssuer" list="myCardBranch"
										 headerKey="" headerValue="--请选择--" 
										 listKey="branchCode" listValue="branchName" 
										 cssClass="{required:true}" onmouseover="FixWidth(this);"/>
								</s:elseif>
								<span class="field_tipinfo">请选择发卡机构</span>
							</td>
							
						</tr>
						<tr>
							<td width="80" height="30" align="right">系统推荐卡BIN</td>
							<td>
								<s:textfield id="idCardBinNo" name="cardBinReg.binNo" cssClass="{required:true,minlength:6,digit:true}" maxlength="6"/>
								<span class="field_tipinfo" id="idCardBinNo_field">请输入6位数字的卡BIN号码</span>
							</td>
						</tr>

						<!-- 
						<tr>
							<td width="80" height="30" align="right">货币代码</td>
							<td>
								<s:select id="idCurrCode" name="cardBinReg.currCode" list="currCodeList" headerKey="" headerValue="--请选择--" listKey="currCode" listValue="currName" cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择货币代码</span>
							</td>
						</tr>
						 -->
						 
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30">
								<input type="button" value="提交申请" id="input_btn2"  name="ok" onclick="return check();" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm');clearCarBinError();" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/addCardBin/regList.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARDBIN_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>