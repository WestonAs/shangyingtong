function showJzFlag(){
	var feeAmt = $('#hidden_feeAmt').val();
	var lastFee = $('#hidden_lastFee').val();
	var recvAmt = $('#id_recvAmt').val();
	if($.trim(recvAmt)!=''&&isNaN(Number(recvAmt))){
		showMsg('请输入正确的金额格式');
	}
	var payAmount = Number(feeAmt)+Number(lastFee);
	if(payAmount==Number(recvAmt)){
		$('#tr_zjFlag').hide();
		$('#id_jzFlag').attr('disabled',true);
	}else{
		$('#id_jzFlag').removeAttr('disabled');
		$('#tr_zjFlag').show();
	}
}
