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
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		function setDefaultBranch(){
			var cardBranchId = $('#idBranchCode').val();
			var cardBranchName = $('#idBranchCode_sel').val();
			if (cardBranchId != null && cardBranchId != undefined) {
				$('#idProxyId_').unbind();
				$('#idProxyId_').val(cardBranchId);
				$('#idProxyId_sel').val(cardBranchName);
				$('#idProxyId_sel').removeMulitselector();
				Selector.selectProxy('idProxyId_sel', 'idProxyId_', true, cardBranchId, '21');

				$('#idMerchId_sel').unbind();
				$('#idMerchId_sel').val('');
				$('#idMerchId').val('');
				$('#idProxyId_sel').removeMulitselector();
				if('${loginRoleType}' == '00'){
					Selector.selectMerch('idMerchId_sel', 'idMerchId', false, '', '', '', '', '', cardBranchId);
				} else if('${loginRoleType}' == '01'){
					Selector.selectMerch('idMerchId_sel', 'idMerchId', false, '', '', '', '${loginBranchCode}', '', cardBranchId);
				}
			}
		}
		$(function(){
			if('${loginRoleType}' == '00'){
				//Selector.selectMerch('idMerchId_sel', 'idMerchId', false);
				Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '20', setDefaultBranch);
			} else if('${loginRoleType}' == '01'){
				//Selector.selectMerch('idMerchId_sel', 'idMerchId', false, '', '', '', '${loginBranchCode}', '', );
				Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '20', setDefaultBranch, '', '${loginBranchCode}');
			}
			//Selector.selectMerch('idMerchId_sel', 'idMerchId', false);
			//Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '20', setDefaultBranch);
			$('#idProxyId_sel').focus(function(){
				var cardBranchId = $('#idBranchCode').val();
				if (isEmpty(cardBranchId)){
					showMsg('请先选择发卡机构再选择代理机构');
				}
			});
			$('#idMerchId_sel').focus(function(){
				var cardBranchId = $('#idBranchCode').val();
				if (isEmpty(cardBranchId)){
					showMsg('请先选择发卡机构再选择商户');
				}
			});
		});
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
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								<s:hidden id="idBranchCode" name="cardToMerch.branchCode"/>
								<s:textfield id="idBranchCode_sel" name="branchCode_sel" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择发卡机构</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">代理机构</td>
							<td>
								<s:hidden id="idProxyId_" name="cardToMerch.proxyId"/>
								<s:textfield id="idProxyId_sel" name="proxyId_sel"/>
								<span class="field_tipinfo">请选择代理机构</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">商户</td>
							<td>
								<s:hidden id="idMerchId" name="merchs"/>
								<s:textfield id="idMerchId_sel" name="merchId_sel" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择商户</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/cardMerch/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARD_MERCH_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>