/* ****************************************************************
 *		单张售卡 相关js 
 * ****************************************************************/

$(function() {
	$(':button').attr('disabled', 'true');

	$(':input[id^="id_ExtraInfo"]').attr('disabled', 'true');

	$('#readM1CardIdBtn').removeAttr('disabled');
	$('#readIcCardIdBtn').removeAttr('disabled');
	$('#input_btn_rtn').removeAttr('disabled');
	$('#input_btn_clear').removeAttr('disabled');
	$('#amt').attr('readonly', 'true').addClass('readonly');
	$('#cardId').change(function() {
		var cardId = $(this).val();

		$('#realAmt').val('');
		$('#rebateAmt').val('');

		if (cardId == null || cardId == undefined || cardId.length < 18) {
			$('#idCardCustomerId_sel').bind('focus', customerIdBlur);
			return;
		}

		var cardType = $('#cardType').val();

		// var cardBin = cardId.substr(3, 6);
		$('#idCardCustomerId_sel').removeMulitselector();
		$('#idCardCustomerId_sel').unbind();

		// 加载其他信息
		try {
			$.post(CONTEXT_PATH + '/saleCardReg/calcCardOther.do', {
				'cardId' : cardId,
				'isBatFlag' : 'N',
				'cardType' : cardType,
				'callId' : callId()
			}, function(json) {
				if (json.success) {
					if (cardId.length == 19) {
						$('#amt').removeAttr('readonly').removeClass('readonly');
					} else {
						$('#amt').attr('readonly', 'true').addClass('readonly');
					}
					if (json.isModifyFaceValue) {
						$('#amt').removeAttr('readonly').removeClass('readonly');
					} else {
						$('#amt').attr('readonly', 'true').addClass('readonly');
					}
					
					// 是否需要实名登记
					if (json.isNeedCredNo) {
						$('#inputForm_isHasCustName').attr('checked','checked').attr('disabled', 'true');
						showExtraInfo();
					} else {
						$('#inputForm_isHasCustName').removeAttr('checked').removeAttr('disabled');
						hideExtraInfo();
					}

					$('#amt').val(fix(json.amt));
					$('#expenses').val(fix(json.expenses));
					$('#cardBin').val(json.cardBin);
					$('#cardBinName').val(json.cardBinName);
					$('#cardBranch').val(json.cardBranch);
					$('#cardBranchName').val(json.cardBranchName);
					$('#idCardCustomerId').val(json.cardCustomerId);
					$('#idCardCustomerId_sel').val(json.cardCustomerName);
					loadRebateRule();
					Selector.selectCardCustomer('idCardCustomerId_sel',
							'idCardCustomerId', true, $('#cardType').val(),
							json.cardBin, loadRebateRule);
					
					ajaxLoadCouponClass();
					
				} else {
					showMsg(json.errorMsg);
				}
			}, 'json');
		} catch (e) {
		}
	});
	
	$('#idCardCustomerId_sel').click(customerIdBlur);

	// 点击录入持卡人录入时调用
	$('#inputForm_isHasCustName').click(function() {
		var hasChecked = $(this).attr('checked');
		if (hasChecked) {
			showExtraInfo();
		} else {
			hideExtraInfo();
		}
	});
	
	// 点击赠送赠券调用
	$('#isDepositCoupon').click(changeIsDepositCoupon);
	
	changeIsDepositCoupon();//默认初始化
});

function changeIsDepositCoupon() {
	var hasChecked = $("#isDepositCoupon").attr('checked');
	if (hasChecked) {
		$("#depositCouponTr").show();
		$("#depositCouponTr input").removeAttr("disabled");
		ajaxLoadCouponClass();
	} else {
		$("#depositCouponTr").hide();
		$("#depositCouponTr input").attr("disabled","disabled");
	}
}

