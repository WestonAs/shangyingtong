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
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
				var parent =  $('#idParent').val();
				var branchCode = $('#id_BranchCode').val();

				Selector.selectBranch('id_branchName', 'id_BranchCode', true, '20', loadMerch, '', parent);
								
				$('#id_insType').change(function(){
					var insType = $(this).val();

					// 发卡机构
					if(insType == '0'){
						$('#id_merch_1').hide();
						$('#id_merch_2').hide();
						$('#id_MerchId').attr('disabled', 'true');
						$('#id_merchName').attr('disabled', 'true');
						$('#id_service_tr').show();
					}
					// 商户
					else if(insType == '1'){
						$('#id_merch_1').show();
						$('#id_merch_2').show();
						$('#id_MerchId').removeAttr('disabled');
						$('#id_merchName').removeAttr('disabled');
						$('#id_BranchCode').val('');
						$('#id_branchName').val('');
						
						$('#id_service_tr').show();
						
						$('#id_merchName').focus(function(){
							var insType = $('#id_insType').val();
							var branchCode = $('#id_BranchCode').val();
							//alert("branchCode = " + branchCode);
							if (isEmpty(insType)){
								showMsg('请先选择机构类型');
							}
							
							if (isEmpty(branchCode)){
								showMsg('请先选择发卡机构再选择商户');
							} 
							
						});
					}
					else if(insType == ''){
						$('#id_merch_1').hide();
						$('#id_merch_2').hide();
						$('#id_MerchId').attr('disabled', 'true');
						$('#id_merchName').attr('disabled', 'true');
						$('#id_BranchCode').val('');
						$('#id_branchName').val('');
						
						$('#id_service_tr').hide();
					}
				});
				
				$('#id_branchName').focus(function(){
					var insType = $('#id_insType').val();
					
					if (isEmpty(insType)){
						showMsg('请先选择机构类型');
					}
				});

				function loadMerch() {
					var insType = $('#id_insType').val();
					var branchCode = $('#id_BranchCode').val();
					
					if (isEmpty(insType)){
						showMsg('请先选择机构类型');
					}
						
					if (!isEmpty(branchCode)){
						$('#id_merchName').unbind().removeMulitselector();
						
						$('#id_MerchId').val('');
						$('#id_merchName').val('');
						Selector.selectMerch('id_merchName', 'id_MerchId', true, branchCode);
					} else {
						$('#id_merchName').focus(function(){
							if (isEmpty(insType)){
								showMsg('请先选择机构类型');
							}
					
							if (isEmpty(branchCode)){
								showMsg('请先选择发卡机构再选择商户');
							}
						});
					}
				}
				
			});
			
			function bind(){
				var insType = $('#id_insType').val(); 
				
				$("#id_service").load(CONTEXT_PATH + "/para/insServicePara/serviceList.do",
					{'insServiceAuthority.insType':insType, 'callId':callId()});	
				
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
						<s:hidden id="idParent" name="parent" />
						<tr>
						<td width="80" height="30" align="right">机构类型</td>
						<td>
							<s:select id="id_insType" name="insServiceAuthority.insType" onchange="bind();" list="insTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo">请选择发卡机构或者商户</span>
						</td>
						</tr>
						<tr>
						<td width="80" height="30" align="right">发卡机构</td>
						<td id="id_branch_2">
							<s:hidden id="id_BranchCode" name="branchCode" cssClass="{required:true}" ></s:hidden>
							<s:textfield  id="id_branchName" cssClass="{required:true}" ></s:textfield>
						</td>
						</tr>
						<tr>
						<td id="id_merch_1" width="80" height="30" align="right" style="display: none">商户</td>
						<td id="id_merch_2" style="display: none">
							<s:hidden id="id_MerchId" name="insServiceAuthority.insId"
								cssClass="{required:true}" disabled="true"></s:hidden>
							<s:textfield  id="id_merchName" cssClass="{required:true}" disabled="true"></s:textfield>
							<span class="field_tipinfo">请先选择发卡机构</span>
						</td>
						</tr>
						<tr id="id_service_tr" style="display: none;">
							<td width="80" height="30" align="right">业务类型</td>
							<td id="id_service">
							<span class="field_tipinfo">选择开通的业务类型</span>
							</td>
						</tr>	
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/para/insServicePara/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_INSSERVICE_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>