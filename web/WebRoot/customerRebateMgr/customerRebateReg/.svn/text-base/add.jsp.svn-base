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
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
			$(function(){
				$('#input_btn2').click(function(){
					if($('#inputForm').validate().form()){
						$('#inputForm').submit();
						$('#input_btn2').attr('disabled', 'true');
						//window.parent.showWaiter();
						$("#loadingBarDiv").css("display","inline");
						$("#contentDiv").css("display","none");
					}
				});
				
				$('#id_cardBranch').change(function(){
					var cardBranch = $('#id_cardBranch').val();
					
					$('#cardBinNo_sel').removeMulitselector();
					$('#cardBinNo_sel').unbind();
					$('#idCardBinNo').val('');
					if(!isEmpty(cardBranch)){
						// 卡BIN选择
						Selector.selectCardBin('cardBinNo_sel', 'idCardBinNo', true, cardBranch);
					}
					
					$('#id_cardCustomer').load(CONTEXT_PATH + '/customerRebateMgr/customerRebateReg/findCustomerRebateType.do', {"cardBranch":cardBranch, 'callId':callId()});

					//加载管理系统售卡、充值使用的返利规则（通用与非通用的返利规则）
					$('select[id^="id_RebateRule"]').load(CONTEXT_PATH + '/customerRebateMgr/customerRebateReg/findRebateRule.do', {"cardBranch":cardBranch, 'callId':callId(), "formMap.type":"web"});
				});
				
				$('#cardBinNo_sel').focus(function(){
					var cardBranch = $('#id_cardBranch').val();
					if(isEmpty(cardBranch)){
						showMsg('请先选择发卡机构，再选择卡BIN');
						return;
					}
				});
				
			});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div id="loadingBarDiv"	style="display: none; width: 100%; height: 100%">
			<table width=100% height=100%>
				<tr>
					<td align=center >
						<center>
							<img src="${CONTEXT_PATH}/images/ajax_saving.gif" align="middle">
							<div id="processInfoDiv"
								style="font-size: 15px; font-weight: bold">
								正在处理中，请稍候...
							</div>
							<br>
							<br>
							<br>
						</center>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="contentDiv" class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						
						<tr>
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								<s:select id="id_cardBranch" name="customerRebateReg.cardBranch" list="cardBranchList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName" cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">购卡客户</td>
							<td>
								<select id="id_cardCustomer" name="customerRebateReg.cardCustomerId"  class="{required:true}">
								</select>
								<span class="field_tipinfo">请选择卡类型</span>								
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡BIN</td>
							<td>
								<s:hidden id="idCardBinNo" name="customerRebateReg.binNo"/>
								<s:textfield id="cardBinNo_sel" name="cardBinNo_sel" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择卡BIN</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">售卡返利规则</td>
							<td>
								<select id="id_RebateRuleSell" name="customerRebateReg.saleRebateId" class="{required:true}"></select>
								<span class="field_tipinfo">请选择售卡返利规则</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">充值返利规则</td>
							<td>
								<select id="id_RebateRuleDeposit" name="customerRebateReg.depositRebateId" class="{required:true}"></select>
								<span class="field_tipinfo">请选择充值返利规则</span>
							</td>							
							
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/customerRebateMgr/customerRebateReg/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CUSTOMERREBATEREG_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>