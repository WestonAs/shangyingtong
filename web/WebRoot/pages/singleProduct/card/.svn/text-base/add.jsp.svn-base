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
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
			$(function(){
				Selector.selectBranch('id_makeBranchName', 'id_makeBranch', true, '30');
				if('${loginRoleType}' == '00'){
					$('#id_brancheCode').val('');
					//Selector.selectBranch('id_brancheCodeName', 'id_brancheCode', true, '20', loadCardSubClass);
					Selector.selectBranch('id_brancheCodeName', 'id_brancheCode', true, '20', loadCardStyle);
					//Selector.selectBranch('id_makeBranchName', 'id_makeBranch', true, '30');
				} else if('${loginRoleType}' == '01'){
					$('#id_brancheCode').val('');
					//Selector.selectBranch('id_brancheCodeName', 'id_brancheCode', true, '20', loadCardSubClass, '', '${loginBranchCode}');
					Selector.selectBranch('id_brancheCodeName', 'id_brancheCode', true, '20', loadCardStyle, '', '${loginBranchCode}');
					Selector.selectBranch('id_makeBranchName', 'id_makeBranch', true, '30', '', '', '${loginBranchCode}');
					Selector.selectModel('idPlanName_Sel', 'idPlanId', true, '${loginBranchCode}', loadCardStyle);
				}
				
				/*
				$('#idCardSubclass_Sel').focus(function(){
					var branch = $('#id_brancheCode').val();
					if (isEmpty(branch)){
						showMsg('请先选择发卡机构再选择卡类型');
					}
				});*/
				
				$('#id_cardStyleName').focus(function(){
					var planNo = $('#idPlanId').val();
					var branch = $('#id_brancheCode').val();
					if (isEmpty(planNo)
						|| isEmpty(branch)){
						showMsg('请先选择发卡机构和套餐再选择卡样');
					}
				});
				
			});
			
			/*
			function loadCardSubClass(){
				var branchCode = $('#id_brancheCode').val();

				if(!isEmpty(branchCode)){
					$('#idCardSubclass_Sel').unbind().removeMulitselector();
					$('#idCardSubclass_Sel').val('');
					$('#idCardSubclass').unbind();
					$('#idCardSubclass').val('');
					// 卡子类型选择
					Selector.selectCardSubclass('idCardSubclass_Sel', 'idCardSubclass', true, '', branchCode);
				}
			}*/
			
			// 加载所有的卡样类型，选择一个默认卡样
			function loadCardStyle() {
				var planNo = $('#idPlanId').val();
				var branchCode = $('#id_brancheCode').val();
				
				if(isEmpty(planNo) || isEmpty(branchCode)){
					return false;
				}
				
				//alert('loadStyle planId:' + plandNo);
				$('#id_cardStyleName').unbind().removeMulitselector();
				$('#id_cardStyleName').val('');
				$('#id_cardStyleId').unbind();
				$('#id_cardStyleId').val('');
				Selector.selectStyle('id_cardStyleName', 'id_cardStyleId', true, '${loginBranchCode}', planNo, branchCode);
				
				$('#tr_pointExchangeRate').load(CONTEXT_PATH + '/pages/singleProduct/card/loadCity.do', {'planNo':planNo, 'callId':callId}, function(){
					SysStyle.setDataGridStyle();
					SysStyle.setFormGridStyle();
					SysStyle.setSearchGridStyle();
					SysStyle.setDetailGridStyle();
					SysStyle.setPageNavStyle();
					SysStyle.setButtonStyle();
					SysStyle.setNoPrivilegeStyle();
					SysStyle.addFormValidate();
					SysStyle.addRequiredStyle();
				});
				
				$('#idMembDetailTb').load(CONTEXT_PATH + '/pages/singleProduct/card/loadBranch.do', {'planNo':planNo, 'callId':callId}, function(){
					SysStyle.setDataGridStyle();
					SysStyle.setFormGridStyle();
					SysStyle.setSearchGridStyle();
					SysStyle.setDetailGridStyle();
					SysStyle.setPageNavStyle();
					SysStyle.setButtonStyle();
					SysStyle.setNoPrivilegeStyle();
					SysStyle.addFormValidate();
					SysStyle.addRequiredStyle();
				});
			}
			
			function checkSumbitParam() {
				var flag = true;
				$(':input[id^="id_discnt_"]').each(function(){
					var value = $(this).val();
					var tipe = $(this).next();
					if(isEmpty(value)){
						if (tipe != null) {
							tipe.removeClass('field_tipinfo').addClass('error_tipinfo').show();
						}
						flag = false;
						//$(this).focus();
					} else {
						if (tipe != null) {
							tipe.removeClass('error_tipinfo').addClass('field_tipinfo').show();
						}
					}
					return true;
				});
				return flag;
			}
			
			function clearInputParam() {
				$('#id_brancheCode').val('');
				$('#id_brancheCodeName').val('');
				$('#idPlanId').val('');
				$('#idPlanName_Sel').val('');
				$('#id_cardStyleId').val('');
				$('#id_cardStyleName').val('');
				$('#id_makeBranch').val('');
				$('#id_makeBranchName').val('');
			}
		</script>
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
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								<s:hidden id="id_brancheCode" name="cardissuerPlan.brancheCode"/>
								<s:textfield id="id_brancheCodeName" name="brancheCodeName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择发卡机构</span>
							</td>
						</tr>
						<!-- 根据套餐里的模板自动生成卡类型
						<tr>
							<td width="80" height="30" align="right">卡类型</td>
							<td>
								<s:hidden id="idCardSubclass" name="cardissuerPlan.cardSubclass"/>
								<s:textfield id="idCardSubclass_Sel" name="cardSubclassName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择卡子类型</span>
							</td>
						</tr>
						 -->
						<tr>
							<td width="80" height="30" align="right">套餐</td>
							<td>
								<s:hidden id="idPlanId" name="cardissuerPlan.planId"/>
								<s:textfield id="idPlanName_Sel" name="planName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择套餐</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">默认卡样</td>
							<td>
								<s:hidden id="id_cardStyleId" name="cardissuerPlan.defaultCardExample"/>
								<s:textfield id="id_cardStyleName" name="cardStyleName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择默认卡样</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">制卡厂商</td>
							<td>
								<s:hidden id="id_makeBranch" name="makeBranch"/>
								<s:textfield id="id_makeBranchName" name="makeBranchName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择制卡厂商</span>
							</td>
						</tr>
						<tr id="tr_pointExchangeRate">
						</tr>
					</table>
					
					<table class="form_grid" id="idMembDetailTb" width="640px;" border="0" cellspacing="3" cellpadding="0">
					</table>
					
					<table>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2" name="ok" onclick="return checkSumbitParam();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm');clearInputParam();" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/singleProduct/card/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_SINGLEPRODUCT_CARD_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>