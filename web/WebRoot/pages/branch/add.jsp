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
		<f:css href="/js/tree/dhtmlxtree.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:js src="/js/tree/dhtmlxcommon.js"/>
		<f:js src="/js/tree/dhtmlxtree.js"/>
		<f:js src="/js/date/WdatePicker.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/biz/branch/add.js"/>

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
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}<span class="caption_title"> | <f:link href="/pages/branch/showAddExist.do">机构已存在？</f:link></span></caption>
						<tr>
							<td width="80" height="30" align="right">机构使用币种</td>
							<td>
								<input type="hidden" id="id_loginRoleType" name="loginRoleType" value="${loginRoleType }" />
								<input type="hidden" id="id_loginBranchCode" name="loginBranchCode" value="${loginBranchCode }" />
								<s:select name="branch.curCode" list="currCodeList" headerKey="" headerValue="--请选择--" listKey="currCode" listValue="currName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
							
							<td width="80" height="30" align="right">机构索引号</td>
							<td>
								<s:textfield name="branch.branchIndex" maxlength="20"/>
								<span class="field_tipinfo">输入机构索引号</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">机构类型</td>
							<td>
								<s:select id="idType" name="branchHasType.typeCode" list="typeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
							
							<td id="td_branchLevel_1" width="80" height="30" align="right" style="display: none;">级别</td>
							<td id="td_branchLevel_2" style="display: none;">
								<select id="id_branchLevel" name="branchHasType.branchLevel" class="{required:true}" disabled="true"></select>
								<span class="field_tipinfo">请选择级别</span>
							</td>
							
							<td id="td_superBranchCode_1" width="80" height="30" align="right" style="display: none;">上级机构</td>
							<td id="td_superBranchCode_2" style="display: none;">
								<s:hidden id="idBranchCode" name="branch.superBranchCode"/>
								<s:textfield id="idBranchName" name="superBranchName" disabled="true"/>
								<span class="field_tipinfo">请选择上级</span>
							</td>
						</tr>
						
						<tr id="tr_setMode">
							<td width="80" height="30" align="right">管理机构</td>
							<td>
								<select id="idManageBranchList" name="branch.parent" class="{required:true}" onmouseover="FixWidth(this);" />
								<span class="field_tipinfo">点击选择</span>
							</td>
							
							<td id="td_setMode1" width="80" height="30" align="right" style="display: none;">清算模式</td>
							<td id="td_setMode2" style="display: none;">
								<s:select id="id_SetMode" name="branchHasType.setMode" list="setModeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" 
									cssClass="{required:true}" disabled="true"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
						</tr>
						
						<tr id="id_card" style="display: none;">
							<td width="80" height="30" align="right">发展方</td>
							<td>
								<select id="id_card_develop" name="branch.developSide" class="{required:true}" onmouseover="FixWidth(this);" />
								<span class="field_tipinfo">管理方代理</span>
							</td>
							<td width="80" height="30" align="right">资信额度</td>
							<td>
								<s:textfield id="id_trustAmt" name="cardRiskReg.amt" cssClass="{required:true, num:true, decimal:'10,2'}"/>
								<span class="field_tipinfo">万元</span>
								<span class="error_tipinfo">整数位最多8位</span>
							</td>
						</tr>
						<tr id="id_sale" style="display: none;">
							<td width="80" height="30" align="right">刷卡充值是否独立清算</td>
							<td>
								<s:select disabled="true" id="id_sale_setle" list="yesOrNoList" name="branch.isSettle" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择是或否</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">机构名称</td>
							<td>
								<s:textfield name="branch.branchName" cssClass="{required:true}" maxlength="100" onblur="$('#idAccName').val(this.value)"/>
								<span class="field_tipinfo">请输入机构名</span>
							</td>
							
							<td width="80" height="30" align="right">机构简称</td>
							<td>
								<s:textfield id="id_branchAbbname" name="branch.branchAbbname" cssClass="{required:true}" maxlength="30"/>
								<span class="field_tipinfo">请输入简称</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">地区</td>
							<td>
								<s:hidden id="idArea" name="branch.areaCode" />
								<s:textfield id="idArea_sel" name="area" cssClass="{required:true}" />
								<span class="field_tipinfo">点击选择地区</span>
							</td>
							
							<td width="80" height="30" align="right">地址</td>
							<td>
								<s:textfield name="branch.address" maxlength="30" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入地址</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">联系人</td>
							<td>
								<s:textfield name="branch.contact" maxlength="10" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入联系人</span>
							</td>
							
							<td width="80" height="30" align="right">联系电话</td>
							<td>
								<s:textfield name="branch.phone" cssClass="{required:true, digitOrAllowChars:'-'}" maxlength="20"/>
								<span class="field_tipinfo">请输入数字</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">传真号</td>
							<td>
								<s:textfield name="branch.fax" cssClass="{digitOrAllowChars:'-'}" maxlength="20"/>
								<span class="field_tipinfo"></span>
								<span class="error_tipinfo">传真号格式不对</span>
							</td>
							
							<td width="80" height="30" align="right">邮编</td>
							<td>
								<s:textfield name="branch.zip" cssClass="{digit:true}" maxlength="10"/>
								<span class="field_tipinfo">邮编必须为数字</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">账户类型</td>
							<td>
								<s:select id="id_acctType" name="branch.acctType" list="acctTypeList" headerKey="" headerValue="--请选择--" 
									listKey="value" listValue="name" cssClass="{required:true}" />
								<span class="field_tipinfo">选择账户类型</span>
							</td>
							
							<td width="80" height="30" align="right">账户介质类型</td>
							<td>
								<s:select id="id_acctMediaType" name="branch.acctMediaType" list="acctMediaTypeList" headerKey="" headerValue="--请选择--" 
									listKey="value" listValue="name" cssClass="{required:true}" />
								<span class="field_tipinfo">请选择</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">开户行名称</td>
							<td>
								<s:textfield id="idBank_sel" name="branch.bankName" cssClass="{required:true}"/>
								<span class="field_tipinfo">点击选择</span>
							</td>
							
							<td width="80" height="30" align="right">开户行号</td>
							<td>
								<s:textfield id="idBank" name="branch.bankNo" cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo">点击行名选择</span>
							</td>
						</tr>

						<tr>
							<td width="80" height="30" align="right">账户地区</td>
							<td>
								<s:hidden id="idAccAreaCode" name="branch.accAreaCode" />
								<s:textfield id="idAccAreaCode_sel" name="accAreaName" cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo">点击行名选择</span>
							</td>
							
							<td width="80" height="30" align="right">账号</td>
							<td>
								<s:textfield name="branch.accNo" cssClass="{required:true, digitOrAllowChars:'-'}" maxlength="32"/>
								<span class="field_tipinfo">为数字或“-”</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">账户户名</td>
							<td>
								<s:textfield id="idAccName" name="branch.accName" maxlength="30" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入户名</span>
							</td>
							
							<td width="80" height="30" align="right">风险级别</td>
							<td>
								<s:select name="branch.riskLevel" list="riskLevelTypeList"  
									headerKey="" headerValue="--请选择--" listKey="value" listValue="name" />
								<span class="field_tipinfo">选择风险级别</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">营业执照</td>
							<td>
								<s:textfield name="branch.license" maxlength="32" cssClass="{digitOrLetter:true}"/>
								<span class="field_tipinfo">请输入营业执照</span>
							</td>
							
							<td width="80" height="30" align="right" >营业执照有效期</td>
							<td>
								<s:textfield name="branch.licenseExpDate" 
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true,isShowOthers:false,readOnly:true})"/>
								<span class="field_tipinfo">请选择日期</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">组织机构号</td>
							<td>
								<s:textfield name="branch.organization" maxlength="32" cssClass="{digitOrLetter:true}"/>
								<span class="field_tipinfo">输入组织机构号</span>
							</td>
							<td width="80" height="30" align="right" >组织机构代码有效期</td>
							<td>
								<s:textfield name="branch.organizationExpireDate" 
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true,isShowOthers:false})"/>
								<span class="field_tipinfo">请选择日期yyyy-MM-dd</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right" >税务登记号</td>
							<td>
								<s:textfield name="branch.taxRegCode" size="32"/>
							</td>
							<td width="80" height="30" align="right">法人姓名</td>
							<td>
								<s:textfield name="branch.legalPersonName" maxlength="64"/>
								<span class="field_tipinfo"></span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">法人身份证号码</td>
							<td>
								<s:textfield name="branch.legalPersonIdcard" maxlength="32"/>
								<span class="field_tipinfo"></span>
							</td>
							
							<td width="80" height="30" align="right" >法人身份证有效期</td>
							<td>
								<s:textfield name="branch.legalPersonIdcardExpDate" 
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true,isShowOthers:false,readOnly:true})"/>
								<span class="field_tipinfo">请选择日期</span>
							</td>
						</tr>
						
						
						<tr>
							<td width="80" height="30" align="right" >管理员用户名</td>
							<td>
								<s:textfield id="id_branchAdmin" name="branchAdmin" cssClass="{required:true, digitOrLetter:true, maxlength:20}" />
								<span id="idbranchAdmin_field" class="field_tipinfo">20位以内的字符或数字</span>
							</td>

							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield name="branch.remark" maxlength="128"/>
								<span class="field_tipinfo">请输入备注</span>
							</td>
							<td width="80" height="30" align="right"></td>
							<td>
							</td>
						</tr>
						
						<tr id="idAsMerchTr" style="display: none;">
							<td width="80" height="30" align="right">是否单机产品机构</td>
							<td>
								<s:select name="branch.singleProduct" list="yesOrNoList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" 
									cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
							
							<td colspan="2" height="30" align="left">是否同时作为商户?
								<input type="radio" name="asMerch" value="Y" id="idForAsMerchYes"/><label for="idForAsMerchYes">是</label>
								<input type="radio" name="asMerch" value="N" id="idForAsMerchNo" checked="checked"/><label for="idForAsMerchNo">否</label>
							</td>
						</tr>
						
						<tr id="idSaleProxyTr" style="display: none;">
							<td colspan="2" height="30" align="center">自定义权限吗?
								<input type="radio" name="definePrivilege" value="Y" id="idForDefineYes"/><label for="idForDefineYes">是</label>
								<input type="radio" name="definePrivilege" value="N" id="idForDefineNo" checked/><label for="idForDefineNo">否</label>
							</td>
						</tr>
						
						<tr id="idSaleProxyTreeTr" style="display: none;">
							<td width="80" height="30" align="right" valign="top" style="padding-top: 10px">角色权限</td>
							<td colspan="3">
								<div id="treebox_tree" style="padding:5px;width:300px; background-color:#f5f5f5;border :1px solid Silver;">
									<img src="../../images/loading.gif" />正在加载...
								</div>
							</td>
						</tr>
						
						<tr>	
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="提交" id="input_btn2"  name="ok" onclick="return checkAllParams();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm'); clearCarBinError();" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/branch/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_BRANCH_ADD"/>
					<s:hidden id="privileges" name="privileges" />
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>