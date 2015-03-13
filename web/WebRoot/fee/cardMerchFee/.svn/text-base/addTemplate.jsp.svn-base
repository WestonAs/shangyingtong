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
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
			$(function(){
				var branchCode = $('#idBranchCode').val();
				Selector.selectMerch('merchName', 'idMerchId', false, branchCode);
				$('#idMerchGroup').load(CONTEXT_PATH + "/fee/cardMerchFee/getMerchGroup.do", 
					{'branchCode':branchCode, 'callId':callId()});
				
				$('#idMerchFlag').change(function(){
					var value = $(this).val();
					if (value == '0') {
						$('td[id^="idMerchGroup_td"]').hide();
						$('td[id^="idMerch_td"]').show();
						$('#idMerchGroup').attr('disabled', 'true');
						$('#merchName').removeAttr('disabled');
					} else {
						$('td[id^="idMerchGroup_td"]').show();
						$('td[id^="idMerch_td"]').hide();
						$('#merchName').attr('disabled', 'true');
						$('#idMerchGroup').removeAttr('disabled');
					}
				});

				$('#id_templateId').blur(function(){
					var value = $(this).val();
					
					if(isEmpty(value)){ 
						$('tr[id^="idAmtTr"]').hide();
						$('#idFeeRateTr').hide();
						$('#idSectTbl').hide();
						/*$('#id_maxAmt').attr('disabled', 'true');
						$('#id_minAmt').attr('disabled', 'true');
						$('#id_feeRate').attr('disabled', 'true');*/
						return;
					}
					
					$.post(CONTEXT_PATH + '/fee/cardMerchFee/getTempaltePara.do', {'merchFeeTemplate.templateId':value, 'callId':callId()}, 
					function(json){
						if (json.success){

							$('#id_feeType').val(json.feeTypeName);
							$('#id_transType').val(json.transTypeName);
							$('#id_cardBin').val(json.cardBin);
							$('#id_costCycle').val(json.costCycleName);
							$('#id_feeRate').val(json.feeRate);
							$('#id_maxAmt').val(json.maxAmt);
							$('#id_minAmt').val(json.minAmt);
							$(':submit').removeAttr('disabled');
							
							if(json.feeType == '0'){ // 交易笔数
								$('tr[id^="idAmtTr"]').hide();
								$('#idFeeRateTr').show();
								$('#idSectTbl').hide();
								//$('#id_feeRate').removeAttr('disabled');
							}
							else if(json.feeType == '1'){ // 金额固定比例
								$('tr[id^="idAmtTr"]').show();
								$('#idFeeRateTr').show();
								$('#idSectTbl').hide();
								/*$('#id_feeRate').removeAttr('disabled');
								$('#id_maxAmt').removeAttr('disabled');
								$('#id_minAmt').removeAttr('disabled');*/
							}
							else { // 金额分段比例、金额段阶梯收费
								$('tr[id^="idAmtTr"]').hide();
								$('#idFeeRateTr').hide();
								$('#idSectTbl').show();
								
								$("#idSectTbl").load(CONTEXT_PATH + "/fee/cardMerchFee/merchFeeTemplateDetailList.do",{'merchFeeTemplate.templateId':value, 'callId':callId()});
							}
							
						} else {
							showMsg(json.error);
							$('#id_feeType').val('');
							$('#id_cardBin').val('');
							$('#id_costCycle').val('');
							$('#id_feeRate').val('');
							$('#id_maxAmt').val('');
							$('#id_minAmt').val('');
							
							$(':submit').attr('disabled', 'true');
						}
					}, 'json');
				});
			});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
			<s:form action="addTemplate.do" id="inputForm" cssClass="validate">
				<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<caption>使用手续费模板添加商户手续费参数</caption>
					<tr>
						<td width="80" height="30" align="right">发卡机构</td>
						<td>
							<s:hidden  id="idBranchCode" name="cardMerchFee.branchCode" ></s:hidden>
							<s:textfield  id="idBranchName" name="cardMerchFee.branchName" cssClass="readonly" readonly="true"></s:textfield>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">选择商户标志</td>
						<td>
							<s:select id="idMerchFlag"  name="cardMerchFee.merchFlag" list="selectMerchTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
						</td>
						<td id="idMerchGroup_td1" width="80" height="30" align="right" style="display: none;">商户组</td>
						<td id="idMerchGroup_td2" style="display: none;">
							<select id="idMerchGroup" name="cardMerchFee.merchId" class="{required:true}" disabled="disabled"></select>
						</td>
						<td id="idMerch_td1" width="80" height="30" align="right">商户</td>
						<td id="idMerch_td2">
							<s:hidden id="idMerchId" name="merchs" />
							<s:textfield id="merchName" name="merchName" cssClass="{required:true}"/>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">请选择模板</td>
						<td>
							<s:select id="id_templateId" name="merchFeeTemplate.templateId" list="merchFeeTemplateList" headerKey="" headerValue="--请选择--" listKey="templateId" listValue="templateName" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo">请选择模板</span>
						</td>
						<td width="80" height="30" align="right">交易类型</td>
						<td>
							<s:textfield id="id_transType" name="" cssClass="readonly" readonly="true"></s:textfield>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费方式</td>
						<td>
							<s:textfield id="id_feeType" name="" cssClass="readonly" readonly="true"></s:textfield>
						</td>
						<td width="80" height="30" align="right">卡BIN</td>
						<td>
							<s:textfield id="id_cardBin" name="" cssClass="readonly" readonly="true"></s:textfield>
							<span class="field_tipinfo">'*'表示通用</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费周期</td>
						<td>
							<s:textfield id="id_costCycle" name="" cssClass="readonly" readonly="true"></s:textfield>
						</td>
					</tr>
					<tr id="idAmtTr1" style="display:none">
						<td width="80" height="30" align="right">单笔最高手续费</td>
						<td colspan="3">
							<s:textfield id="id_maxAmt" name="" cssClass="readonly" readonly="true"></s:textfield>
							<span class="field_tipinfo">最大为12位整数，小于等于2位小数</span>
						</td>
					</tr>
					<tr id="idAmtTr2" style="display:none">
						<td width="80" height="30" align="right">单笔最小手续费</td>
						<td colspan="3">
							<s:textfield id="id_minAmt" name="" cssClass="readonly" readonly="true"></s:textfield>
							<span class="field_tipinfo">最大为12位整数，小于等于2位小数</span>
						</td>
					</tr>
					<tr id="idFeeRateTr" style="display: none;">
						<td width="80" height="30" align="right">费率</td>
						<td colspan="3">
							<s:textfield id="id_feeRate" name="" cssClass="readonly" readonly="true"></s:textfield><span id="idFeeRateTip">%</span>
							<span class="field_tipinfo">正确格式：最大为10位整数，小于等于4位小数</span>
						</td>
					</tr>
				</table>
				<div id="idSectTbl" style="display: none;">
				</div>
				<table class="form_grid" width="80%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30" colspan="3"> 
							<input type="submit" value="提交" id="input_btn2"  name="ok" />
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/fee/cardMerchFee/showAdd.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_CARD_MERCH_FEE_ADD_TEMPLATE"/>
			</s:form>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>