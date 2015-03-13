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
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
			$(function(){
				var showCenter = $('#id_showCenter').val();
				var showCard = $('#id_showCard').val();
				//营运中心、营运中心部门
				if(showCenter=='true'){
					$('#id_branchCode_center_tr').show();
					$('#id_branchCode_card_tr').hide();
					$('#branchName').removeAttr('disabled');
					$('#id_cardIssuer').removeAttr('disabled');
					$('#id_cardIssuer_card').attr('disabled', true);
					Selector.selectBranch('branchName', 'id_cardIssuer', true, '20', loadCardBin);
				}
				// 发卡机构、发卡机构部门
				else if(showCard=='true'){
					$('#id_branchCode_center_tr').hide();
					$('#id_branchCode_card_tr').show();
					$('#branchName').attr('disabled', true);
					$('#id_cardIssuer').attr('disabled', true);
					$('#id_cardIssuer_card').removeAttr('disabled');
					var branchCode = $('#id_cardIssuer_card').val();
					Selector.selectCardBin('cardBinNo_sel_1', 'idCardBin_1', true, branchCode);
					Selector.selectCardBin('cardBinNo_sel_2', 'idCardBin_2', true, branchCode);
				}
			});
			
			function loadCardBin() {
				var branchCode = $('#id_cardIssuer').val();
					
				if (!isEmpty(branchCode)){
					$('#cardBinNo_sel_1').unbind().removeMulitselector();
					$('#cardBinNo_sel_2').unbind().removeMulitselector();
					
					$('#cardBinNo_sel_1').val('');
					$('#idCardBin_1').val('');
					$('#cardBinNo_sel_2').val('');
					$('#idCardBin_2').val('');
					
					Selector.selectCardBin('cardBinNo_sel_1', 'idCardBin_1', true, branchCode);
					Selector.selectCardBin('cardBinNo_sel_2', 'idCardBin_2', true, branchCode);
				} else {
					$('#cardBinNo_sel_1').focus(function(){
						var branchCode = $('#id_cardIssuer').val();
						if (isEmpty(branchCode)){
							showMsg('请先选择发卡机构再选择卡BIN');
						}
					});
					$('#cardBinNo_sel_2').focus(function(){
						var branchCode = $('#id_cardIssuer').val();
						if (isEmpty(branchCode)){
							showMsg('请先选择发卡机构再选择卡BIN');
						}
					});
				}
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<s:hidden id="id_showCenter" name="showCenter"></s:hidden>
					<s:hidden id="id_showCard" name="showCard"></s:hidden>
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr id="id_branchCode_center_tr">
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
							<s:hidden id="id_cardIssuer" name="transAccRuleDef.branchCode" disabled="true"></s:hidden>
							<s:textfield  id="branchName" cssClass="{required:true}" disabled="true"></s:textfield>
							<span class="field_tipinfo">请选择发卡机构</span>
							</td>
						</tr>
						<tr id="id_branchCode_card_tr">	
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
							<s:textfield id="id_cardIssuer_card" name="transAccRuleDef.branchCode" disabled="true" cssClass="readonly" readonly="true"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡BIN_1</td>
							<td>
								<s:hidden id="idCardBin_1" name="transAccRuleDef.cardBin1"/>
								<s:textfield id="cardBinNo_sel_1" name="cardBinNo_sel_1" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择卡BIN_1</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡BIN_2</td>
							<td>
								<s:hidden id="idCardBin_2" name="transAccRuleDef.cardBin2"/>
								<s:textfield id="cardBinNo_sel_2" name="cardBinNo_sel_2" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择卡BIN_2</span>
							</td>
						</tr>
						<tr style="margin-top: 30px;">
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/transAccRuleDef/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_TRANSACCRULEDEF_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>