function ajaxLoadCouponClass(){
	$("#couponClassSel").val("").empty(); //清空赠券类型
	if (isEmpty($("#cardId").val())) {
		return;
	}
	$.getJSON(CONTEXT_PATH + "/ajax/ajaxFindCouponClass.do",{'formMap.cardId':$("#cardId").val(), 'callId':callId()}, 
		function(json){
			$("#couponClassSel").append("<option value=''>--请选择--</option>");
			var couponClassList = json.couponClassList;
			if(couponClassList!=null && couponClassList.length>0){
				
				for(i in couponClassList){
					var option = "<option value='"+couponClassList[i].coupnClass +"'> "
						+couponClassList[i].className+"</option>";
					$("#couponClassSel").append(option);
				}
			}
		}
	);
}

function customerIdBlur() {
	var cardId = $("#cardId").val();
	if (cardId == null || cardId == undefined || cardId.length < 18) {
		showMsg('请先输入正确的卡号再选择购卡客户');
		return;
	}
}

function loadRebateRule() {
	var cardId = $("#cardId").val();
	var cardCustomerId = $('#idCardCustomerId').val();
	$('#realAmt').val('');
	$('#rebateAmt').val('');
	$.post(CONTEXT_PATH + '/saleCardReg/findCustomerRebateType.do', {
		'cardCustomer.cardCustomerId' : cardCustomerId,
		'callId' : callId()
	}, function(json) {
		if (json.choose) {
			$('#idRebateTypeTr').show();
			$('#rebateType').removeAttr('disabled').val(json.rebateType);
			;
		} else {
			$('#idRebateTypeTr').hide();
			$('#rebateType').attr('disabled', 'true');
		}
	}, 'json');

	if (cardId == undefined || cardId == "") {
		return;
	}
	if (cardCustomerId == undefined || cardCustomerId == "") {
		return;
	}
	var ablePeriod = $('#id_presell').val()=="0";//单张实时售卡允许分期返利规则
	
	$jload('idRebateRulePage', '/saleCardReg/findRebateRule.do', {
		'cardId' : cardId,
		'cardCustomer.cardCustomerId' : cardCustomerId,
		'formMap.ablePeriod': ablePeriod,
		'callId' : callId()
	});
	$('#idConfirmTbl').show();
}

// 服务端计算返利金额、实收金额
function calRealAmt(b) {
	// 按次充值不计算金额
	$('#idConfirmTbl').show();
	var cardCustomerId = $('#idCardCustomerId').val();
	var saleRebateId = FormUtils.getRadioedValue('rebateIdRadio');
	
	if($('#rebateRuleRebateType'+saleRebateId).val()==1){//分期
		$('#periodLabel').show();
	}else{
		$('#periodLabel').hide();
	}
	var rebateType = $('#rebateType').val();
	var amt = $('#amt').val(); // 面值
	var expenses = $('#expenses').val(); // 工本费
	var feeRate = $('#id_FeeRate').val(); // 手续费比例

	if (amt == undefined || amt == "") {
		if (b)
			showMsg('未填写面值');
		return;
	}
	if (isEmpty(expenses)) {
		showMsg('工本费不能为空');
		return;
	}
	if (isEmpty(feeRate)) {
		showMsg('手续费比例不能为空');
		return;
	}
	if (cardCustomerId == undefined || cardCustomerId == "") {
		if (b)
			showMsg('未选择购卡客户');
		return;
	}
	if (saleRebateId == undefined || saleRebateId == "") {
		if (b)
			showMsg('未选择返利规则');
		return;
	}
	$('#idRebateId').val(saleRebateId);

	var type = 1; // type默认指按金额充值，为0时按次充值
	var cardId = $('#cardId').val();
	if ($("#depositTypeIsTimes").val() == 'true') {
		var type = '0';
		$('#realAmt').removeClass('readonly').removeAttr('readonly');
	}
	
	$('#input_btn2').attr('disabled', 'disabled');//禁用提交按钮
	$('#realAmt').val("");
	$('#rebateAmt').val("");
	$('#id_FeeAmt').val(""); // 清算金额*手续费费率
	$.post(CONTEXT_PATH + '/saleCardReg/calRealAmt.do', {
		'saleCardReg.cardCustomerId' : cardCustomerId,
		'saleCardReg.rebateId' : saleRebateId,
		'saleCardReg.amt' : amt,
		'saleCardReg.expenses' : expenses,
		'rebateType' : rebateType,
		'saleCardReg.feeRate' : feeRate,
		'type' : type,
		'cardId' : cardId,
		'callId' : callId()
	}, function(data) {
		if (data.success) {
			$('#realAmt').val(fix(data.realAmt));
			$('#rebateAmt').val(fix(data.rebateAmt));
			$('#id_FeeAmt').val(fix(data.feeAmt)); // 清算金额*手续费费率

			// 是否需要实名登记
			if (data.isNeedCredNo) {
				$('#inputForm_isHasCustName').attr('checked', 'checked').attr(
						'disabled', 'true');
				showExtraInfo();
			} else {
				$('#inputForm_isHasCustName').removeAttr('checked').removeAttr(
						'disabled');
				hideExtraInfo();
			}
			$(':button').removeAttr('disabled');
		} else {
			showMsg(data.errorMsg);
		}
	}, 'json');
}

