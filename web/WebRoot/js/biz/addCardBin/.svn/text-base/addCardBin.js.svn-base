$(function(){
	var loginBranchCode = $('#id_loginBranchCode').val();
	Selector.selectBranch('id_CardBranch_sel', 'id_CardBranch', true, '20', '', '', loginBranchCode);

	$('#idCardBinNo').blur(function(){
		$('#idCardBinNo_field').html('请输入6位数字的卡BIN号码');
		checkCardBin();
	});
});

function clearCarBinError(){
	$('#idCardBinNo_field').removeClass('error_tipinfo').html('请输入6位数字的卡BIN号码');
	$('#input_btn2').removeAttr('disabled');
}
function checkCardBin() {
	var flag = false;
	var value = $('#idCardBinNo').val();
	if (value == undefined || value.length < 6){
		return;
	}
	if (!validator.isDigit(value)) {
		return;
	}
	$.ajax({
		url: CONTEXT_PATH + '/addCardBin/isExistBinNo.do',
		data: {'cardBinReg.binNo':value},
		cache: false,
		async: false,
		type: 'POST',
		dataType: 'json',
		success: function(result){
			if (result.isExist){
				flag = false;
				$('#idCardBinNo_field').html('该卡BIN已存在，请更换').addClass('error_tipinfo').show();
				//$('#idCardBinNo').focus();
				$('#input_btn2').attr('disabled', 'true');
			} else {
				flag = true;
				$('#idCardBinNo_field').removeClass('error_tipinfo').html('卡BIN输入正确');
				$('#input_btn2').removeAttr('disabled');
			}
		}
	});
	/*$.post(CONTEXT_PATH + '/addCardBin/isExistBinNo.do', {'cardBinReg.binNo':value}, function(json){
		if (json.isExist){
			flag = false;
			$('#idCardBinNo_field').html('该卡BIN已存在，请更换').addClass('error_tipinfo').show();
			//$('#idCardBinNo').focus();
			$('#input_btn2').attr('disabled', 'true');
		} else {
			flag = true;
			$('#idCardBinNo_field').removeClass('error_tipinfo').html('卡BIN输入正确');
			$('#input_btn2').removeAttr('disabled');
		}
	}, 'json');*/
	return flag;
}
function check(){
	if(checkCardBin()){
		if($('#inputForm').validate().form()){
			$('#inputForm').submit();
			$('#input_btn2').attr('disabled', 'true');
			$("#loadingBarDiv").css("display","inline");
			$("#contentDiv").css("display","none");
		}
	}
}