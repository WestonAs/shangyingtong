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
				Selector.selectCardBin('idCardBin_sel', 'idCardBin', true, branchCode);
				$('#idMerchGroup').load(CONTEXT_PATH + "/fee/cardMerchFee/getMerchGroup.do", 
					{'branchCode':branchCode, 'callId':callId()});
				
				$('#idTransType').change(function(){
					var type = $(this).val();
					if (type != ''){
						$("#idFeeType").load(CONTEXT_PATH + "/fee/cardMerchFee/feeTypeList.do",{'cardMerchFee.transType':type}, function(){
							$('#idFeeRateTr').hide();
							$('#idFeeRate').attr('disabled', 'true');
							//$('#idCostCycle').removeAttr('disabled');
							$('#idSectTbl').hide();
						});
					}
				});
				
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

				/*
				1: 按交易笔数; 2: 按交易笔数分段; 3: 按交易笔数分段阶梯收费
				5: 金额固定比例单笔保底封顶; 6: 金额分段比例; 7: 金额段阶梯收费
				*/			
				$('#idFeeType').change(function(){
					var value = $(this).val();
					if (value == '1' || value == '5') { 
						$('#idFeeRate').val('');
						$('#idFeeRateTr').show();
						$('#idFeeRate').removeAttr('disabled');
						$('#idSectTbl').hide();
					} else if (value == '2' || value == '3' || value == '6' || value == '7') {
						$('#idFeeRateTr').hide();
						$('#idFeeRate').attr('disabled', 'true');
						$('#idSectTbl').show();
					} else {
						$('#idFeeRateTr').hide();
						$('#idFeeRate').attr('disabled', 'true');
						$('#idSectTbl').hide();
					}
	
					if (value == '1') { // 按交易笔数
						$('#idFeeRateTip').html('元');
					} else if (value == '5') { // 金额固定比例单笔保底封顶
						$('#idFeeRateTip').html('%');
					} 
	
					if(value == '5'){ //金额固定比例单笔保底封顶
						$('#idAmtTr_1').show();
						$('#idAmtTr_2').show();
						$('#idMaxAmt').removeAttr('disabled');
						$('#idMinAmt').removeAttr('disabled');
					} else { //其他
						$('#idAmtTr_1').hide();
						$('#idAmtTr_2').hide();
						$('#idMaxAmt').attr('disabled', 'true');
						$('#idMinAmt').attr('disabled', 'true');
					}
					
					if(value == '2' || value == '3'){ // 按交易笔数分段、按交易笔数分段阶梯收费
						$('#id_AmtOrFeeRate').html('收费金额(元)');
					}
					else if(value == '6' || value == '7'){ // 金额分段比例、金额段阶梯收费
						$('#id_AmtOrFeeRate').html('费率(%)');
					}
					
				});
			
			});
			function addItem() {
				var count = $('tr[id^="idDetail_"]').size();
					var content = '<tr id="idDetail_'+ (count+1) +'"><td align="center">'+ (count+1) +'</td><td align="center"><input type="text" name="ulimit" onblur="formatCurrency(this)" value="" id="inputForm_ulimit" /></td><td align="center" ><input type="text" name="feeRate" value="" id="inputForm_feeRate" /></td></tr>';
				
				$('#idXh').html(count+2);
				$('#idDetailMax').before(content);
			
				// 设置样式
				SysStyle.setFormGridStyle();
			}
			function deleteItem() {
				var count = $('tr[id^="idDetail_"]').size();
				if (count == 0) {
					$('#idXh').html('1');
					showMsg('必须设置一项');
					return;
				}
				$('#idXh').html(count);
				$('tr[id^="idDetail_"]:last').remove();
			}
			
			function check(){
				// 判断最小金额和最大金额
				if (isDisplay('idMaxAmt')){
					var maxAmt = $('#idMaxAmt').val();
					maxAmt = maxAmt.replace(/,/g, '');
					var minAmt = $('#idMinAmt').val();
					minAmt = minAmt.replace(/,/g, '');
					
					if (parseFloat(maxAmt) < parseFloat(minAmt)){
						showMsg('最小手续费不能大于最大手续费');
						return;
					}
				}
				
				if(!isDisplay('idSectTbl')){
					return true;
				}
				var count = $('tr[id^="idDetail"]').size();
				for(i=0; i<count; i++){
					var $objU = $('tr[id^="idDetail"]').eq(i).children().eq(1).children()
					var objUValue = $objU.val().toString().replace(/\$|\,/g, '');
					
					var $objR = $('tr[id^="idDetail"]').eq(i).children().eq(2).children()
					var objRValue = $objR.val().toString().replace(/\$|\,/g, '');
					
					if((isNaN(objUValue))||(isNaN(objRValue))){
						showMsg("“分段金额上限”、“费率”请输入数字");
						return false;
					}
					if((objUValue=="")||(objUValue < 0 )){
						showMsg("分段金额上限不能小于 0 ，请重新输入！");
						return false;
					}
					if(!isDecimal(objUValue, '14,2')){
						showMsg("分段金额最大为12位整数，2位小数，必须为Num(14,2)");
						return false;
					}
					if((objRValue=="")||(objRValue < 0 )){
						showMsg("费率不能小于 0 ，请重新输入！");
						return false;
					}
					if(!isDecimal(objRValue, '14,4')){
						showMsg("费率最大为10位整数，4位小数，必须为Num(14,4)");
						return false;
					}
				}
				for(i=1; i<count; i++){
					var $preObj = $('tr[id^="idDetail"]').eq(i-1).children().eq(1).children()
					var $nextObj = $('tr[id^="idDetail"]').eq(i).children().eq(1).children()
					var preValue = $preObj.val();
					preValue = preValue.replace(/,/g, '');
					
					var nextValue = $nextObj.val();
					nextValue = nextValue.replace(/,/g, '');
					
					if(Number(preValue) > Number(nextValue)){
						showMsg("后段“分段金额上限”必须大于前段“分段金额上限” ，请重新输入！");
						return false;
					}				
				}
				return true;
			}
			
			function submitForm(){
				if(!check()){
					return false;
				}
				//$('#idCostCycleHidden').val($('#idCostCycle').val());
				$("#inputForm").submit();
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
					<caption>${ACT.name}<span class="caption_title">| <f:link href="/fee/cardMerchFee/showAddTemplate.do">使用商户手续费模板？</f:link></span></caption>
					<tr>
						<td width="80" height="30" align="right">发卡机构</td>
						<td>
							<s:hidden  id="idBranchCode" name="cardMerchFee.branchCode" ></s:hidden>
							<s:textfield  id="idBranchName" name="cardMerchFee.branchName" cssClass="readonly" readonly="true"></s:textfield>
						</td>
						
						<td width="80" height="30" align="right">交易类型</td>
						<td>
							<s:select id="idTransType"  name="cardMerchFee.transType" list="transTypeList" headerKey="" headerValue="--请选择--" 
								listKey="value" listValue="name" cssClass="{required:true}"></s:select>
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
						<td id="idMerch_td1">
							<s:hidden id="idMerchId" name="merchs" />
							<s:textfield id="merchName" name="merchName" cssClass="{required:true}"/>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费方式</td>
						<td>
							<select id="idFeeType" name="cardMerchFee.feeType" class="{required:true}"></select>
						</td>
						<td width="80" height="30" align="right">卡BIN</td>
						<td>
							<s:hidden id="idCardBin" name="cardMerchFee.cardBin" />
							<s:textfield id="idCardBin_sel" name="cardBin"></s:textfield>
							<span class="field_tipinfo">不选为通用</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费周期</td>
						<td>
							<s:select id="idCostCycle" name="cardMerchFee.costCycle" list="costCycleList" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
						</td>
					</tr>
					<tr id="idAmtTr_1" style="display:none">
						<td width="80" height="30" align="right">单笔最高手续费</td>
						<td colspan="3">
							<s:textfield id="idMaxAmt" name="maxAmtComma" onblur="formatCurrency(this);" 
								cssClass="{required:true, decimal:'14,2'}" disabled="disabled"></s:textfield>
							<span class="field_tipinfo">最大为12位整数，小于等于2位小数</span>
						</td>
					</tr>
					<tr id="idAmtTr_2" style="display:none">
						<td width="80" height="30" align="right">单笔最小手续费</td>
						<td colspan="3">
							<s:textfield id="idMinAmt" name="minAmtComma" onblur="formatCurrency(this);" 
								cssClass="{required:true, decimal:'14,2'}" disabled="disabled"></s:textfield>
							<span class="field_tipinfo">最大为12位整数，小于等于2位小数</span>
						</td>
					</tr>
					<tr id="idFeeRateTr" style="display: none;">
						<td width="80" height="30" align="right">费率</td>
						<td colspan="3">
							<s:textfield id="idFeeRate" name="feeRateComma" cssClass="{required:true, decimal:'14,4'}" disabled="disabeld"></s:textfield><span id="idFeeRateTip">%</span>
							<span class="field_tipinfo">正确格式：最大为10位整数，小于等于4位小数</span>
						</td>
					</tr>
				</table>
				<table class="form_grid" width="60%" border="0" cellspacing="3" cellpadding="0" id="idSectTbl" style="display:none;">
					<tr>
					   <td align="center" nowrap class="titlebg">序号</td>
					   <td align="center" nowrap class="titlebg">分段金额上限</td>
					   <td align="center" nowrap class="titlebg" id="id_AmtOrFeeRate"></td>
					</tr>
					<tr id="idDetailMax">
						<td align="center" id="idXh">1</td>
						<td align="center">
							<s:textfield name="ulimit" value="999999999999.99" readonly="true" cssClass="readonly"/>
						</td>
						<td align="center" >
							<s:textfield name="feeRate" value=""/>
						</td>
					</tr>
					<tr id="idLink" style="line-height: 30px;">
						<td align="left" colspan="4" style="padding-left: 20px;">
							<span class="redlink"><a href="javascript:void(0);" onclick="javascript:addItem();">增加一项</a><a class="ml30" href="javascript:void(0);" onclick="javascript:deleteItem();">删除一项</a></span>
						</td>
					</tr>
				</table>
				<table class="form_grid" width="80%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30" colspan="3"> 
							<input type="button" value="提交" id="input_btn2"  name="ok" onclick="return submitForm()"/>
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/fee/cardMerchFee/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_CARDMERCHFEE_ADD"/>
			</s:form>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>