// 需要做实名登记
function showExtraInfo() {
	// $('#inputForm_isHasCustName').attr('checked', 'checked').atrr('disabled',
	// 'true');
	$('#idCardInfoTbl').show();
	$(':input[id^="id_ExtraInfo"]').removeAttr('disabled');
	$('#custName').removeAttr('disabled');
}

// 不需要做实名登记
function hideExtraInfo() {
	// $('#inputForm_isHasCustName').removeAttr('checked').removeAttr('disabled');
	$('#idCardInfoTbl').hide();
	$(':input[id^="id_ExtraInfo"]').attr('disabled', 'true');
	$('#custName').attr('disabled', 'true')
	$('#extraInfo_Remark').val('');
}


function submitForm() {
	try {
		// if(!checkField()){
		// return false;
		// }
		var saleRebateId = FormUtils.getRadioedValue('rebateIdRadio');

		if (isEmpty(saleRebateId)) {
			showMsg('请选择返利规则！');
			return false;
		}
		if (isEmpty($('#realAmt').val())) {
			showMsg('“实收金额”不能为空，请重新计算费用');
			return false;
		}
		if (isEmpty($('#rebateAmt').val())) {
			showMsg('“返利金额”不能为空，请重新计算费用');
			return false;
		}
		if (isEmpty($('#amt').val())) {
			showMsg('“面值(次数)”不能为空，请重新计算费用');
			return false;
		}
		
		if ($("#isDepositCoupon").attr('checked') 
				&& (isEmpty($("#couponClassSel").val()) || isEmpty($('#couponAmt').val()))
				){ // 选择了 赠送赠券
			showMsg('选择了赠送赠券，赠券类型与赠券金额都不能为空');
			return false;
		}
		
		if ($('#inputForm').validate().form()) {
			var signatureReg = $('#id_signatureReg').val();
			if (signatureReg == 'true') {
				if (!CheckUSBKey()) {
					return false;
				}
			}

			$('#inputForm').submit();
			$('#input_btn2').attr('disabled', 'true');
			// window.parent.showWaiter();
			$("#loadingBarDiv").css("display", "inline");
			$("#contentDiv").css("display", "none");
		}
	} catch (err) {
		txt = "本页中存在错误。\n\n"
		txt += "错误描述：" + err.description + "\n\n"
		showMsg(txt)
	}
}

function CheckUSBKey() {
	var USBKeyFlag = 0; // USBKeyFlag＝0：科友的key；USBKeyFlag＝1：非科友的key

	try {
		// 捷德的key不在线，检查飞天的key
		var isOnline = document.all.FTUSBKEYCTRL.IsOnline;
		if (isOnline == 0) {
			if (FTDoSign()) { // 调用FT的签名函数
				return true;
			} else {
				return false;
			}
		} else {
			showMsg("请检查USB Key是否插入或USB Key是否正确！");
			return false;
		}
	} catch (ex) {
		alert(ex);
	}
	return true;
}

