$(function(){
	Selector.selectBank('bankName', 'bankNo', true, function(){
			loadAreaCode($('#bankNo').val())
	});
});

/** 根据银行的行号来取得地区码，作为开户行地区码 */
function loadAreaCode(bankNo){
	$.post(CONTEXT_PATH + '/pages/branch/loadAccAreaCode.do', {'bankNo':bankNo, 'callId':callId()}, 
		function(data){
			$('#accAreaName').val(data.accAreaName);
			$('#accAreaCode').val(data.accAreaCode);
		}, 'json');
}

/** 检测卡号，查找卡号的会员类型 */
function cardIdCheck(){
		var cardId = $("#cardId").val();
		
		if(cardId == null || cardId == undefined || cardId == ""){
			showMsg("卡号不能为空");
			return;
		}else{
			hideMsg();
		}
		if (!validator.isDigit(cardId)) {
			return;
		}
		$.post(CONTEXT_PATH + '/vipCard/membReg/cardIdCheck.do', {'membReg.cardId':cardId, 'callId':callId()}, 
			function(json){
				if (json.success){
					$("#membClass").val(json.membClass);
					$("#className_memb").val(json.className);
					$(':submit').removeAttr('disabled');
				} else {
					showMsg(json.error);
					$(':submit').attr('disabled', 'true');
				}
			}, 'json');
}