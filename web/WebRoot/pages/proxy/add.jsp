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
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:js src="/js/tree/dhtmlxcommon.js"/>
		<f:js src="/js/tree/dhtmlxtree.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			$('#idProxyType').change(function(){
				var value = $(this).val();
				$('#idProxyId_sel').removeMulitselector();
				$('#idRespOrgId_sel').removeMulitselector();
				$('#idProxyId').val('');
				$('#idProxyId_sel').val('');
				$('#idRespOrgId').val('');
				$('#idRespOrgId_sel').val('');
				var loginRoleType = '${loginRoleType}';
				var loginBranchCode = '${loginBranchCode}';
				if (value == '11') {
					if(loginRoleType == '00'){// 运营中心登录
						Selector.selectBranch('idProxyId_sel', 'idProxyId', true, '11');
					} else {
						Selector.selectBranch('idProxyId_sel', 'idProxyId', true, '11', '', '', loginBranchCode);
					}
					//Selector.selectBranch('idRespOrgId_sel', 'idRespOrgId', true, '00');
					//$('#idRespOrgTd').html('营运机构');
					//$('#idRespOrgId').val('00000000');
					//$('#idRespOrgId_sel').val('运营中心');
					$('#idRespOrgTr_2').show();
					$('#idRespOrgTr_1').hide();
					$('#idRespOrgTd_3').removeAttr('disabled');
					$('#idRespOrgId').attr('disabled', true);
					$('#idRespOrgId_sel').attr('disabled', true);
					
					$('tr[id^="idPrivilege_td"]').hide();
				} 
				else if(value == '21'||value == '22'){
					$('#idRespOrgTr_1').show();
					$('#idRespOrgTr_2').hide();
					$('#idRespOrgTd_3').attr('disabled', true);
					$('#idRespOrgId').removeAttr('disabled');
					$('#idRespOrgId_sel').removeAttr('disabled');
					if (value == '21') {
						if(loginRoleType == '00'){// 运营中心登录
							Selector.selectBranch('idProxyId_sel', 'idProxyId', true, '21');
							Selector.selectBranch('idRespOrgId_sel', 'idRespOrgId', true, '20');
						} else {
							Selector.selectBranch('idProxyId_sel', 'idProxyId', true, '21', '', '', loginBranchCode);
							Selector.selectBranch('idRespOrgId_sel', 'idRespOrgId', true, '20', '', '', loginBranchCode);
						}
						//Selector.selectBranch('idProxyId_sel', 'idProxyId', true, '21');
						//Selector.selectBranch('idRespOrgId_sel', 'idRespOrgId', true, '20');
						$('#idRespOrgTd').html('发卡机构');
					
						$('tr[id^="idPrivilege_td"]').hide();
					} else if (value == '22') {
						//Selector.selectBranch('idProxyId_sel', 'idProxyId', true, '22');
						//Selector.selectBranch('idRespOrgId_sel', 'idRespOrgId', true, '20');
						if(loginRoleType == '00'){// 运营中心登录
							Selector.selectBranch('idProxyId_sel', 'idProxyId', true, '22');
							Selector.selectBranch('idRespOrgId_sel', 'idRespOrgId', true, '20');
						} else {
							Selector.selectBranch('idProxyId_sel', 'idProxyId', true, '22', '', '', loginBranchCode);
							Selector.selectBranch('idRespOrgId_sel', 'idRespOrgId', true, '20', '', '', loginBranchCode);
						}
						$('#idRespOrgTd').html('发卡机构');
					
						$('tr[id^="idPrivilege_td"]').show();
					}
				}
				else{
					$('#idRespOrgTr_1').hide();
					$('#idRespOrgTr_2').hide();
					$('#idRespOrgTd_3').attr('disabled', true);
					$('#idRespOrgId').attr('disabled', true);
					$('#idRespOrgId_sel').attr('disabled', true);
					$('tr[id^="idPrivilege_td"]').hide();
				}
			});
		});
		function checkTree(){
			if (!isDisplay('idPrivilege_td1')){
				return true;
			}
			if (!FormUtils.hasSelected('groups')) {
				showMsg("请选择权限点!");
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
							<td width="120" height="30" align="right">代理标志</td>
							<td>
								<s:select id="idProxyType" name="branchProxy.proxyType" list="types" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择代理标志</span>
							</td>
						</tr>
						<tr>
							<td width="120" height="30" align="right">代理机构</td>
							<td>
								<s:hidden id="idProxyId" name="branchProxy.proxyId" />
								<s:textfield id="idProxyId_sel" name="proxyId_sel" cssClass="{required:true}" readonly="true"/>
								<span class="field_tipinfo">请选择代理机构</span>
							</td>
						</tr>
						<tr id="idRespOrgTr_1" style="display: none">
							<td width="120" height="30" align="right" id="idRespOrgTd">发卡机构</td>
							<td>
								<s:hidden id="idRespOrgId" name="branchProxy.respOrg" disabled="true"/>
								<s:textfield id="idRespOrgId_sel" disabled="true" name="respOrg_sel" cssClass="{required:true}" readonly="true"/>
								<span class="field_tipinfo">请选择机构</span>
							</td>
						</tr>
						<tr id="idRespOrgTr_2" style="display: none">
						    <td width="120" height="30" align="right" id="idRespOrgTd_2">营运机构</td>
							<td>
							    <s:select disabled="true" id="idRespOrgTd_3" name="branchProxy.respOrg" list="respOrgList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择机构</span>
							</td>
						</tr>
						<tr id="idPrivilege_td1" style="display: none;">
							<td width="120" height="30" align="right" class="nes formlabel">可分配权限点</td>
							<td>
								<s:iterator value="cardPrivilegeList">
									<input type="checkbox" name="groups" value="${id}" />${name}<br />
									</span>
								</s:iterator>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return checkTree()" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/proxy/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_PROXY_ADD"/>
					<s:hidden id="privileges" name="privileges" />
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>