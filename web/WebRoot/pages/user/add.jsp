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
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script language="javascript">
		$(function(){
			// 只有运营中心或运营分支机构的角色登录时，此方法方能用
			$('#idRoleType').change(function(){
				$('#idMerchName').val('').unbind().removeMulitselector();
				$('#idMerchNo').val('').attr('disabled', 'true');
				$('#idBranchName').val('').unbind().removeMulitselector();
				$('#idBranchCode').val('').attr('disabled', 'true');
				
				var value = $('#idRoleType').val();
				// 个人用户类型时，机构，商户，部门均不能选
				if (value == '50'){
					$('#idMerch_tr').hide();
					$('#idDept_tr').hide();
					$('#idBranch_tr').hide();
					
					$('#idMerchNo').val('').attr('disabled', 'true');
					$('#idDeptNo').val('').attr('disabled', 'true');
					$('#idBranchCode').html('').attr('disabled', 'true');
				} 
				// 商户类型时，商户可选
				else if (value == '40'){
					$('#idMerch_tr').show();
					$('#idDept_tr').hide();
					$('#idBranch_tr').hide();
					
					$('#idMerchNo').removeAttr('disabled');
					$('#idMerchName').removeAttr('disabled');
					if('${loginRoleType}' == '00'){
						Selector.selectMerch('idMerchName', 'idMerchNo', true);
					} else if('${loginRoleType}' == '01'){
						Selector.selectMerch('idMerchName', 'idMerchNo', true, '', '', '', '${loginBranchCode}');
					}
					
					$('#idDeptNo').val('').attr('disabled', 'true');
					$('#idBranchCode').val('').attr('disabled', 'true');
					$('#idBranchName').val('').attr('disabled', 'true');
				} 
				// 运营中心部门或发卡机构部门类型时，部门类型可选，用下拉
				else if (value == '02' || value == '23'){
					$('#idDept_tr').show();
					$('#idMerch_tr').hide();
					$('#idBranch_tr').hide();
					
					$('#idMerchNo').attr('disabled', 'true');
					$('#idMerchName').attr('disabled', 'true');
					$('#idDeptNo').removeAttr('disabled');
					$('#idDeptNo').load(CONTEXT_PATH + '/pages/role/loadDept.do', 
						{'callId':callId(), 'roleType':value}, function(){setTimeout('SysStyle.initSelect()', 100);});
					$('#idBranchCode').attr('disabled', 'true');
					$('#idBranchName').attr('disabled', 'true');
				} else {
					$('#idMerch_tr').hide();
					$('#idDept_tr').hide();
					$('#idBranch_tr').show();
					
					$('#idMerchNo').val('').attr('disabled', 'true');
					$('#idMerchName').attr('disabled', 'true');
					
					$('#idDeptNo').val('').attr('disabled', 'true');
					$('#idBranchCode').removeAttr('disabled');
					$('#idBranchName').removeAttr('disabled');
					
					if('${loginRoleType}' == '00'){
						Selector.selectBranch('idBranchName', 'idBranchCode', true, value);
					} else if('${loginRoleType}' == '01'){
						Selector.selectBranch('idBranchName', 'idBranchCode', true, value, '', '', '${loginBranchCode}');
					}
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
						<caption>${ACT.name}</caption>
						
						<tr>
							<td width="80" height="30" align="right">用户编号</td>
							<td>
								<s:textfield name="userInfo.userId" cssClass="{required:true, digitOrLetter:true}"/>
								<span class="field_tipinfo">输入用户编号</span>
								<span class="error_tipinfo">请输入字符数字</span>
							</td>
							<td width="80" height="30" align="right">用户名称</td>
							<td>
								<s:textfield name="userInfo.userName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入用户名称</span>
							</td>
						</tr>
						<!-- 运营中心或分支机构登录时，可以给机构或商户添加用户 -->
						<s:if test="loginRoleType == 00 || loginRoleType == 01">
							<tr>
								<td width="80" height="30" align="right">所属角色类型</td>
								<td>
									<s:select id="idRoleType" name="roleType" list="roleTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
									<span class="field_tipinfo">请选择角色类型</span>
								</td>
							</tr>
							<tr id="idBranch_tr" style="display:none;">
								<td width="80" height="30" align="right">所属机构</td>
								<td>
									<s:hidden id="idBranchCode" name="userInfo.branchNo"/>
									<s:textfield id="idBranchName" name="userBranchName" cssClass="{required:true}"/>
									<span class="field_tipinfo">请选择所属机构</span>
								</td>
							</tr>
							<tr id="idDept_tr" style="display:none;">
								<td width="80" height="30" align="right">所属部门</td>
								<td>
									<s:select id="idDeptNo" name="userInfo.deptId" list="depts" headerKey="" headerValue="--请选择--" listKey="deptId" listValue="deptName" cssClass="{required:true}" disabled="disabled" onmouseover="FixWidth(this)"></s:select>
									<span class="field_tipinfo">请选择所属机构</span>
								</td>
							</tr>
							<tr id="idMerch_tr" style="display:none;">
								<td width="80" height="30" align="right">所属商户</td>
								<td>
									<s:hidden id="idMerchNo" name="userInfo.merchantNo"/>
									<s:textfield id="idMerchName" name="userMerchName" cssClass="{required:true}"/>
									<span class="field_tipinfo">请选择所属商户</span>
								</td>
							</tr>
						</s:if>
						<s:elseif test="loginRoleType == 20">
							<tr>
								<td width="80" height="30" align="right">所属机构</td>
								<td>
									<s:select id="idBranchCode" name="userInfo.branchNo" list="branchs" listKey="branchCode" listValue="branchName" cssClass="{required:true}" disabled="disabled" onmouseover="FixWidth(this)"></s:select>
									<span class="field_tipinfo">请选择所属机构</span>
								</td>
							</tr>
							<tr>
								<td width="80" height="30" align="right">所属部门</td>
								<td>
									<s:select id="idDeptNo" name="userInfo.deptId" list="depts" headerKey="" headerValue="--请选择--" listKey="deptId" listValue="deptName" disabled="disabled" onmouseover="FixWidth(this)"></s:select>
									<span class="field_tipinfo">请选择所属机构</span>
								</td>
							</tr>
						</s:elseif>
						<s:else>
							<s:if test="showBranch">
							<tr>
								<td width="80" height="30" align="right">所属机构</td>
								<td>
									<s:select id="idBranchCode" name="userInfo.branchNo" list="branchs" listKey="branchCode" listValue="branchName" cssClass="{required:true}" disabled="disabled" onmouseover="FixWidth(this)"></s:select>
									<span class="field_tipinfo">请选择所属机构</span>
								</td>
							</tr>
							</s:if>
							<s:if test="showDept">
							<tr>
								<td width="80" height="30" align="right">所属部门</td>
								<td>
									<s:select id="idDeptNo" name="userInfo.deptId" list="depts" listKey="deptId" listValue="deptName" cssClass="{required:true}" disabled="disabled" onmouseover="FixWidth(this)"></s:select>
									<span class="field_tipinfo">请选择所属机构</span>
								</td>
							</tr>
							</s:if>
							<s:if test="showMerch">
							<tr>
								<td width="80" height="30" align="right">所属商户</td>
								<td>
									<s:select id="idMerchNo" name="userInfo.merchantNo" list="merchs" listKey="merchId" listValue="merchName" cssClass="{required:true}" disabled="disabled" onmouseover="FixWidth(this)"></s:select>
									<span class="field_tipinfo">请选择所属商户</span>
								</td>
							</tr>
							</s:if>
						</s:else>
						<tr>
							<td width="80" height="30" align="right">联系电话</td>
							<td>
								<s:textfield name="userInfo.phone" cssClass="{digit:true}"/>
								<span class="field_tipinfo">请输入联系电话</span>
							</td>	
							<td width="80" height="30" align="right">手机号码</td>
							<td>
								<s:textfield name="userInfo.mobile" cssClass="{digit:true}"/>
								<span class="field_tipinfo">请输入手机号码</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">Email</td>
							<td>
								<s:textfield name="userInfo.email" cssClass="{email:true}"/>
								<span class="field_tipinfo">请输入Email</span>
							</td>	
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<s:token name="_TOKEN_USER_ADD"/>
								<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return validateForm()"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" style="margin-left:30px;" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/user/list.do?goBack=goBack')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token/>
					
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