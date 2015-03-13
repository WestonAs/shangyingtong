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
		<f:css href="/js/tree/dhtmlxtree.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/tree/dhtmlxcommon.js"/>
		<f:js src="/js/tree/dhtmlxtree.js"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script language="javascript">
		$(function(){
			$loadTree('/pages/role/initTreeByAdd.do', 'treebox_tree', true);
			
			// 只有运营中心或运营分支机构的角色登录时，此方法方能用
			$('#idRoleType').change(function(){
				$('#idMerchName').val('').unbind().removeMulitselector();
				$('#idMerchNo').val('').attr('disabled', 'true');
				$('#idBranchName').val('').unbind().removeMulitselector();
				$('#idBranchCode').val('').attr('disabled', 'true');
				
				var value = $('#idRoleType').val();
				if (value == '50'){
					$('#idMerch_tr').hide();
					$('#idDept_tr').hide();
					$('#idBranch_tr').hide();
					
					$('#idMerchNo').val('').attr('disabled', 'true');
					$('#idMerchName').val('').attr('disabled', 'true');
					$('#idDeptNo').val('').attr('disabled', 'true');
					$('#idBranchCode').val('').attr('disabled', 'true');
					$('#idBranchName').val('').attr('disabled', 'true');
				} else if (value == '40'){
					$('#idMerch_tr').show();
					$('#idDept_tr').hide();
					$('#idBranch_tr').hide();
					
					//$('#idMerchNo').load(CONTEXT_PATH + '/pages/role/loadMerch.do', 
					//	{'callId':callId(), 'roleType':value}, function(){setTimeout('SysStyle.initSelect()', 100);}).removeAttr('disabled');
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
				} else if (value == '02' || value == '23'){
					$('#idDept_tr').show();
					$('#idMerch_tr').hide();
					$('#idBranch_tr').hide();
					
					$('#idMerchNo').attr('disabled', 'true');
					$('#idMerchName').val('').attr('disabled', 'true');
					$('#idDeptNo').load(CONTEXT_PATH + '/pages/role/loadDept.do', 
						{'callId':callId(), 'roleType':value}, function(){setTimeout('SysStyle.initSelect()', 100);}).removeAttr('disabled');
					$('#idBranchCode').attr('disabled', 'true');
					$('#idBranchName').val('').attr('disabled', 'true');
				} else {
					$('#idMerch_tr').hide();
					$('#idDept_tr').hide();
					$('#idBranch_tr').show();
					
					$('#idMerchNo').val('').attr('disabled', 'true');
					$('#idMerchName').val('').attr('disabled', 'true');
					$('#idDeptNo').val('').attr('disabled', 'true');
					//$('#idBranchCode').load(CONTEXT_PATH + '/pages/role/loadBranch.do', 
					//	{'callId':callId(), 'roleType':value}, function(){setTimeout('SysStyle.initSelect()', 100);}).removeAttr('disabled');
					
					$('#idBranchCode').removeAttr('disabled');
					$('#idBranchName').removeAttr('disabled');
					if('${loginRoleType}' == '00'){
						Selector.selectBranch('idBranchName', 'idBranchCode', true, value);
					} else if('${loginRoleType}' == '01'){
						Selector.selectBranch('idBranchName', 'idBranchCode', true, value, '', '', '${loginBranchCode}');
					}
				}
				
				$('#treebox_tree').html(LOAD_IMAGE);
				$loadTree('/pages/role/initTreeByRoleType.do?roleType=' + value, 'treebox_tree', true);
			});
		});
		function checkTree(){
			var privilege = tree.getAllCheckedBranches();
			if (privilege == null || privilege == undefined || privilege == "") {
				alert("请选择权限!");
				return false;
			}
			$('#privileges').val(privilege);
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
						<!-- 
						<tr>
							<td width="100" height="30" align="right">角色编号</td>
							<td>
								<s:textfield name="roleInfo.roleId" cssClass="{required:true, digitOrLetter:true}"/>
								<span class="field_tipinfo">请输入角色编号</span>
							</td>
						</tr>
						 -->
						<tr>
							<td width="100" height="30" align="right">角色名称</td>
							<td>
								<s:textfield name="roleInfo.roleName" cssClass="{required:true, maxlength:28}" maxlength="28"/>
								<span class="field_tipinfo">请输入角色名称（最多28个中英文字符）</span>
							</td>
						</tr>
						<s:if test="loginRoleType == 00 || loginRoleType == 01">
							<tr>
								<td width="100" height="30" align="right">角色类型</td>
								<td>
									<s:select id="idRoleType" name="roleInfo.roleType" list="roleTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
									<span class="field_tipinfo">请选择角色类型</span>
								</td>
							</tr>
							<tr id="idBranch_tr" style="display:none;">
								<td width="100" height="30" align="right">所属机构</td>
								<td>
									<s:hidden id="idBranchCode" name="roleInfo.branchNo"/>
									<s:textfield id="idBranchName" name="userBranchName" cssClass="{required:true}"/>
									<span class="field_tipinfo">请选择所属机构</span>
								</td>
							</tr>
							<tr id="idMerch_tr" style="display:none;">
								<td width="100" height="30" align="right">所属商户</td>
								<td>
									<s:hidden id="idMerchNo" name="roleInfo.merchantNo"/>
									<s:textfield id="idMerchName" name="userMerchName" cssClass="{required:true}"/>
									<span class="field_tipinfo">请选择所属商户</span>
								</td>
							</tr>
							<tr id="idDept_tr" style="display:none;">
								<td width="100" height="30" align="right">所属部门</td>
								<td>
									<s:select id="idDeptNo" name="roleInfo.deptId" list="deptList" headerKey="" headerValue="--请选择--" listKey="deptId" listValue="deptName" cssClass="{required:true}" disabled="disabled" onmouseover="FixWidth(this)"></s:select>
									<span class="field_tipinfo">请选择所属部门</span>
								</td>
							</tr>
						</s:if>
						<s:elseif test="loginRoleType == 20">
							<s:if test="roleTypeList != null && roleTypeList.size > 0">
							<tr>
								<td width="100" height="30" align="right">角色类型</td>
								<td>
									<s:select id="idRoleType" name="roleInfo.roleType" list="roleTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
									<span class="field_tipinfo">请选择角色类型</span>
								</td>
							</tr>
							</s:if>
							<tr>
								<td width="100" height="30" align="right">所属机构</td>
								<td>
									<s:select id="idBranchCode" name="roleInfo.branchNo" list="branchList" listKey="branchCode" listValue="branchName" cssClass="{required:true}" disabled="disabled" onmouseover="FixWidth(this)"></s:select>
									<span class="field_tipinfo">请选择所属机构</span>
								</td>
							</tr>
							<tr>
								<td width="100" height="30" align="right">所属部门</td>
								<td>
									<s:select id="idDeptNo" name="roleInfo.deptId" list="deptList" headerKey="" headerValue="--请选择--" listKey="deptId" listValue="deptName" cssClass="{required:true}" disabled="disabled" onmouseover="FixWidth(this)"></s:select>
									<span class="field_tipinfo">请选择所属部门</span>
								</td>
							</tr>
						</s:elseif>
						<s:else>
							<s:if test="roleTypeList != null && roleTypeList.size > 0">
							<tr>
								<td width="100" height="30" align="right">角色类型</td>
								<td>
									<s:select id="idRoleType" name="roleInfo.roleType" list="roleTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
									<span class="field_tipinfo">请选择角色类型</span>
								</td>
							</tr>
							</s:if>
							<s:if test="showBranch">
							<tr>
								<td width="100" height="30" align="right">所属机构</td>
								<td>
									<s:select id="idBranchCode" name="roleInfo.branchNo" list="branchList" listKey="branchCode" listValue="branchName" cssClass="{required:true}" onmouseover="FixWidth(this)"></s:select>
									<span class="field_tipinfo">请选择所属机构</span>
								</td>
							</tr>
							</s:if>
							<s:if test="showMerch">
							<tr>
								<td width="100" height="30" align="right">所属商户</td>
								<td>
									<s:select id="idMerchNo" name="roleInfo.merchantNo" list="merchList" listKey="merchId" listValue="merchName" cssClass="{required:true}" onmouseover="FixWidth(this)"></s:select>
									<span class="field_tipinfo">请选择所属商户</span>
								</td>
							</tr>
							</s:if>
							<s:if test="showDept">
							<tr>
								<td width="100" height="30" align="right">所属部门</td>
								<td>
									<s:select id="idDeptNo" name="roleInfo.deptId" list="deptList" listKey="deptId" listValue="deptName" cssClass="{required:true}" onmouseover="FixWidth(this)"></s:select>
									<span class="field_tipinfo">请选择所属部门</span>
								</td>
							</tr>
							</s:if>
						</s:else>
						<tr>
							<td width="100" height="30" align="right" valign="top" style="padding-top: 10px">角色权限</td>
							<td>
								<div id="treebox_tree" style="padding:5px;width:300px; background-color:#f5f5f5;border :1px solid Silver;">
									<img src="../../images/loading.gif" />正在加载...
								</div>
							</td>
						</tr>
						<tr><td>&nbsp;</td><td><input id="selectAll" type="checkbox" onclick="checkAllTree('selectAll', tree)" /><label for="selectAll">全选</label></td></tr>
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2" onclick="return checkTree()"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/role/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_ROLE_ADD"/>
					<s:hidden id="privileges" name="privileges" />
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>