/* 飞天的Key的签名函数 */
function FTDoSign() {
	var SetCertResultRet = document.all.FTUSBKEYCTRL.SetCertResult; // 选择证书
	if (SetCertResultRet == 0) {
		var randomNum = $('#id_RandomSessionId').val();
		var cardId = $('#cardId').val();
		var realAmt = $('#realAmt').val();

		var serialNumber = document.all.FTUSBKEYCTRL.GetCertSerial;
		$('#id_serialNo').val(serialNumber);

		var signResultRet = document.all.FTUSBKEYCTRL.SignResult(cardId
				+ realAmt + randomNum);
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

/** 
 * 售卡时，加载身份证信息（持卡人信息） 
 */
function loadIdCardInfo(){
		try{
			var CVR_IDCard = document.getElementById("CVR_IDCard");	
			var strReadResult = CVR_IDCard.ReadCard();
		}catch (e) {
			alert("未能识别出读卡器！");
			return;
		}
		if(strReadResult == "0"){
			clearIdCardInfoFields();
			$("#custName").val(CVR_IDCard.Name);
			$("#id_ExtraInfo_CredType").val("01"); // 证件类型为身份证
			$("#id_ExtraInfo_CredNo").val(CVR_IDCard.CardNo);
			$("#id_ExtraInfo_Address").val(CVR_IDCard.Address);
		} else {
			clearIdCardInfoFields();
	    	alert(strReadResult);
	    }
}
/** 清空身份证信息输入框（持卡人信息）*/
function clearIdCardInfoFields(){
	$("#custName").val("");
	$("#id_ExtraInfo_CredType").val("");
	$("#id_ExtraInfo_CredNo").val("");
	$("#id_ExtraInfo_Address").val("");
}

/** 读取ic卡卡号 */
function readIcCardId(){
	var oldCardId = $("#cardId").val();
	var touchType = $('input:radio[name="touchType"]:checked').val();
	var cardId = IcReader.readIcCardId(touchType);
	$("#cardId").val(cardId);
	if(cardId != oldCardId){
		$("#cardId").change();
	}
}


/**
 *@param type: credNo证件号，mobileNo手机号 
 */
function ajaxFindCardExtraInfos(type){
	if($('#cardId').val()==""  || $('#cardBranch').val()==""){
		return;
	}

	var params;
	if(type=="credNo"){//证件号检查
		if($("#id_ExtraInfo_CredNo").val()=="") return;
		params = {'formMap.cardBranch':$('#cardBranch').val(),
				'formMap.credNo':$('#id_ExtraInfo_CredNo').val(),
				'callId':callId()};
	} else if(type=="mobileNo"){//手机号检查
		if($('#id_ExtraInfo_MobileNo').val()=="") return;
		params = {'formMap.cardBranch': $('#cardBranch').val(),
				'formMap.mobileNo': $('#id_ExtraInfo_MobileNo').val(),
				'callId': callId()};
	} else {
		return;
	}
	$.post(CONTEXT_PATH + '/ajax/ajaxFindCardExtraInfos.do', 
			params, 
			function(json){
				if (json.returnCode == 1){ //成功
					if(json.cnt>0){
						if(type=="credNo"){
							showCardHolderMsg("提示信息：证件号["+$('#id_ExtraInfo_CredNo').val()+"]已经关联了卡号");
						}else{
							showCardHolderMsg("提示信息：手机号["+$('#id_ExtraInfo_MobileNo').val()+"]已经关联了卡号");
						}
					}
				} else {
					if(type=="credNo"){
						showCardHolderMsg("检查证件号["+$("#id_ExtraInfo_CredNo").val()+"]是否存在录入的持卡人信息 失败");
					}else{
						showCardHolderMsg("检查手机号["+$('#id_ExtraInfo_MobileNo').val()+"]是否存在录入的持卡人信息 失败");
					}
				}
			}, 
			'json');
}

function showCardHolderMsg(content){
	$('#cardHolderMsgDiv').fadeIn();
	$('#cardHolderMsgContent').html(content);
	$('#cardHolderMsgClose').focus();
}
function hideCardHolderMsg(){
	$('#cardHolderMsgDiv').fadeOut(100);
	$('#cardHolderMsgContent').html('');
}