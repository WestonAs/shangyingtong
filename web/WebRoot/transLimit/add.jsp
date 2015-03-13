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
				var showFenZhi = $('#id_showFenZhi').val();
				var parent = $('#id_parent').val();
				
				//营运中心、营运中心部门
				if(showCenter=='true' || showFenZhi=='true'){
					$('#id_branchCode_center_tr').show();
					$('#id_branchCode_card_tr').hide();
					$('#branchName').removeAttr('disabled');
					$('#id_cardIssuer').removeAttr('disabled');
					$('#id_cardIssuer_card').attr('disabled', true);
					
					// 营运中心、中心部门
					if(showCenter=='true'){
						Selector.selectBranch('branchName', 'id_cardIssuer', true, '20', loadMerchGroup);
					}
					// 分支机构
					else if(showFenZhi=='true'){
						Selector.selectBranch('branchName', 'id_cardIssuer', true, '20', loadMerchGroup, '', parent);
					}
				}
				// 发卡机构、发卡机构部门
				else if(showCard=='true'){
					$('#id_branchCode_center_tr').hide();
					$('#id_branchCode_card_tr').show();
					$('#branchName').attr('disabled', true);
					$('#id_cardIssuer').attr('disabled', true);
					$('#id_cardIssuer_card').removeAttr('disabled');
					var branchCode = $('#id_cardIssuer_card').val();
					Selector.selectMerch('merchName', 'idMerchId', false, branchCode);
					Selector.selectCardBin('cardBinNo_sel', 'idCardBinNo', false, branchCode);
				}
			});
			
			function loadMerchGroup() {
				var branchCode = $('#id_cardIssuer').val();
					
				if (!isEmpty(branchCode)){
					$('#merchName').unbind().removeMulitselector();
					$('#cardBinNo_sel').unbind().removeMulitselector();
					
					$('#merchName').val('');
					$('#idMerchId').val('');
					$('#cardBinNo_sel').val('');
					$('#idCardBinNo').val('');
					Selector.selectMerch('merchName', 'idMerchId', false, branchCode);
					Selector.selectCardBin('cardBinNo_sel', 'idCardBinNo', false, branchCode);
				} else {
					$('#merchName').focus(function(){
						var branchCode = $('#id_cardIssuer').val();
						if (isEmpty(branchCode)){
							showMsg('请先选择发卡机构再选择商户');
						}
					});
					$('#cardBinNo_sel').focus(function(){
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
					<s:hidden id="id_showFenZhi" name="showFenZhi"></s:hidden>
					<s:hidden id="id_parent" name="parent"></s:hidden>
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr id="id_branchCode_center_tr">
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
							<s:hidden id="id_cardIssuer" name="transLimit.cardIssuer" disabled="true"></s:hidden>
							<s:textfield  id="branchName" cssClass="{required:true}" disabled="true"></s:textfield>
							<span class="field_tipinfo">请选择发卡机构</span>
							</td>
						</tr>
						<tr id="id_branchCode_card_tr">	
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
							<s:textfield id="id_cardIssuer_card" name="transLimit.cardIssuer" disabled="true" cssClass="readonly" readonly="true"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">商户</td>
							<td> 
								<s:hidden id="idMerchId" name="merchs" />
								<s:textfield id="merchName" name="merchName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择商户</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡BIN</td>
							<td>
								<s:hidden id="idCardBinNo" name="cardBins"/>
								<s:textfield id="cardBinNo_sel" name="cardBinNo_sel" />
								<span class="field_tipinfo">不填默认为  *，表示通用卡BIN</span>
							</td>
						</tr>
						<!--<tr>
							<td width="80" height="30" align="right">交易类型</td>
							<td>
							<s:select name="transLimit.transType" list="transTypeList" headerKey="  " headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							<span class="field_tipinfo">请选择交易类型</span>
							</td>
						</tr>
						--><tr style="margin-top: 30px;">
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/transLimit/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_TRANSLIMIT_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
				<span class="note_div">注释</span>
				<ul class="showli_div">
				<li class="showli_div">此规则为允许规则。</li>
				<li class="showli_div">如果没有设置控制规则，默认为不能做交易。</li>
				<li class="showli_div">如果设置了，那么定义了的才能做交易。如果未设置卡BIN，卡BIN默认为 *，表示所有卡BIN都能做交易。</li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>