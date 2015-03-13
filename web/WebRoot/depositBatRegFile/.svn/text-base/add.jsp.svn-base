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
		<f:js src="/js/validate.js"/>
		
		<f:js src="/js/cert/AC_ActiveX.js"/>
		<f:js src="/js/cert/AC_RunActiveContent.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
			$(function(){
				$('#id_cardClass').change(changeByCardClass);
				$(':radio[name="timesDepositType"]').click(changeByDepositType);
				$('#id_RebateType').change(changeByRebateType);
				$(':radio[name="calcMode"]').click(showRealAmt);
				$('#id_totalAmt').change(calcAmt);
				$('#id_FeeRate').change(calcAmt);

				changeByCardClass(); // 初始设置
			});

			/** 根据卡种类修改充值方式展示 */
			function changeByCardClass(){
				var value = $('#id_cardClass').val();
				if(value == '06') {//次卡
					$('[id^="depositTypeTd"]').show();
					$('#depositTypeAmtLabel').show();
					if($("#isNeedDepositRebateAcct").val()=="1"){
						$('#depositTypeRebateAmtLabel').show();
					}else{
						$('#depositTypeRebateAmtLabel').hide();
					}
					$('#depositTypeTimesLabel').show();
					$('#idForAmt').attr('checked', 'checked');//默认 充值资金子账户充值
				} else {
					$('#id_amttd').html('充值总金额');
					if($("#isNeedDepositRebateAcct").val()=="1"){
						$('[id^="depositTypeTd"]').show();
						$('#depositTypeAmtLabel').show();
						$('#depositTypeRebateAmtLabel').show();
					}else{
						$('[id^="depositTypeTd"]').hide();
						$('#depositTypeAmtLabel').hide();
						$('#depositTypeRebateAmtLabel').hide();
					}
					$('#depositTypeTimesLabel').hide();
					$('#idForAmt').attr("checked","checked");//默认 充值资金子账户充值
				}
				changeByDepositType();
			}
			
			/** 根据充值方式 充值描述修改 和 返利字段展示 */
			function changeByDepositType(){
				var value = $('input:radio[name="timesDepositType"]:checked').val();
				if (value == '0'){
					$('#id_amttd').html('充值总次数');
					$('#id_feeAmt').removeAttr('readonly').removeClass('readonly').val('');
					hideRebate();
				} else if (value == '1' || value == '2'){
					$('#id_amttd').html('充值总金额');
					$('#id_feeAmt').attr('readonly', 'true').addClass('readonly').val('');
					showRebate();
				}
			}

			function hideRebate() {
				clearAmt();
				$('#id_RebateTypeTr').hide();
				$('#id_RebateType').attr('disabled', 'true');
				$('#idRebateCardId').attr('disabled', 'true');

				$('#idCardCustomerTr').hide();
				$('#idCardCustomerId').attr('disabled', 'true');
				$('#idCardCustomerId_sel').attr('disabled', 'true');
				$('#id_rebateRule').attr('disabled', 'true');
				
				$('[id^="id_RebateAmtTd"]').hide();
				$('#id_rebateAmt').attr('disabled', 'true');

				$('[id^="id_CalcModeTd"]').hide();
				$(':radio[name="calcMode"]').attr('disabled', 'true');
				
				$('#id_realAmt').removeClass('readonly').removeAttr('readonly');
			}
			
			function showRebate() {
				$('#id_RebateTypeTr').show();
				$('#id_RebateType').removeAttr('disabled');
				$('#idRebateCardId').removeAttr('disabled');
				
				$('#idCardCustomerTr').show();
				$('#idCardCustomerId').removeAttr('disabled');
				$('#idCardCustomerId_sel').removeAttr('disabled');
				$('#id_rebateRule').removeAttr('disabled');
				
				$('[id^="id_RebateAmtTd"]').show();
				$('#id_rebateAmt').removeAttr('disabled');

				$('[id^="id_CalcModeTd"]').show();
				$(':radio[name="calcMode"]').removeAttr('disabled');
				
				$('#id_realAmt').addClass('readonly').attr('readonly', 'true');
				
				changeByRebateType();
			}


			function changeByRebateType(){ //返利方式
				var value = $("#id_RebateType").val();
				if (value == '2'){ // 返利指定卡
					$('[id^="idRebateCardTd"]').show();
					$('#idRebateCardId').removeAttr('disabled'); //可用
				} else {
					$('[id^="idRebateCardTd"]').hide();
					$('#idRebateCardId').attr('disabled', 'true'); //禁用
				}
				showRealAmt();
				calcAmt();
			}
			
			function showRealAmt() {
				var rebateType = $('#id_RebateType').val();
				var calcMode = FormUtils.getRadioedValue('calcMode'); //取得选中的单选框的值
				$('#id_realAmt').addClass('readonly').attr('readonly', 'true');
		    	$('#id_rebateAmt').addClass('readonly').attr('readonly', 'true');
				if (isEmpty(rebateType) || rebateType == 'undefined'){
			    	return;
			    }
				if (isEmpty(calcMode) || calcMode == 'undefined'){
			    	return;
			    }
			    if(calcMode == "1"){//手工计算时
			    	$('#id_realAmt').removeClass('readonly').removeAttr('readonly');
				    if(rebateType == '0' || rebateType == '2'){ // 折现或者返利指定卡的话，实收金额可改，返利金额为0
				    	$('#id_rebateAmt').val('0.0');
				    	$('#id_rebateAmt').addClass('readonly').attr('readonly', 'true');
				    } else { //其他的话可以都修改
				    	$('#id_rebateAmt').removeClass('readonly').removeAttr('readonly');
				    }
			    } else {
			    	$('#id_realAmt').addClass('readonly').attr('readonly', 'true');
			    	$('#id_rebateAmt').addClass('readonly').attr('readonly', 'true');
			    }
			}

			/**
			 * 计算实收金额，返利金额
			 */
			function calcAmt(){
				//var cardClass = $('#id_cardClass').val(); //卡类型
				//var depositType = FormUtils.getRadioedValue('timesDepositType'); // 充值方式，按次充值，还是按金额充值
				var totalAmt = $('#id_totalAmt').val();//充值总金额
				var rebateType = $('#id_RebateType').val(); //返利方式
				var rebateId = $('#id_rebateRule').val(); //返利规则ID
				
				var feeRate = $('#id_FeeRate').val(); //手续费比例
				
				clearAmt();
				if(isEmpty(totalAmt) || totalAmt == 'undefined'){
			    	return;
			    }
			    if (!validator.isDecimal(totalAmt)) {
					return;
				}
			    if(isEmpty(rebateType) || rebateType == 'undefined'){
			    	return;
			    }
			    if(isEmpty(rebateId) || rebateId == 'undefined'){
			    	return;
			    }
			    $.post(CONTEXT_PATH + '/depositBatRegFile/calcRealAmt.do', {'totalAmt':totalAmt, 'rebateType':rebateType, 
			    	'rebateId':rebateId, 'feeRate':feeRate }, 
			    	function(data){
			    		$('#id_realAmt').val(fix(data.realAmt));
						$('#id_rebateAmt').val(fix(data.rebateAmt));
						$('#id_feeAmt').val(fix(data.feeAmt));
			    	}, 'json');
			    
			}
			
			function clearAmt(){
				$('#id_realAmt').val('');
				$('#id_rebateAmt').val('');
			}
			
			function CheckUSBKey() {
				var USBKeyFlag = 0; //USBKeyFlag＝0：科友的key；USBKeyFlag＝1：非科友的key
			
					//捷德的key不在线，检查飞天的key
					var isOnline = document.all.FTUSBKEYCTRL.IsOnline;
					if (isOnline == 0) {
						if(FTDoSign()){ //调用FT的签名函数
							return true;
						} else {
							return false;
						}
					} else {
						showMsg("请检查USB Key是否插入或USB Key是否正确！");
						return false;
				    }
				return true;
			}
			
			 /*飞天的Key的签名函数*/
			function FTDoSign()	{
				var SetCertResultRet = document.all.FTUSBKEYCTRL.SetCertResult; //选择证书
				if (SetCertResultRet == 0) {
			   		var randomNum = $('#id_RandomSessionId').val();
					var cardId = $('#cardId').val();
					var realAmt = $('#id_realAmt').val();
					
					var serialNumber = document.all.FTUSBKEYCTRL.GetCertSerial;
					$('#id_serialNo').val(serialNumber);
			      
			        var signResultRet = document.all.FTUSBKEYCTRL.SignResult(fix(realAmt) + randomNum);
					if (signResultRet == 0) {
						var signData = document.all.FTUSBKEYCTRL.GetSignature;
						$('#id_Signature').val(signData);
					} else {
						showMsg("签名失败");
						return false;
					}
				} else {
					showMsg("选择证书失败");
					return false;
				}
				return true;
			}
			
			function submitDepositFileForm(){
				try{
					var signatureReg = $('#id_signatureReg').val();
					if(signatureReg == 'true'){
						if(!CheckUSBKey()){
							return false;
						}
					}
					if($('#inputForm').validate().form()){
						$('#inputForm').submit();
						$('#input_btn2').attr('disabled', 'true');
						//window.parent.showWaiter();
						$("#loadingBarDiv").css("display","inline");
						$("#contentDiv").css("display","none");
					}
		
					return true;
				}catch(err){
					txt="本页中存在错误。\n\n 错误描述：" + err.description + "\n\n"
					showMsg(txt)
				}
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<jsp:include flush="true" page="/common/loadingBarDiv.jsp"></jsp:include>
		
		<div id="contentDiv" class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" enctype="multipart/form-data" cssClass="validate">
					<s:hidden id="id_RandomSessionId" name="depositReg.randomSessionId" />
					<s:hidden id="id_Signature" name="depositReg.signature" />
					<s:hidden id="id_signatureReg" name="signatureReg" />
					<s:hidden id="id_serialNo" name="serialNo"/>
					<s:hidden name="preDeposit" />
					<s:hidden id="fromPage" name="fromPage"/>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">卡类型</td>
							<td>
								<s:select id="id_cardClass" name="depositReg.cardClass" list="cardTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" 
								cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择卡类型</span>
							</td>
							
							<s:if test='@gnete.card.service.mgr.BranchBizConfigCache@isNeedDepositRebateAcct(loginBranchCode) && preDeposit!="1"'>
								<input type="hidden" id="isNeedDepositRebateAcct" value="1" />
							</s:if>
							<td id="depositTypeTd1" width="80" height="30" align="right" style="display: none;">
								充值方式
							</td> 
							<td id="depositTypeTd2" style="display: none;" colspan="11">&nbsp;&nbsp; <%-- 跟登记表记录的充值类型有关，但字段值有转换 --%>
								<label id="depositTypeAmtLabel"><input type="radio" id="idForAmt" name="timesDepositType" value="1" />充值资金子账户</label>&nbsp;&nbsp;&nbsp;&nbsp;
								<label id="depositTypeRebateAmtLabel"><input type="radio" id="idForRebate" name="timesDepositType" value="2" />返利资金子帐户</label>&nbsp;&nbsp;&nbsp;&nbsp;
								<label id="depositTypeTimesLabel"><input type="radio" id="idForTimes" name="timesDepositType" value="0" />次卡子账户</label>
							</td>
						</tr>
						
						<tr>
							<td id="id_amttd" width="80" height="30" align="right">充值总金额</td>
							<td>
								<s:textfield id="id_totalAmt" name="depositReg.amt" cssClass="{required:true, num:true, decimal:'14,2'}"/>
								<span class="field_tipinfo">请输入金额</span>
								<span class="error_tipinfo">金额不合法</span>
							</td>
							<td width="80" height="30" align="right">总笔数</td>
							<td>
								<s:textfield id="id_totalNum" name="totalNum" cssClass="{required:true, digit:true, min:1}" maxlength="7"/>
								<span class="field_tipinfo">请输入正整数</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">手续费比例</td>
							<td>
								<s:textfield id="id_FeeRate" name="depositReg.feeRate" maxlength="20" cssClass="{num:true, required:true, decimal:'5,3'}" maxLength="9" value="0" />
								<span class="field_tipinfo">%最多3位小数</span>
							</td>
							<td width="80" height="30" align="right">手续费</td>
							<td>
								<s:textfield id="id_feeAmt" name="depositReg.feeAmt" cssClass="{required:true, num:true} readonly" readonly="true"/>
								<span class="field_tipinfo">请输入金额</span>
								<span class="error_tipinfo">金额不合法</span>
							</td>
						</tr>
						<tr id="id_RebateTypeTr">
							<td width="80" height="30" align="right">返利方式</td>
							<td>
								<s:select id="id_RebateType" name="depositReg.rebateType" list="rebateTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" 
									cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择返利方式</span>
							</td>
							<td width="80" height="30" align="right" id="idRebateCardTd1" style="display: none;">返利卡卡号</td>
							<td id="idRebateCardTd2" style="display: none;">
								<s:textfield id="idRebateCardId" name="depositReg.rebateCard" cssClass="{required:true}" disabled="true" maxlength="19"/>
								<span class="field_tipinfo">请输入19位卡号</span>
							</td>
						</tr>
						<tr id="idCardCustomerTr">
							<td width="80" height="30" align="right">购卡客户</td>
							<td>
								<s:hidden id="idCardCustomerId" name="depositReg.cardCustomerId" />
								<s:textfield id="idCardCustomerId_sel" name="depositReg.cardCustomerName" cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo">请选择购卡客户</span>
							</td>
							<td width="80" height="30" align="right">返利规则</td>
							<td>
								<s:hidden id="id_rebateRule" name="depositReg.rebateId" />
								<s:textfield id="id_rebateRuleName" name="depositReg.rebateName" cssClass="{required:true} readonly" readonly="true"/>
<%--								<select id="id_rebateRule" name="depositReg.rebateId" class="{required:true}"></select>--%>
								<span class="field_tipinfo">请选择返利规则</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">实收金额</td>
							<td>
								<s:textfield id="id_realAmt" name="depositReg.realAmt" readonly="true" cssClass="readonly {required:true, num:true, decimal:'14,2'}"/>
								<span class="field_tipinfo">请输入金额</span>
								<span class="error_tipinfo">金额不合法</span>
							</td>
							<td width="80" height="30" align="right" id="id_RebateAmtTd1">返利金额</td>
							<td id="id_RebateAmtTd2">
								<s:textfield id="id_rebateAmt" name="depositReg.rebateAmt" readonly="true" cssClass="readonly {required:true, num:true, decimal:'14,2'}"/>
								<span class="field_tipinfo">请输入金额</span>
								<span class="error_tipinfo">金额不合法</span>
							</td>
						</tr>
						<tr>
							<td width="80" align="right" id="id_CalcModeTd1">计算方式</td>
							<td id="id_CalcModeTd2" colspan="11">&nbsp;&nbsp;
								<input type="radio" id="idCalcModeAuto" name="calcMode" value="0" checked/><label for="idCalcModeAuto"> 自动计算 </label>&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" id="idCalcModeManual" name="calcMode" value="1"/><label for="idCalcModeManual"> 手工指定 </label>
							</td>
						</tr>
						<tr >
							<td width="80" height="30" align="right">文件格式</td>
							<td height="30">&nbsp;&nbsp;
								<label><input type="radio" name="oldFileFmt" value="false" checked="checked"/> 新格式 </label>&nbsp;&nbsp;&nbsp;&nbsp;
								<label><input type="radio" name="oldFileFmt" value="true" /> 旧格式（积分卡一代） </label>
							</td>
							<td width="80" height="30" align="right">充值文件</td>
							<td  height="30" >
								<s:file name="upload" cssClass="{required:true}" contenteditable="false"></s:file>
								<span class="field_tipinfo">请选择文件</span>
							</td>
						</tr>
						<tr style="margin: 30px" >
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="提交" id="input_btn2" name="ok" onclick="return submitDepositFileForm();" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<s:if test="actionSubName == null">
									<input type="button" value="返回" name="escape" onclick="gotoUrl('/depositBatRegFile/list.do?goBack=goBack')" class="ml30" />
								</s:if>
								<s:else>
									<input type="button" value="返回" name="escape" onclick="gotoUrl('/depositBatReg/listPreFile.do?goBack=goBack')" class="ml30" />
								</s:else>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_DEPOSIT_BAT_FILE_ADD"/>
				</s:form>
				
				<span class="note_div">注释</span>
				<ul class="showli_div">
					<li class="showli_div">文件新格式（商盈通系统）：第一行为标题行；明细行（卡号,金额）如：“2089999787878787,200”</li>
					<li class="showli_div">文件旧格式（积分卡一代）：第一行为标题行；明细行（序号;卡号;金额）如：“123;2089999787878787;200”</li>
					<li class="showli_div">文件方式只支持手工指定返利，返利总金额按照充值卡的充值金额比例大小平摊到各卡。</li>
				</ul>
				
				<script type="text/javascript">
					AC_AX_RunContent( 'classid','clsid:B494F2C1-57A7-49F7-A7AF-0570CA22AF80','id','FTUSBKEYCTRL','style','DISPLAY:none' ); //end AC code
				</script>
				<noscript><OBJECT classid="clsid:B494F2C1-57A7-49F7-A7AF-0570CA22AF80" id="FTUSBKEYCTRL" style="DISPLAY:none"/></OBJECT></noscript>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>