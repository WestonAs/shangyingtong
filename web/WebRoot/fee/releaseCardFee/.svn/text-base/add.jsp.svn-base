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
		<f:js src="/js/date/WdatePicker.js"/>
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
			$(function(){
				$('#idMerchFlag').val('0');
				$('#idSetMode').val('');
				var setMode = $('#idSetMode').val();
				var parent =  $('#idParent').val();
				
				$('#id_branchType').change(function(){
					var branchType = $(this).val();

					// 集团
					if(branchType == '1'){
						$('#id_branchCode_1').show();
						$('#id_branchCode_2').show();
						$('#id_branchCode_3').hide();
						$('#id_branchCode_4').hide();
						$('#branchName_1').removeAttr('disabled');
						$('#idBranchCode_1').removeAttr('disabled');
						$('#branchName_2').attr('disabled', 'true');
						$('#idBranchCode_2').attr('disabled', 'true');
						Selector.selectBranch('branchName_1', 'idBranchCode_1', true, '12', loadMerchGroup, setMode, parent);
					}
					// 发卡机构
					else if(branchType == '0'){
						$('#id_branchCode_3').show();
						$('#id_branchCode_4').show();
						$('#id_branchCode_1').hide();
						$('#id_branchCode_2').hide();
						$('#branchName_2').removeAttr('disabled');
						$('#idBranchCode_2').removeAttr('disabled');
						$('#branchName_1').attr('disabled', 'true');
						$('#idBranchCode_1').attr('disabled', 'true');
						Selector.selectBranch('branchName_2', 'idBranchCode_2', true, '20', loadMerchGroup, setMode, parent);
					}
					else if(branchType == ''){
						$('#id_branchCode_3').hide();
						$('#id_branchCode_4').hide();
						$('#id_branchCode_1').hide();
						$('#id_branchCode_2').hide();
						$('#branchName_1').attr('disabled', 'true');
						$('#idBranchCode_1').attr('disabled', 'true');
						$('#branchName_2').attr('disabled', 'true');
						$('#idBranchCode_2').attr('disabled', 'true');
					}
				});
				
				$('#idTransType').change(function(){
					var type = $(this).val();
					
					if (!isEmpty(type)){
						$("#idFeeType").load(CONTEXT_PATH + "/fee/releaseCardFee/feeTypeList.do",{'releaseCardFee.transType':type}, function(){
							$('#idFeeRateTr').hide();
							$('#idAmtTr_1').hide();
							$('#idAmtTr_2').hide();
							$('#idFeeRate').attr('disabled', 'true');
							$('#idMaxAmt').attr('disabled', 'true');
							$('#idMinAmt').attr('disabled', 'true');
							$('#idSectTbl').hide();
						});
					}
					
					// 当选择了售卡充值、次卡充值、赠券充值、通用积分充值、专属积分充值时，商户标志和商户隐藏并禁用
					if (type == '01' || type == '02' || type == '03' || type == '04' || type == '05'){
						$('#idMerchTr').hide();
						$('#idMerchFlag').attr('disabled', 'true');
						$('#idMerchGroup').attr('disabled', 'true');
						$('#merchName').attr('disabled', 'true');
					} else {
						$('#idMerchTr').show();
						$('#idMerchFlag').removeAttr('disabled');
						$('#idMerchGroup').removeAttr('disabled');
						$('#merchName').removeAttr('disabled');
					}
				});
				
				$('#idMerchFlag').change(function(){
					var value = $(this).val();
					if (value == '0') {
						$('td[id^="idMerchGroup_td"]').hide();
						$('td[id^="idMerch_td"]').show();
						$('#idMerchGroup').attr('disabled', 'true');
						$('#merchName').removeAttr('disabled');
						$('#idMerchId').removeAttr('disabled');
					} else {
						$('td[id^="idMerchGroup_td"]').show();
						$('td[id^="idMerch_td"]').hide();
						$('#idMerchGroup').removeAttr('disabled');
						$('#merchName').attr('disabled', 'true');
						$('#idMerchId').attr('disabled', 'true');
					}
				});
				
				$('#merchName').focus(function(){
					var branchType = $('#id_branchType').val();
					
					if (isEmpty(branchType)){
						showMsg('请先选择机构类型');
					}
					var branchCode = "";
					if(branchType == '1'){
						branchCode = $('#idBranchCode_1').val();
					}else if(branchType == '0'){
						branchCode = $('#idBranchCode_2').val();
					} 
					
					if (isEmpty(branchCode)){
						showMsg('请先选择集团或发卡机构再选择商户');
					}
				});
				
				$('#idCardBin_sel').focus(function(){
					var branchType = !isEmpty($('#id_branchType').val());
					if (isEmpty(branchType)){
						showMsg('请先选择机构类型');
					}
					
					var branchCode = "";
					if(branchType == '1'){
						branchCode = $('#idBranchCode_1').val();
					}else if(branchType == '0'){
						branchCode = $('#idBranchCode_2').val();
					}
					
					if (isEmpty(branchCode)){
						showMsg('请先选择集团或发卡机构再选择卡BIN');
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

				/*
					1: 按自然日; 2: 按自然月; 3: 按自然季度; 4: 按自然年; 5: 按自然半年; 6: 协议月
				*/
				$('#id_feeCycleType').change(function(){ // 计费周期类型
					var value = $(this).val();
					
					if (value == '6') { 
						$('#id_feeDate').val('');
						$('#id_adjustMonths').val('');
						$('#id_feeDate_tr').show();
						$('#id_feeDate').removeAttr('disabled');
						$('#id_adjustMonths').removeAttr('disabled');
					} else {
						$('#id_feeDate_tr').hide();
						$('#id_feeDate').attr('disabled', 'true');
						$('#id_adjustMonths').attr('disabled', 'true');
					}
					
				});
			});
			function loadMerchGroup() {
				var branchType = $('#id_branchType').val();
				if (isEmpty(branchType)){
					showMsg('请先选择机构类型');
				}
				var branchCode = "";
				if(branchType == '1'){
					branchCode = $('#idBranchCode_1').val();
				}else if(branchType == '0'){
					branchCode = $('#idBranchCode_2').val();
				}
						
				/*$('#idMerchGroup').load(CONTEXT_PATH + "/fee/releaseCardFee/getMerchGroup.do", 
					{'branchCode':branchCode, 'callId':callId()});*/
					
				if (!isEmpty(branchCode)){
					$('#merchName').unbind().removeMulitselector();
					$('#idCardBin_sel').unbind().removeMulitselector();
					
					$('#merchName').val('');
					$('#idMerchId').val('');
					$('#idCardBin_sel').val('');
					$('#idCardBin').val('');
					Selector.selectMerch('merchName', 'idMerchId', false, branchCode);
					Selector.selectCardBin('idCardBin_sel', 'idCardBin', true, branchCode);
				} else {
					$('#merchName').focus(function(){
						var branchType = $('#id_branchType').val();
						if (isEmpty(branchType)){
							showMsg('请先选择机构类型');
						}
						
						var branchCode = "";
						if(branchType == '1'){
							branchCode = $('#idBranchCode_1').val();
						}else if(branchType == '0'){
							branchCode = $('#idBranchCode_2').val();
						}
						
						if (isEmpty(branchCode)){
							showMsg('请先选择集团或发卡机构再选择商户');
						}
					});
				
					$('#idCardBin_sel').focus(function(){
						var branchType = $('#id_branchType').val();
						if (isEmpty(branchType)){
							showMsg('请先选择机构类型');
						}
						
						var branchCode = "";
						if(branchType == '1'){
							branchCode = $('#idBranchCode_1').val();
						}else if(branchType == '0'){
							branchCode = $('#idBranchCode_2').val();
						}
						
						if (isEmpty(branchCode)){
							showMsg('请先选择集团或发卡机构再选择卡BIN');
						}
					});
					
				}
			}
			function addItem() {
				var count = $('tr[id^="idDetail_"]').size();
					var content = '<tr id="idDetail_'+ (count+1) +'"><td align="center">'+ (count+1) +'</td><td align="center"><input type="text" name="ulimit" onblur="formatCurrency(this)" value="" id="inputForm_ulimit" /></td><td align="center" ><input type="text" name="feeRate" onblur="formatCurrency(this)" value="" id="inputForm_feeRate" /></td></tr>';
				
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
					if((objUValue=="")||(objRValue < 0 )){
						showMsg("分段金额上限不能小于 0 ，请重新输入！");
						return false;
					}
					if(!isDecimal(objUValue, '14,2')){
						showMsg("分段金额最大12位整数，2位小数，必须为Num(14,2)");
						return false;
					}
					if((objRValue=="")||(objRValue < 0 )){
						showMsg("费率不能小于 0 ，请重新输入！");
						return false;
					}
					if(!isDecimal(objRValue, '5,2')){
						showMsg("费率最大3位整数，2位小数，必须为Num(5,2)");
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

			// 根据金额获得中文大写
			function getChinese(){
				var feeRate = $('#idFeeRate').val(); 
				var chinese = AmountInWords(feeRate);
				$("#id_feeRateToChinese").html(chinese);
			}

			function getExpirDate(){
				WdatePicker({minDate:'#F{$dp.$D(\'id_effDate\')}'});
			} 

			function getEffDate(){
				WdatePicker({minDate:'%y-%M-%d', maxDate:'#F{$dp.$D(\'id_expirDate\')}'});
			} 

			function queryFeePrincipleList(){
				var feeType = $('#idFeeType').val(); 
				$("#id_feePrinciple").load(CONTEXT_PATH + "/fee/releaseCardFee/queryFeePrincipleList.do",{'releaseCardFee.feeType':feeType, 'callId':callId()});
			}

			/*
				如果是计费原则“按计费周期实际费率”，不输入调整周期；
				其他需要输入调整周期
			*/
			function bindCycle(){
				var feePrinciple = $('#id_feePrinciple').val(); 
				
				/*
				0: 按计费周期实际费率
				1: 按计费周期最小费率计费
				3: 按调整周期实际费率
				*/
				if(feePrinciple=='0'){
					$('#td_adjustCycleType_1').hide();
					$('#td_adjustCycleType_2').hide();
					$('#id_adjustCycleType').attr('disabled',true);
				}
				else {
					$('#td_adjustCycleType_1').show();
					$('#td_adjustCycleType_2').show();
					$('#id_adjustCycleType').removeAttr('disabled');
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
				<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<caption>${ACT.name}</caption>
					<s:hidden id="idSetMode" name="setMode" />
					<s:hidden id="idParent" name="parent" />
					<tr>
						<td width="80" height="30" align="right">机构类型</td>
						<td>
							<s:select id="id_branchType" name="branchType" list="branchTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo">请选择集团或发卡机构</span>
						</td>
					</tr>
					<tr>
						<td id="id_branchCode_1" width="80" height="30" align="right" style="display: none">集团</td>
						<td id="id_branchCode_2" style="display: none">
							<s:hidden id="idBranchCode_1" name="releaseCardFee.branchCode"
								cssClass="{required:true}" disabled="true"></s:hidden>
							<s:textfield  id="branchName_1" cssClass="{required:true}" disabled="true" ></s:textfield>
						</td>
					</tr>
					<tr>
						<td id="id_branchCode_3" width="80" height="30" align="right" style="display: none">发卡机构</td>
						<td id="id_branchCode_4" style="display: none">
							<s:hidden id="idBranchCode_2" name="releaseCardFee.branchCode"
								cssClass="{required:true}" disabled="true"></s:hidden>
							<s:textfield  id="branchName_2" cssClass="{required:true}" disabled="true"></s:textfield>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">交易类型</td>
						<td>
							<s:select id="idTransType"  name="releaseCardFee.transType" list="transTypeList" headerKey="" headerValue="--请选择--" 
								listKey="value" listValue="name" cssClass="{required:true}"></s:select>
						</td>
					</tr>
					<tr id="idMerchTr">
						<td width="80" height="30" align="right" style="display: none;">选择商户标志</td>
						<td style="display: none;">
							<s:select id="idMerchFlag"  name="releaseCardFee.merchFlag" list="selectMerchTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							<span class="field_tipinfo">请选择商户标志</span>
						</td>
						<td id="idMerchGroup_td1" width="80" height="30" align="right" style="display: none;">商户组</td>
						<td id="idMerchGroup_td2" style="display: none;">
							<select id="idMerchGroup" name="releaseCardFee.merchId" disabled="disabled"></select>
							<span class="field_tipinfo">请选择商户组</span>
						</td>
						<td id="idMerch_td1" width="80" height="30" align="right">商户</td>
						<td id="idMerch_td2">
							<s:hidden id="idMerchId" name="merchs" />
							<s:textfield id="merchName" name="merchName"/>
							<span class="field_tipinfo">不选为通用</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">卡BIN</td>
						<td>
							<s:hidden id="idCardBin" name="releaseCardFee.cardBin" />
							<s:textfield id="idCardBin_sel" name="cardBin" ></s:textfield>
							<span class="field_tipinfo">不选为通用</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费方式</td>
						<td>
							<s:select id="idFeeType" name="releaseCardFee.feeType" onchange="queryFeePrincipleList()" list="feeTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
						</td>
						
						<td width="80" height="30" align="right">计费原则</td>
						<td>
							<select name="releaseCardFee.feePrinciple" id="id_feePrinciple" onchange="bindCycle();"
								class="{required:true}">
							</select>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费周期</td>
						<td>
							<s:select id="id_feeCycleType" name="releaseCardFee.feeCycleType" headerKey="" headerValue="--请选择--" 
								list="feeCycleTypeList" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
						</td>
						<td id="td_adjustCycleType_1" width="80" height="30" align="right" style="display: none">调整周期</td>
						<td id="td_adjustCycleType_2" style="display: none">
							<s:select id="id_adjustCycleType" name="releaseCardFee.adjustCycleType" headerKey="" headerValue="--请选择--" 
								list="adjustCycleTypeList" listKey="value" listValue="name" cssClass="{required:true}" disabled="true"></s:select>
						</td>
					</tr>
					<tr id="id_feeDate_tr" style="display: none;">
						<td width="80" height="30" align="right">触发日期</td>
						<td>
							<s:textfield id="id_feeDate" name="releaseCardFee.feeDate" onclick="WdatePicker({minDate:'%y-%M-%d'})" 
								cssClass="{required:true}" disabled="disabled"></s:textfield>
							<span class="field_tipinfo"></span>
						</td>
						<td width="80" height="30" align="right">调整月数</td>
						<td>
							<s:textfield id="id_adjustMonths" name="releaseCardFee.adjustMonths" cssClass="{required:true,digit:true,max:12,min:1}" 
								disabled="disabled"></s:textfield>
							<span class="field_tipinfo">1~12月</span>
						</td>
					</tr>
					<tr id="idFeeRateTr" style="display: none;">
						<td width="80" height="30" align="right">费率</td>
						<td colspan="3">
							<s:textfield id="idFeeRate" name="feeRateComma" cssClass="{required:true, decimal:'14,4'}" disabled="disabled"></s:textfield><span id="idFeeRateTip">%</span>
							<span class="field_tipinfo">正确格式：最大10位整数，4位小数</span>
						</td>
					</tr>
					<tr id="idAmtTr_1" style="display:none">
						<td width="80" height="30" align="right">单笔封顶手续费</td>
						<td colspan="3"><!-- releaseCardFee.maxAmt  -->
							<s:textfield id="idMaxAmt" name="maxAmtComma" onblur="formatCurrency(this);" 
								cssClass="{required:true, decimal:'14,4'}" disabled="disabled"></s:textfield><span>元</span>
							<span class="field_tipinfo">最大为10位整数，小于等于4位小数</span>
						</td>
					</tr>
					<tr id="idAmtTr_2" style="display:none">
						<td width="80" height="30" align="right">单笔保底手续费</td>
						<td colspan="3"><!-- releaseCardFee.minAmt  -->
							<s:textfield id="idMinAmt" name="minAmtComma" onblur="formatCurrency(this);" 
								cssClass="{required:true, decimal:'14,4'}" disabled="disabled"></s:textfield><span>元</span>
							<span class="field_tipinfo">最大为10位整数，小于等于4位小数</span>
						</td>
					</tr>
					<tr id="id_date_tr">
						<td width="80" height="30" align="right">生效日期</td>
						<td>
							<s:textfield id="id_effDate" name="releaseCardFee.effDate" onclick="getEffDate();" cssClass="{required:true}" ></s:textfield>
							<span class="field_tipinfo"></span>
						</td>
						<td width="80" height="30" align="right">失效日期</td>
						<td>
							<s:textfield id="id_expirDate" name="releaseCardFee.expirDate" onclick="getExpirDate();" cssClass="{required:true}" ></s:textfield>
							<span class="field_tipinfo"></span>
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
							<s:textfield name="feeRate" onblur="formatCurrency(this)" value=""/>
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
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/fee/releaseCardFee/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_RELEASECARDFEE_ADD"/>
			</s:form>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>