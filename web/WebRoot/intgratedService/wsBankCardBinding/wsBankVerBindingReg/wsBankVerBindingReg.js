/* ****************************************************************
 *		银行卡绑定/解绑/默认卡 登记 模块相关js 
 * ****************************************************************/
$(function(){
	$('#cardId').change(cardIdCheck);
	
	$("#setStyle").click(selectSetStyle);
	
	Selector.selectBank('bankName', 'bankNo', true, function(){
			loadAreaCode($('#bankNo').val())
	});
	
	selectSetStyle();//默认初始化
});

function cardIdCheck(){
	var value = $(this).val();
	if(value == null || value == undefined || value == ""){
		return;
	}
	$.post(CONTEXT_PATH + '/intgratedService/wsBankCardBinding/wsBankVerBindingReg/cardIdCheck.do', 
			{'wsBankVerBindingReg.cardId':value, 'callId':callId()}, 
				function(json){
				if (json.returnCode == 1){ //成功
						$(':submit').removeAttr('disabled');
					} else {
						showMsg(json.msg);
						$(':submit').attr('disabled', 'disabled');
					}
				}, 'json');
}

function selectSetStyle() {
	var setStyle = $("#setStyle").val();
	if (setStyle == '1' || setStyle == '2') {// 绑定、设置默认并绑定
		$("#bankCardBindingTbody").show();
		$("#bankCardBindingTbody").find("input,select").removeAttr("disabled");
		$("#bankCardUnbindingTbody").hide();
		$("#bankCardUnbindingTbody").find("input,select").attr("disabled", "disabled");
	} else if (setStyle == '3' ||　setStyle == '0') {// 设置默认、解绑 
		$("#bankCardBindingTbody").hide();
		$("#bankCardBindingTbody").find("input,select").attr("disabled", "disabled");
		$("#bankCardUnbindingTbody").show();
		$("#bankCardUnbindingTbody").find("input,select").removeAttr("disabled");
	}
}

/** 根据银行的行号来取得地区码，作为开户行地区码 */
function loadAreaCode(bankNo){
	$.post(CONTEXT_PATH + '/pages/branch/loadAccAreaCode.do', {'bankNo':bankNo, 'callId':callId()}, 
		function(data){
			$('#accAreaName').val(data.accAreaName);
			$('#accAreaCode').val(data.accAreaCode);
		}, 'json');
}

/** 表单域校验 */
function validateForm() {
	var setStyle = $("#setStyle").val();
	if((setStyle == '1' || setStyle == '2') && $("#idcard").val().length!=16 && $("#idcard").val().length!=18){
		showMsg("身份证号必须是16位或18位");
		return false;
	}
	var signatureReg = $('#needSignatureReg').val();
	if (signatureReg == 'true' && !CheckUSBKey()) { // 证书签名失败
		return false;
	}
}

function CheckUSBKey() {
	// 检查飞天的key
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
	return true;
}

/* 飞天的Key的签名函数 */
function FTDoSign() {
	var SetCertResultRet = document.all.FTUSBKEYCTRL.SetCertResult; // 选择证书
	if (SetCertResultRet == 0) {
		$('#serialNo').val(document.all.FTUSBKEYCTRL.GetCertSerial);
		var signResultRet = document.all.FTUSBKEYCTRL.SignResult($('#cardId').val() + $('#random').val());
		if (signResultRet == 0) {
			$('#mySign').val(document.all.FTUSBKEYCTRL.GetSignature);
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