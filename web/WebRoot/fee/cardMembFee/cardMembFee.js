
function feeTypeChanged(){
	$('#minMaxAmtTr').hide();
	$('#minMaxAmtTr input').attr("disabled","disabled");
	
	if ($("#feeType").val() == '0') {
		$('#idFeeRateTip').text(' 元');
	} else if ($("#feeType").val() == '1') {
		$('#idFeeRateTip').text(' %');
		$('#minMaxAmtTr').show();
		$('#minMaxAmtTr input').removeAttr('disabled');
	} else{
		$('#idFeeRateTip').html('');
	}
}
function check(){
	if($("#feeType").val() == '1' && $("#maxAmt").val() < $("#minAmt").val()){
		showMsg("单笔封顶手续费 必须大于等于 单笔保底手续费！");
		return false;
	}
	return true;
}

function submitForm(){
	if(!check()){
		return false;
	}
	$("#inputForm").submit();
}